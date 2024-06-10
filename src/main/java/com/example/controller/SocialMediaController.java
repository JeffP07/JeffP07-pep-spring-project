package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    MessageService messageService;
    @Autowired
    AccountService accountService;

    @PostMapping(value = "/register")
    public ResponseEntity registerUser(@RequestBody Account account) {
        if (accountService.findByUsername(account.getUsername()) != null) {
            return ResponseEntity.status(409).body("");
        }
        if (account.getUsername().length() == 0 || account.getPassword().length() < 4) {
            return ResponseEntity.status(400).body("");
        }
        return ResponseEntity.status(200).body(accountService.registerUser(account));
    }

    @PostMapping(value = "login")
    public ResponseEntity login(@RequestBody Account account) {
        Account loginAccount = accountService.login(account);
        if (loginAccount == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.status(200).body(loginAccount);
    }

    @PostMapping(value = "/messages")
    public ResponseEntity createMessage(@RequestBody Message message) {
        Message returnMessage = messageService.persistMessage(message);
        if (returnMessage == null) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.status(200).body(returnMessage);
    }

    @GetMapping(value = "/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping(value = "/messages/{message_id}")
    public Message getMessageById(@PathVariable int message_id) {
        return messageService.getMessageById(message_id);
    }

    @DeleteMapping(value = "/messages/{message_id}")
    public ResponseEntity deleteMessageById(@PathVariable int message_id) {
        Message message = messageService.deleteMessageById(message_id);
        if (message == null) {
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(200).body(1);
    }

    @PatchMapping(value = "/messages/{message_id}")
    public ResponseEntity updateMessageById(@PathVariable int message_id, @RequestBody Message message) {
        Message updatedMessage = messageService.updateMessageById(message_id, message);
        if (updatedMessage == null) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.status(200).body(1);
    }

    @GetMapping(value = "/accounts/{account_id}/messages")
    public List<Message> getAllMessagesByAccountId(@PathVariable int account_id) {
        return messageService.getAllMessagesByUserId(account_id);
    }
}
