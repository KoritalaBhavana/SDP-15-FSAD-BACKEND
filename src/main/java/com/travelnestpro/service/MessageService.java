package com.travelnestpro.service;

import com.travelnestpro.dto.MessageRequest;
import com.travelnestpro.entity.Message;
import com.travelnestpro.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message send(MessageRequest request) {
        Message message = new Message();
        message.setSenderId(request.getSenderId());
        message.setReceiverId(request.getReceiverId());
        message.setMessage(request.getMessage());
        message.setIsRead(false);
        return messageRepository.save(message);
    }

    public List<Message> getConversation(Long user1, Long user2) {
        return messageRepository
                .findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(user1, user2, user1, user2)
                .stream()
                .sorted(Comparator.comparing(Message::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
    }

    public List<Message> getInbox(Long userId) {
        return messageRepository.findAll().stream()
                .filter(m -> userId.equals(m.getReceiverId()))
                .sorted(Comparator.comparing(Message::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
    }
}
