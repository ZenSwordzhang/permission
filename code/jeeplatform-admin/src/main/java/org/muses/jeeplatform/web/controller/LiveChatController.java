package org.muses.jeeplatform.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.muses.jeeplatform.core.CommonConsts;
import org.muses.jeeplatform.core.entity.admin.User;
import org.muses.jeeplatform.entity.Chat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/live/chat")
public class LiveChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String REDIS_UNREAD_MSG_PREFIX = "redisUnreadMsgPrefix__";

    @Autowired
    private SimpUserRegistry userRegistry;

    @Autowired
    private RedisTemplate redisTemplate;

    // 通过构造器注入，解决循环依赖问题
//    @Autowired
//    public WsController(SimpMessagingTemplate simpMessagingTemplate) {
//        this.simpMessagingTemplate = simpMessagingTemplate;
//    }

    /**
     * 给指定用户发送WebSocket消息
     *
     * @param chat
     */
    @MessageMapping("/user")
    public void handleChat(@RequestBody Chat chat) {
        simpMessagingTemplate.convertAndSendToUser(chat.getReceiver(), "/topic/reply", chat);
    }

    @GetMapping("/list")
    public ModelAndView toList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/chat/chat_list");
        return modelAndView;
    }

    /**
     * 拉取指定监听路径的未读的WebSocket消息
     *
     * @param destination 指定监听路径
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    @PostMapping("/message")
    @ResponseBody
    public Map<String, Object> pullUnreadMessage(String destination) {
        Map<String, Object> result = new HashMap<>();
        try {
            //shiro框架的会话管理，获取Session，校验用户是否通过登录验证
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            User loginUser = (User) session.getAttribute(CommonConsts.SESSION_USER);

            //存储消息的Redis列表名
            String listKey = REDIS_UNREAD_MSG_PREFIX + loginUser.getUsername() + ":" + destination;
            //从Redis中拉取所有未读消息
            List<Object> messageList = redisTemplate.boundListOps(listKey).range(0, -1);

            result.put("code", "200");
            if (messageList != null && messageList.size() > 0) {
                //删除Redis中的这个未读消息列表
                redisTemplate.delete(listKey);
                //将数据添加到返回集，供前台页面展示
                result.put("result", messageList);
            }
        } catch (Exception e) {
            result.put("code", "500");
            result.put("msg", e.getMessage());
        }

        return result;
    }

    /**
     * 给指定用户发送消息，并处理接收者不在线的情况
     *
     * @param sender      消息发送者
     * @param receiver    消息接收者
     * @param destination 目的地
     * @param payload     消息正文
     */
    private void sendToUser(String sender, String receiver, String destination, String payload) {
        SimpUser simpUser = userRegistry.getUser(receiver);

        //如果接收者存在，则发送消息
        if (simpUser != null && StringUtils.isNoneBlank(simpUser.getName())) {
            simpMessagingTemplate.convertAndSendToUser(receiver, destination, payload);
        }
        //否则将消息存储到redis，等用户上线后主动拉取未读消息
        else {
            //存储消息的Redis列表名
            String listKey = REDIS_UNREAD_MSG_PREFIX + receiver + ":" + destination;
            logger.info(MessageFormat.format("消息接收者{0}还未建立WebSocket连接，{1}发送的消息【{2}】将被存储到Redis的【{3}】列表中", receiver, sender, payload, listKey));

            //存储消息到Redis中
            redisTemplate.boundListOps(listKey).rightPush(payload);
        }

    }
}
