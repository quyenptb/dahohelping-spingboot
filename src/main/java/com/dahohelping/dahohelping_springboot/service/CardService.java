package com.dahohelping.dahohelping_springboot.service;

import com.dahohelping.dahohelping_springboot.entity.Card;
import com.dahohelping.dahohelping_springboot.exception.AppException;
import com.dahohelping.dahohelping_springboot.exception.ErrorCode;
import com.dahohelping.dahohelping_springboot.repository.CardRepository;
import com.dahohelping.dahohelping_springboot.service.dto.request.CardCreationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.request.CardFilterDto;
import com.dahohelping.dahohelping_springboot.service.dto.response.ApiResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.CardResponse;
import com.dahohelping.dahohelping_springboot.mapper.CardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardMapper cardMapper;

    @Transactional
    public ResponseEntity<ApiResponse<CardResponse>> createCard(@RequestBody CardCreationRequest request ) {
        ApiResponse<CardResponse> apiResponse = new ApiResponse<>();
        Card card = cardMapper.toCard(request);

        Card savedCardEntity = cardRepository.save(card);

        CardResponse savedCardResponse = cardRepository.findCardDetailById(savedCardEntity.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CARD_NOT_EXISTED));

        apiResponse.setResult(savedCardResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    public List<CardResponse> getAllCardDetails() {
        return cardRepository.findAllCardDetails();
    }

    public CardResponse getCardById(Integer id) {
        return cardRepository.findCardDetailById(id).orElseThrow(() ->
                new AppException(ErrorCode.CARD_NOT_EXISTED)
        );
    }

    public CardResponse getCardByTitle(String title) {
        return cardRepository.findCardDetailByTitle(title).orElseThrow(() ->
                new AppException(ErrorCode.CARD_NOT_EXISTED)
        );
    }

    public List<CardResponse> getCardsByTitleContaining(String keyword) {
        return cardRepository.findCardDetailsByTitleContaining(keyword);
    }

    public List<CardResponse> getCardsByUsername(String username) {
        return cardRepository.findCardDetailsByUsername(username);
    }

    public boolean deleteCardById(Integer id) {
        if (!cardRepository.existsById(id)) {
            throw new AppException(ErrorCode.CARD_NOT_EXISTED);
        }
        cardRepository.deleteById(id);
        return true;
    }

    @Transactional
    public ResponseEntity<?> deleteCardByTitle(String title) {
        cardRepository.deleteByTitle(title);
        return ResponseEntity.ok("Card deleted successfully");
    }


    public List<CardResponse> getCardByFilter(Integer dahoId, Integer uniId, Integer falId, Integer majId, Integer subId) {
        CardFilterDto filter = CardFilterDto.builder()
                .dahoId(dahoId)
                .uniId(uniId)
                .falId(falId)
                .majId(majId)
                .subId(subId)
                .build();
        return cardRepository.findFilteredCardDetails(filter);
    }
}