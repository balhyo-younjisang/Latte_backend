package com.jsell.latte.domain.Chat.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsell.latte.domain.Chat.Dto.ChatRoomDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final ObjectMapper objectMapper;
    private Map<Long, ChatRoomDto> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoomDto> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoomDto findById(Long roomId) {
        return chatRooms.get(roomId);
    }

    public ChatRoomDto createRoom() {
        Long roomId = UUID.randomUUID().getMostSignificantBits();
        ChatRoomDto chatRoom = ChatRoomDto.builder().chatRoomId(roomId).build();
        chatRooms.put(roomId, chatRoom);

        return chatRoom;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
