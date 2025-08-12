package com.ugovslima.shortener.controllers;

import com.ugovslima.shortener.domain.Link;
import com.ugovslima.shortener.dtos.LinkDTO;
import com.ugovslima.shortener.services.LinkService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api/links")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @PostMapping("/shorten")
    public ResponseEntity<LinkDTO> shortenLink(@RequestBody Map<String, String> request) {
        String originalLink = request.get("originalLink");

        Link existingLink = linkService.findByOriginalLink(originalLink);
        if (existingLink != null) {
            String existingFullShortLink = "http://localhost:8080/api/links/" + existingLink.getShortLink();
            String existingQrCodeFullLink = "http://localhost:8080/api/qrcode/generate?url=" + existingFullShortLink;

            LinkDTO existingDTO = new LinkDTO(
                    existingLink.getId(),
                    existingFullShortLink,
                    existingLink.getOriginalLink(),
                    existingLink.getCreatedAt(),
                    existingQrCodeFullLink
            );
            return ResponseEntity.status(409).body(existingDTO);
        }

        String newLink = linkService.generateShortLink(originalLink);
        String fullShortLink = "http://localhost:8080/api/links/" + newLink;
        String fullQrCodeLink = "http://localhost:8080/api/qrcode/generate?url=" + fullShortLink;

        Link link = new Link();
        link.setShortLink(newLink);
        link.setOriginalLink(originalLink);
        link.setCreatedAt(java.time.LocalDateTime.now());

        link.setQrCodeLink(newLink);

        Link savedLink = linkService.saveLink(link);

        LinkDTO linkDTO = new LinkDTO(
                savedLink.getId(),
                fullShortLink,
                savedLink.getOriginalLink(),
                savedLink.getCreatedAt(),
                fullQrCodeLink
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
        linkService.clicksCount(link);
    }

    @GetMapping("/clicks")
    public ResponseEntity<Integer> getClicksCount(@RequestParam String url) {
        int lastSlash = url.lastIndexOf('/');
        if (lastSlash == -1 || lastSlash == url.length() - 1) {
            return ResponseEntity.badRequest().build();
        }
        String shortLink = url.substring(lastSlash + 1);
        Link link = linkService.getOriginalUrl(shortLink);
        if (link == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(link.getClicksCount());
    }
}
