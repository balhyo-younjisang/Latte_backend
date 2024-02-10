package com.jsell.latte.domain.Chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsell.latte.domain.Chat.Dto.ChatRoomDto;
import com.jsell.latte.domain.Chat.Service.ChatService;
import com.jsell.latte.domain.Chat.Dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    // 소켓 통신 시 메세지의 전송을 다루는 부분
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println(payload);

        ChatMessageDto chatMessageDto = objectMapper.readValue(payload, ChatMessageDto.class);
        ChatRoomDto chatRoomDto = chatService.findById(chatMessageDto.getChatRoomId());

        chatRoomDto.handleActions(session, chatMessageDto, chatService);
    }
}
