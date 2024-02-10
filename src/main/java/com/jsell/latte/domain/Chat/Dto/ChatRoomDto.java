package com.jsell.latte.domain.Chat.Dto;

import com.jsell.latte.domain.Chat.Service.ChatService;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoomDto {
    private Long chatRoomId;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoomDto(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public void handleActions(WebSocketSession session, ChatMessageDto chatMessageDto, ChatService chatService) {
        if(chatMessageDto.getMessageType().equals(ChatMessageDto.MessageType.ENTER)) {
            sessions.add(session);
            chatMessageDto.setMessage(chatMessageDto.getSenderId() + "님이 입장했습니다");
        }
        sendMessage(chatMessageDto, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }
}
