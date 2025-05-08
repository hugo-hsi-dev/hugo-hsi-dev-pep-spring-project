package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidCredentialsException;
import com.example.exception.InvalidDataException;
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
AccountService accountService;

@Autowired
MessageService messageService;

// === Endpoints ===
@PostMapping("/register")
@ResponseStatus(HttpStatus.OK)
public @ResponseBody Account register(@RequestBody Account account) {
    return accountService.register(account);

}

@PostMapping("/login")
@ResponseStatus(HttpStatus.OK)
public @ResponseBody Account login(@RequestBody Account account) {
    return accountService.login(account);
}

@PostMapping("/messages")
@ResponseStatus(HttpStatus.OK)
public @ResponseBody Message createMessage(@RequestBody Message message) {
    return messageService.create(message);
}

@GetMapping("/messages")
@ResponseStatus(HttpStatus.OK)
public @ResponseBody List<Message> getMessages() {
    return messageService.getAll();
}

@GetMapping("/messages/{messageId}")
@ResponseStatus(HttpStatus.OK)
public @ResponseBody Message getMessageById(@PathVariable int messageId) {
    return messageService.getById(messageId);
}


@DeleteMapping("/messages/{messageId}")
@ResponseStatus(HttpStatus.OK)
public @ResponseBody Integer deleteMessageById(@PathVariable int messageId) {
    Message message = messageService.deleteById(messageId);
    if (message != null) {
        return 1;
    }
    return null;
}

@PatchMapping("/messages/{messageId}")
@ResponseStatus(HttpStatus.OK)
public @ResponseBody Integer deleteMessageById(@PathVariable int messageId, @RequestBody Message message) {
    message.setMessageId(messageId);
    messageService.updateMessageTextById(message);
    return 1;
}

@GetMapping("/accounts/{accountId}/messages")
@ResponseStatus(HttpStatus.OK)
public @ResponseBody List<Message> getMessagesByAccountId(@PathVariable int accountId) {
    return messageService.getMessagesByAccountId(accountId);
}

// === Exceptions ===
@ExceptionHandler(InvalidDataException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public @ResponseBody String handleBadRequest(InvalidDataException ex) {
    return ex.getMessage();
}

@ExceptionHandler(DuplicateUsernameException.class)
@ResponseStatus(HttpStatus.CONFLICT)
public @ResponseBody String handleConflict(DuplicateUsernameException ex) {
    return ex.getMessage();
}

@ExceptionHandler(InvalidCredentialsException.class)
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public @ResponseBody String handleUnauthorized(InvalidCredentialsException ex) {
    return ex.getMessage();
}

}
