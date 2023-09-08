package com.dmj.dmzdbtest.content.entity;

import com.dmj.dmzdbtest.data.response.DramaDetailResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        LocalDate localDate = null;
        if (dramaDetailResponse.getLastAirDate() != null) {
            localDate = LocalDate.parse(dramaDetailResponse.getLastAirDate());
        }
        return DramaInfo.builder()
                .content(content)
                .endDate(localDate)
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

