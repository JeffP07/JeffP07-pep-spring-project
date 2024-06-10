package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message persistMessage(Message message) {
        if (message.getMessageText().length() > 0 && message.getMessageText().length() <= 255 && accountRepository.existsById(message.getPostedBy())) {
            return messageRepository.save(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            return message.get();
        }
        return null;
    }

    public Message deleteMessageById(int id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            messageRepository.delete(message.get());
            return message.get();
        }
        else {
            return null;
        }
    }

    public Message updateMessageById(int id, Message message) {
        Optional<Message> optMessage = messageRepository.findById(id);
        if (optMessage.isPresent() && message.getMessageText().length() > 0 && message.getMessageText().length() < 255) {
            Message toUpdate = optMessage.get();
            toUpdate.setMessageText(message.getMessageText());
            return messageRepository.save(toUpdate);
        }
        return null;
    }

    public List<Message> getAllMessagesByUserId(int id) {
        return messageRepository.findByPostedBy(id);
    }
}
