package com.dahohelping.dahohelping_springboot.service;

import com.dahohelping.dahohelping_springboot.entity.Card;
import com.dahohelping.dahohelping_springboot.entity.University;
import com.dahohelping.dahohelping_springboot.entity.User;
import com.dahohelping.dahohelping_springboot.exception.AppException;
import com.dahohelping.dahohelping_springboot.exception.ErrorCode;
import com.dahohelping.dahohelping_springboot.mapper.CardMapper;
import com.dahohelping.dahohelping_springboot.mapper.CustomMapper;
import com.dahohelping.dahohelping_springboot.repository.CardRepository;
import com.dahohelping.dahohelping_springboot.repository.UniversityRepository;
import com.dahohelping.dahohelping_springboot.repository.UserRepository;
import com.dahohelping.dahohelping_springboot.service.dto.response.CardResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.UniversityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private CustomMapper customMapper;
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CardRepository cardRepository;


    public Set<UniversityResponse> getUniversityByNameContaining(String s) {
        List<University> list = universityRepository.findByNameContaining(s);
        Set<UniversityResponse> _list = new HashSet<>();
        for (University i : list) {
            _list.add(customMapper.toUniversityResponse(i));
        }
        return _list;
    }

    public Set<CardResponse> getCardsByTitleContaining(String keyword) {
        List<Card> cards = cardRepository.findByTitleContaining(keyword);
        return cards.stream()
                .map(cardMapper::toCardResponse)
                .collect(Collectors.toSet());
    }

}



