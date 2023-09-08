package com.dmj.dmzdbtest;

import com.dmj.dmzdbtest.content.entity.Content;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    @Column(nullable = false)
    private String name;

    private Long tmdbId;

    @Builder
    public Company(Content content, String name, Long tmdbId) {
        this.content = content;
        this.name = name;
        this.tmdbId = tmdbId;
    }
}
