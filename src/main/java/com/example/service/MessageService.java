package com.example.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.InvalidDataException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message create(Message message) {
        String messageText = message.getMessageText();
        int postedBy = message.getPostedBy();

        Optional<Account> existingAccount = accountRepository.findById(postedBy);

        if (messageText.length() == 0 || messageText.length() > 255 || existingAccount.isEmpty()) {
            throw new InvalidDataException(messageText);
        }

        return messageRepository.save(message);
    }

    public List<Message> getAll() {
        return messageRepository.findAll();
    }

    public Message getById(int id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        return optionalMessage.orElse(null);
    }

    public Message deleteById(int id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isEmpty()) {
            return null;
        }
        messageRepository.deleteById(id);
        return message.get();
    }

    public Message updateMessageTextById(Message message) {
        int messageId = message.getMessageId();
        String messageText = message.getMessageText();
        
        if (messageText.length() == 0) {
            throw new InvalidDataException("Message must not be blank");
        }

        if (messageText.length() > 255) {
            throw new InvalidDataException("Message must not be greater than 255 characters long");
        }

        try {
            Message existingMessage = messageRepository.findById(messageId).orElseThrow();
            existingMessage.setMessageText(messageText);
            return messageRepository.save(existingMessage);
            
        } catch (NoSuchElementException e) {
            throw new InvalidDataException(e.getMessage());
        }

    }

    public List<Message> getMessagesByAccountId(int AccountId) {
        return messageRepository.findMessagesByPostedBy(AccountId);
    }
}
