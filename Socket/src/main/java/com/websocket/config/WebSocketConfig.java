package com.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import com.websocket.interceptors.HttpHandShakeInterc;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{

	@Autowired
	private HttpHandShakeInterc handshakeInterceptor;
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		
		config.setApplicationDestinationPrefixes("/app");/*sets URL "/app" which
		take the message to the msgBroker; */
		config.enableSimpleBroker("/topic");
		/* these are the recipients of the messages sent from a client or delivery address
		 * "/queue" will have the message broker distribute the msg to a particular user*/

		//		config.setUserDestinationPrefix("/user");
	}
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {

		registry.addEndpoint("/ws").withSockJS().setInterceptors(handshakeInterceptor);
		/* the end points are the URLs where the web sockets will be registered...
		 * the message broker exposes an end point so that the client can contact 
		 * and form a connection... to establish contact with the server*/	
	}
}
