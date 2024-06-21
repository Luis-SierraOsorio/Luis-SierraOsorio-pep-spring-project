package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.List;

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
    public Message postMessage(Message message) {

        // this code checks the conditions required
        if (message.getMessageText().length() > 255 ||
                message.getMessageText().isBlank() ||
                accountRepository.findByAccountId(message.getPostedBy()) == null) {
            return null;
        }

        // save the message
        return messageRepository.save(message);
    }

    /**
     * function to get all the existing messages
     * 
     * @return
     */
    public List<Message> getAllMessages() {
        List<Message> allMessages = messageRepository.findAll();
        return allMessages;
    }

    /**
     * function to get a message by a given id
     * 
     * @param messageId
     * @return
     */
    public Message getMessageById(Integer messageId) {
        return messageRepository.findByMessageId(messageId);
    }

    /**
     * function to delete a message by the given id
     * 
     * @param messageId
     * @return
     */
    public Integer deleteMessageById(Integer messageId) {

        // code checks if the message existed, if so, returns 1 since only 1 row is updated, else returns null
        if (messageRepository.findByMessageId(messageId) != null) {
            messageRepository.deleteById(messageId);
            return 1;
        } else {
            return null;
        }
    }

}
