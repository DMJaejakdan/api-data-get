package com.dmj.dmzdbtest.content.entity;

import com.dmj.dmzdbtest.content.dto.response.DramaDetailResponse;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DramaInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    private LocalDate endDate;

    private String channel;

    @Builder
    public DramaInfo(Content content, LocalDate endDate, String channel) {
        this.content = content;
        this.endDate = endDate;
        this.channel = channel;
    }

    public static DramaInfo toEntity(Content content, DramaDetailResponse dramaDetailResponse) {
        return DramaInfo.builder()
                .content(content)
                .endDate(LocalDate.parse(dramaDetailResponse.getLastAirDate()))
                .channel(dramaDetailResponse.getNetworks().get(0).getName())
                .build();
    }

    @Getter
//    @AllArgsConstructor
    public enum DramaChannel {
        TVN,
        MBC,
        SBS,
        KBS
//        private final String channel;
    }
}

