package com.ecommerce.service.impl;

import com.ecommerce.exception.*;
import com.ecommerce.model.entity.*;
import com.ecommerce.model.dto.request.newsletter.NewsletterSubscriptionRequest;
import com.ecommerce.model.dto.response.newsletter.*;
import com.ecommerce.model.dto.response.PaginatedResponse;
import com.ecommerce.repository.*;
import com.ecommerce.service.NewsletterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsletterServiceImpl implements NewsletterService {

    private final NewsletterRepository newsletterRepository;
    private final NewsletterTagRepository tagRepository;

    @Override
    @Transactional
    public void subscribe(NewsletterSubscriptionRequest request) {
        if (newsletterRepository.existsByEmail(request.getEmail())) {
            throw new com.ecommerce.exception.ConflictException("Email already subscribed");
        }
        Newsletter newsletter = Newsletter.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .isSubscribed(true)
                .unsubscribeToken(UUID.randomUUID().toString())
                .subscribedAt(LocalDateTime.now())
                .build();
        if (request.getTags() != null) {
            List<NewsletterTag> tags = tagRepository.findAllById(request.getTags());
            newsletter.setTags(tags);
        }
        newsletterRepository.save(newsletter);
        log.info("Newsletter subscription: {}", request.getEmail());
    }

    @Override
    @Transactional
    public void unsubscribe(String email) {
        newsletterRepository.findByEmail(email).ifPresent(n -> {
            n.setIsSubscribed(false);
            n.setUnsubscribedAt(LocalDateTime.now());
            newsletterRepository.save(n);
        });
    }

    @Override
    @Transactional
    public void unsubscribeByToken(String token) {
        newsletterRepository.findByUnsubscribeToken(token).ifPresent(n -> {
            n.setIsSubscribed(false);
            n.setUnsubscribedAt(LocalDateTime.now());
            newsletterRepository.save(n);
        });
    }

    @Override
    @Transactional
    public void confirmSubscription(String email) {
        newsletterRepository.findByEmail(email).ifPresent(n -> {
            n.setIsConfirmed(true);
            n.setConfirmedAt(LocalDateTime.now());
            newsletterRepository.save(n);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public NewsletterResponse getById(Long id) {
        Newsletter newsletter = newsletterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Newsletter", "id", id));
        return mapToResponse(newsletter);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isSubscribed(String email) {
        return newsletterRepository.findByEmail(email)
                .map(Newsletter::isSubscribed).orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<NewsletterResponse> getAllSubscribers(Pageable pageable) {
        Page<Newsletter> subscribers = newsletterRepository.findAll(pageable);
        return PaginatedResponse.of(
                subscribers.getContent().stream().map(this::mapToResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), subscribers.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<NewsletterResponse> getActiveSubscribers(Pageable pageable) {
        Page<Newsletter> subscribers = newsletterRepository.findByIsSubscribedTrue(pageable);
        return PaginatedResponse.of(
                subscribers.getContent().stream().map(this::mapToResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), subscribers.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public long countActive() {
        return newsletterRepository.countByIsSubscribedTrue();
    }

    @Override
    @Transactional
    public void deleteSubscriber(Long id) {
        newsletterRepository.deleteById(id);
    }

    @Override
    @Transactional
    public NewsletterTagResponse createTag(String name, String description) {
        NewsletterTag tag = NewsletterTag.builder().name(name).description(description).build();
        NewsletterTag saved = tagRepository.save(tag);
        return NewsletterTagResponse.builder().id(saved.getId()).name(saved.getName()).description(saved.getDescription()).build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsletterTagResponse> getAllTags() {
        return tagRepository.findAll().stream()
                .map(t -> NewsletterTagResponse.builder().id(t.getId()).name(t.getName()).description(t.getDescription()).build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    private NewsletterResponse mapToResponse(Newsletter n) {
        return NewsletterResponse.builder()
                .id(n.getId()).email(n.getEmail()).firstName(n.getFirstName()).lastName(n.getLastName())
                .isSubscribed(n.isSubscribed()).isConfirmed(n.isConfirmed())
                .subscribedAt(n.getSubscribedAt()).unsubscribedAt(n.getUnsubscribedAt())
                .confirmedAt(n.getConfirmedAt())
                .createdAt(n.getCreatedAt())
                .build();
    }
}
