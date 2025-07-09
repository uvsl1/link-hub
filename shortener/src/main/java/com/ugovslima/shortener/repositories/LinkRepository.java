package com.ugovslima.shortener.repositories;

import com.ugovslima.shortener.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    boolean existsByShortLink(String shortLink);
    Link findByShortLink(String shortLink);
}
