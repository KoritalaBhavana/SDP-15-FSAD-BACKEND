package com.travelnestpro.controller;

import com.travelnestpro.dto.MessageRequest;
import com.travelnestpro.entity.Message;
import com.travelnestpro.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@Tag(name = "Messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Operation(summary = "Send message")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Sent")})
    @PostMapping
    public ResponseEntity<Message> send(@Valid @RequestBody MessageRequest request) {
        return ResponseEntity.ok(messageService.send(request));
    }

    @Operation(summary = "Get conversation")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/conversation")
    public ResponseEntity<List<Message>> getConversation(@RequestParam Long user1, @RequestParam Long user2) {
        return ResponseEntity.ok(messageService.getConversation(user1, user2));
    }

    @Operation(summary = "Get inbox")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/inbox/{userId}")
    public ResponseEntity<List<Message>> getInbox(@PathVariable Long userId) {
        return ResponseEntity.ok(messageService.getInbox(userId));
    }
}
