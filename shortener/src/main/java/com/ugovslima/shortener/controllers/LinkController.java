package com.ugovslima.shortener.controllers;

import com.ugovslima.shortener.domain.Link;
import com.ugovslima.shortener.dtos.LinkDTO;
import com.ugovslima.shortener.services.LinkService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/links")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @PostMapping("/shorten")
    public ResponseEntity<LinkDTO> shortenLink(@RequestBody Map<String, String> request) {
        String originalLink = request.get("originalLink");
        String newLink = linkService.generateShortLink(originalLink);
        String fullShortLink = "http://localhost:8081/api/links/" + newLink;
        Link link = new Link();
        link.setShortLink(newLink);
        link.setOriginalLink(originalLink);
        link.setCreatedAt(java.time.LocalDateTime.now());
        Link savedLink = linkService.saveLink(link);
        LinkDTO linkDTO = new LinkDTO(
                savedLink.getId(),
                fullShortLink,
                savedLink.getOriginalLink(),
                savedLink.getCreatedAt()
        );
        return ResponseEntity.ok(linkDTO);
    }

    @GetMapping("/{shortLink}")
    public void redirectLink(@PathVariable String shortLink, HttpServletResponse response) throws IOException {
        Link link = linkService.getOriginalUrl(shortLink);

        if (link == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.sendRedirect(link.getOriginalLink());

    }
}
