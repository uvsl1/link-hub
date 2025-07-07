package com.ugovslima.shortener.dtos;

import java.time.LocalDateTime;

public record LinkDTO(
    Long id,
    String shortLink,
    String originalLink,
    LocalDateTime createdAt
) {}
