package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.AccountExistsException;
import com.example.exception.BadArgumentsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use
 * the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations.
 * You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
@RestController
public class SocialMediaController {

    // I want to keep this file as small as possible so Autowire it is for injection
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    /**
     * endpoint to register account
     * 
     * @param account
     * @return ResponseEntity corresponding to returning object or other message
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody Account account) {

        // try-catch for error handling
        try {
            // everything is fine return object in body
            Account returningAccount = accountService.registerAccount(account);
            return ResponseEntity.ok().body(returningAccount);
        } catch (AccountExistsException e) {
            // account already exists
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (BadArgumentsException e) {
            // illegal arguments
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * endpoint to verify login
     * 
     * @param account
     * @return ResponseEntity containing object or unathorized http status
     */
    @PostMapping("/login")
    public ResponseEntity<Account> verifyLogin(@RequestBody Account account) {
        Account returningAccount = accountService.verifyAccount(account);

        if (returningAccount != null) {
            return ResponseEntity.status(HttpStatus.OK).body(returningAccount);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

    }

    /**
     * endpoint to post new messages
     * 
     * @param message
     * @return
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message) {
        Message returningAccount = messageService.postMessage(message);

        // if nul we return a 400 status code
        if (returningAccount == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // return in the body the account found
        return ResponseEntity.ok().body(returningAccount);
    }

    /**
     * endpoint to handle the retrieval of all existing messages
     * 
     * @return
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> retrieveAllMessages() {
        List<Message> returningMessages = messageService.getAllMessages();

        return ResponseEntity.ok().body(returningMessages);
    }

    /**
     * endpoint to retrive a message by its id
     * 
     * @param messageId
     * @return
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> findMessage(@PathVariable Integer messageId) {

        Message returningMessage = messageService.getMessageById(messageId);

        return ResponseEntity.ok().body(returningMessage);

    }

    /**
     * endpoint to delete a message by the id provided in the path
     * 
     * @param messageId
     * @return
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId) {
        Integer returningRowsNumberUpdated = messageService.deleteMessageById(messageId);

        // returning no body or number of rows
        if (returningRowsNumberUpdated != null) {
            return ResponseEntity.ok().body(returningRowsNumberUpdated);
        } else {
            return ResponseEntity.ok().build();
        }
    }

    /**
     * endpoint to handle the updating of a message by its id
     * 
     * @param messageId
     * @param message
     * @return
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable Integer messageId, @RequestBody Message message) {
        // number of rows updated
        Integer returningNumberOfRows = messageService.updateMessageById(messageId, message);

        // checking to see what responseentity to return
        if (returningNumberOfRows == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.ok().body(returningNumberOfRows);
        }
    }

    /**
     * endpoint to handle the getting of all messages based on a user's id
     * 
     * @param accountId
     * @return
     */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesById(@PathVariable Integer accountId){

        List<Message> allMessages = messageService.getMessagesByUserId(accountId);

        return ResponseEntity.ok().body(allMessages);

    }
}
