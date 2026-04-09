package com.travelnestpro.repository;

import com.travelnestpro.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(Long s1, Long r1, Long s2, Long r2);
}
