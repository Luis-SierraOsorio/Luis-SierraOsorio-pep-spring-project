package com.example.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{

    // custom method to find by id
    Message findByMessageId(Integer message_id);

    // // custom method to delete by id
    // Message deleteByMessageId(Integer message_id);

    // custom method to get a list of message objects
    List<Message> findByPostedBy(Integer posted_by);
}