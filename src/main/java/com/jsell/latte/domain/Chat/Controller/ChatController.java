package com.jsell.latte.domain.Chat.Controller;

import com.jsell.latte.domain.Chat.Dto.ChatRoomDto;
import com.jsell.latte.domain.Chat.Service.ChatService;
import com.jsell.latte.domain.Chat.WebSocketHandler;
import com.jsell.latte.global.Common.Dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public Response<ChatRoomDto> createRoom() {
        ChatRoomDto createdChatRoom =  chatService.createRoom();

        return Response.of(201, "Create Chat room", createdChatRoom);
    }

    @GetMapping("/rooms")
    public Response<List<ChatRoomDto>> findAllRoom() {
        List<ChatRoomDto> rooms = chatService.findAllRoom();

        return Response.of(200, "Find All chat rooms", rooms);
    }
}
