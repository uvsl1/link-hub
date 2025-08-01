package com.ugovslima.shortener.services;

import com.ugovslima.shortener.domain.Link;
import com.ugovslima.shortener.dtos.LinkDTO;
import com.ugovslima.shortener.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public String generateShortLink(String originalLink) {
        String randomLink;
        do {
            randomLink = RandomStringUtils.randomAlphanumeric(8);
        } while (linkRepository.existsByShortLink(randomLink));
        return randomLink;
    }

    public Link saveLink(Link link) {
        return linkRepository.save(link);
    }

    public Link findLinkById(Long id) {
        return linkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Link not found with id: " + id));
    }

    public Link getOriginalUrl(String shortLink) {
        try {
            return linkRepository.findByShortLink(shortLink);
        } catch (Exception e) {
            throw new RuntimeException("Link doesn't exist. " + e);
        }
    }

    public Link findByOriginalLink(String originalLink) {
        return linkRepository.findByOriginalLink(originalLink).orElse(null);
    }

    public void clicksCount(Link link) {
        link.setClicksCount(link.getClicksCount() + 1);
        linkRepository.save(link);
    }
}
