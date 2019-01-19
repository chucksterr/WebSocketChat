package com.websocket.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.websocket.models.ChatMessage;

@Component
public class WebSocketEventLister {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventLister.class);
	
	@Autowired
	SimpMessageSendingOperations messagingTemplate;
	
	@EventListener
	public void handleWebSocketConnectListener (SessionConnectedEvent connectEvent) {
		
		logger.info("New web socket connection successful");
	}
	
	@EventListener
	public void handleWbSocketDisconnectListener(SessionDisconnectEvent discEvent) {
		
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(discEvent.getMessage());
		
		String username = (String) headerAccessor.getSessionAttributes().get("username");
		
		if(username !=null) {
			
			logger.info("User disconnect: " + username);
			
			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setType(ChatMessage.MessageType.LEAVE);
			chatMessage.setSender(username);
			
			messagingTemplate.convertAndSend("/topic/publicChatRoom", chatMessage);
		}
	}
}
