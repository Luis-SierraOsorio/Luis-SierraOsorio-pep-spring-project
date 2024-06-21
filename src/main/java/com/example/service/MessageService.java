package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    
    @Autowired
    MessageRepository messageRepository;

    // using this to find a user
    @Autowired
    AccountRepository accountRepository;

    /**
     * function to post a new message depending on requirements
     * 
     * messageText is NOT blank
     * length is NOT over 255
     * postBy is a REAL user
     * 
     * @param message
     * @return Message object or null depending if message is posted or not
     */
    public Message postMessage(Message message){

        // this code checks the conditions required
        if (message.getMessageText().length() > 255 ||
         message.getMessageText().isBlank() ||
         accountRepository.findByAccountId(message.getPostedBy()) == null){
            return null;
        }

        // save the message
        return messageRepository.save(message);
    }



}
