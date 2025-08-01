package com.ugovslima.shortener.dtos;

import java.time.LocalDateTime;

public record LinkDTO(
    Long id,
    String shortLink,
    String originalLink,
    LocalDateTime createdAt,
    String qrCodeLink,
    int clicksCount
) {
    public LinkDTO(Long id, String shortLink, String originalLink, LocalDateTime createdAt, String qrCodeLink) {
        this(id, shortLink, originalLink, createdAt, qrCodeLink, 0);
    }
}