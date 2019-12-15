package org.muses.jeeplatform.handler;

import org.apache.http.auth.BasicUserPrincipal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.muses.jeeplatform.core.CommonConsts;
import org.muses.jeeplatform.core.entity.admin.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.text.MessageFormat;
import java.util.Map;

/**
 * 自定义{@link org.springframework.web.socket.server.support.DefaultHandshakeHandler}，实现"生成自定义的{@link java.security.Principal}"
 */
@Component
public class MyHandshakeHandler extends DefaultHandshakeHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User loginUser = (User)session.getAttribute(CommonConsts.SESSION_USER);
        if(loginUser != null){
            logger.debug(MessageFormat.format("WebSocket连接开始创建Principal，用户：{0}", loginUser.getUsername()));
            return new BasicUserPrincipal(loginUser.getUsername());
        }else{
            logger.error("未登录系统，禁止连接WebSocket");
            return null;
        }
    }

}
