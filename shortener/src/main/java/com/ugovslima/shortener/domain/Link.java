package com.ugovslima.shortener.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="links")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String shortLink;

    @Column(nullable = false)
    private String originalLink;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private String qrCodeLink;

    private int clicksCount = 0;

}
