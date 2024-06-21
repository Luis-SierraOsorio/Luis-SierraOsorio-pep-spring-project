package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.List;


@Service
public class MessageService {

    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){

        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;

    }

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

        // code checks if the message existed, if so, returns 1 since only 1 row is
        // updated, else returns null
        if (messageRepository.findByMessageId(messageId) != null) {
            messageRepository.deleteById(messageId);
            return 1;
        } else {
            return null;
        }
    }

    /**
     * method to update a message given its id with given requirements
     * 
     * messageId ALREADY exists
     * messageText is NOT blank
     * messageText length is NOT over 255
     * 
     * @param messageId
     * @param messageText
     * @return
     */
    public Integer updateMessageById(Integer messageId, Message newMessage) {

        // getting message if it exists
        Message message = messageRepository.findByMessageId(messageId);

        // checking conditions, returning null if not satisfied
        if (message == null || newMessage.getMessageText().isBlank() || newMessage.getMessageText().length() > 255) {
            return null;
        }

        // changing the text field and then updating the message
        message.setMessageText(newMessage.getMessageText());
        messageRepository.save(message);
        return 1;

    }

    /**
     * method to get all the messages based on a user id
     * 
     * @param accountId
     * @return
     */
    public List<Message> getMessagesByUserId(Integer accountId){
        List<Message> allMessages = messageRepository.findByPostedBy(accountId);
        return allMessages;
    }

}
