package com.dahohelping.dahohelping_springboot.service;

import com.dahohelping.dahohelping_springboot.entity.Card;
import com.dahohelping.dahohelping_springboot.entity.Role;
import com.dahohelping.dahohelping_springboot.entity.User;
import com.dahohelping.dahohelping_springboot.exception.AppException;
import com.dahohelping.dahohelping_springboot.exception.ErrorCode;
import com.dahohelping.dahohelping_springboot.mapper.CardMapper;
import com.dahohelping.dahohelping_springboot.mapper.CustomMapper;
import com.dahohelping.dahohelping_springboot.repository.CardRepository;
import com.dahohelping.dahohelping_springboot.service.dto.request.CardCreationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.ApiResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.CardResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardMapper cardMapper;

    public ResponseEntity<ApiResponse<CardResponse>> createCard(@RequestBody CardCreationRequest request ) {
        ApiResponse<CardResponse> apiResponse = new ApiResponse<CardResponse>();
        Card card = cardMapper.toCard(request);

        CardResponse savedCard = cardMapper.toCardResponse(cardRepository.save(card));

        apiResponse.setResult(savedCard);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    public Set<CardResponse> getAllCards() {
        List<Card> cards = cardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
        return cards.stream()
                .map(cardMapper::toCardResponse)
                .collect(Collectors.toSet());
    }

    public CardResponse getCardById(Integer id) {
        Card card = cardRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.CARD_NOT_EXISTED)
        );

        return cardMapper.toCardResponse(card);
    }

    public CardResponse getCardByTitle(String title) {
        Card card = cardRepository.findByTitle(title);
        if (card != null) {
            return cardMapper.toCardResponse(card);
        } else {
            throw new AppException(ErrorCode.CARD_NOT_EXISTED);
        }
    }

    public List<CardResponse> getCardsByTitleContaining(String keyword) {
        List<Card> cards = cardRepository.findByTitleContaining(keyword);
        return cards.stream()
                .map(cardMapper::toCardResponse)
                .collect(Collectors.toList());
    }

    public List<CardResponse> getCardsByDahohelpingId(Integer dahoId) {
        List<Card> cards = cardRepository.findByDahohelpingId(dahoId);
        return cards.stream()
                .map(cardMapper::toCardResponse)
                .collect(Collectors.toList());
    }

    public List<CardResponse> getCardsByUniversityId(Integer universityId) {
        List<Card> cards = cardRepository.findByUniversityId(universityId);
        return cards.stream()
                .map(cardMapper::toCardResponse)
                .collect(Collectors.toList());
    }

    public List<CardResponse> getCardsByUserId(Integer userId) {
        List<Card> cards = cardRepository.findByUserId(userId);
        return cards.stream()
                .map(cardMapper::toCardResponse)
                .collect(Collectors.toList());
    }

    public List<CardResponse> getCardsBySubjectId(Integer subjectId) {
        List<Card> cards = cardRepository.findBySubjectId(subjectId);
        return cards.stream()
                .map(cardMapper::toCardResponse)
                .collect(Collectors.toList());
    }

    public List<CardResponse> getCardsByDate(LocalDateTime date) {
        List<Card> cards = cardRepository.findByDate(date);
        return cards.stream()
                .map(cardMapper::toCardResponse)
                .collect(Collectors.toList());
    }

    public List<CardResponse> getCardsByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        List<Card> cards = cardRepository.findByCreatedDateBetween(startDate, endDate);
        return cards.stream()
                .map(cardMapper::toCardResponse)
                .collect(Collectors.toList());
    }

    public List<CardResponse> getReportedCards() {
        List<Card> cards = cardRepository.findReportedCard();
        return cards.stream()
                .map(cardMapper::toCardResponse)
                .collect(Collectors.toList());
    }

    public List<CardResponse> getUncommentedCards() {
        List<Card> cards = cardRepository.findIsNotCommentedCard();
        return cards.stream()
                .map(cardMapper::toCardResponse)
                .collect(Collectors.toList());
    }

    public boolean deleteCardById(Integer id) {
        if (!cardRepository.existsById(id)) {
            throw new AppException(ErrorCode.CARD_NOT_EXISTED);
        }
        cardRepository.deleteById(id);
        return true;
    }

    public ResponseEntity<?> deleteCardByTitle(String title) {
        cardRepository.deleteByTitle(title);
        return ResponseEntity.ok("Card deleted successfully");
    }
}
