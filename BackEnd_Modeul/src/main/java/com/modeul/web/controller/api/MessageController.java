package com.modeul.web.controller.api;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.modeul.web.entity.MessageView;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MessageController {

        private final SimpMessageSendingOperations messagingTemplate;
        
        // sub를 메시지를 받을 endpoint로 설정합니다.
        @MessageMapping("/chat/enterUser")
        public void enterUser(@RequestBody MessageView messageView, SimpMessageHeaderAccessor headerAccessor) throws JsonProcessingException{
            

            // String publishMessage = (String) messagingTemplate.getStringSerializer().deserialize(messageView.getContent());
            System.out.println(messageView);
            headerAccessor.getSessionAttributes().put("stuffId", messageView.getStuffId());

            messageView.setContent(messageView.getSender() + "님이 입장하셨습니다.");
            
            
            //String text = mapper.writeValueAsString(messageView);

            messagingTemplate.convertAndSend("/sub/chat/room/" + messageView.getStuffId(), messageView);
        }

        // pub으로 메시지를 반환합니다.
        @MessageMapping("/chat/message")
        public void message(@RequestBody MessageView messageView){
            
            System.out.println(messageView);

            messageView.setContent(messageView.getContent());
    
            messagingTemplate.convertAndSend("/sub/chat/room/" + messageView.getStuffId(), messageView);
        }
    
}
