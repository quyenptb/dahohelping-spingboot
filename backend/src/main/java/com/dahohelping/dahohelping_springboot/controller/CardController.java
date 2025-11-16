package com.dahohelping.dahohelping_springboot.controller;

import com.dahohelping.dahohelping_springboot.service.CardService;
import com.dahohelping.dahohelping_springboot.service.dto.request.CardCreationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.ApiResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.CardResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApiResponse<CardResponse>> createCard(@RequestBody @Valid CardCreationRequest request) {
        return cardService.createCard(request);
    }

    @GetMapping("/filter")
    public List<CardResponse> filterCards(
            @RequestParam(required = false) Integer dahoId,
            @RequestParam(required = false) Integer uniId,
            @RequestParam(required = false) Integer falId,
            @RequestParam(required = false) Integer majId,
            @RequestParam(required = false) Integer subId
    ) {
        return cardService.getCardByFilter(dahoId, uniId, falId, majId, subId);
    }

    @GetMapping("/details")
    public List<CardResponse> getAllCardDetails() {
        return cardService.getAllCardDetails();
    }

    @GetMapping("/id/{cardId}")
    public CardResponse getCardById(@PathVariable("cardId") Integer cardId) {
        return cardService.getCardById(cardId);
    }

    @GetMapping("/user/{username}")
    public List<CardResponse> getCardsByUsername(@PathVariable String username) {
        return cardService.getCardsByUsername(username);
    }


    @GetMapping("/title/{title}")
    public CardResponse getCardByTitle(@PathVariable("title") String title) {
        return cardService.getCardByTitle(title);
    }

    @DeleteMapping("/delete/{cardId}")
    public ResponseEntity<String> deleteCardById(@PathVariable Integer cardId) {
        boolean deleted = cardService.deleteCardById(cardId);
        if (deleted) {
            return ResponseEntity.ok("Card deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found");
        }
    }

}