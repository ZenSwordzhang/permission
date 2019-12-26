package com.zsx.web.interceptor;

import com.zsx.core.CommonConsts;
import com.zsx.core.entity.admin.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.text.MessageFormat;
import java.util.Map;

/**
 * @description 基于Spring框架的拦截器，继承HandlerInterceptorAdapter，
 * 该接口是通过适配器模式进行设计的
 * @author Nicky
 * @date 2019年12月15日
 */
@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
								   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
		//shiro框架的会话管理，获取Session，校验用户是否通过登录验证
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		User loginUser = (User)session.getAttribute(CommonConsts.SESSION_USER);
		if(loginUser != null) {
			logger.debug(MessageFormat.format("用户{0}请求建立WebSocket连接", loginUser.getUsername()));
			/**加入权限校验，待开发...**/
			return true;
		}
		logger.error("未登录系统，禁止连接WebSocket");
		return false;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

	}

}
