package com.copus.parser.domain.info.meta;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 목차 정보
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChapterInfo {
    @Id
    @Column(name = "chapter_info_id")
    private Long id;

    @Lob
    private String chapterInfoText;

    @OneToOne
    @JoinColumn(name = "meta_info_id")
    private MetaInfo metaInfo;

    public ChapterInfo(Long id, String chapterInfoText, MetaInfo metaInfo) {
        this.id = id;
        this.chapterInfoText = chapterInfoText;
        this.metaInfo = metaInfo;
    }
}

