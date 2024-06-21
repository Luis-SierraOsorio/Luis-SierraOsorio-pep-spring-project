package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.AccountExistsException;
import com.example.exception.BadArgumentsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Account> verifyLogin(@RequestBody Account account){
        Account returningAccount = accountService.verifyAccount(account);

        if (returningAccount != null){
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
    public ResponseEntity<Message> postMessage(@RequestBody Message message){
        Message returningAccount = messageService.postMessage(message);

        // if nul we return a 400 status code
        if (returningAccount == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // return in the body the account found
        return ResponseEntity.ok().body(returningAccount);
    }
}
