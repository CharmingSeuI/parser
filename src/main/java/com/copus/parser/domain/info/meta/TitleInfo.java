package com.copus.parser.domain.info.meta;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 제목 정보
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TitleInfo {
    @Id
    @Column(name = "title_info_id")
    private Long id;

    @OneToMany(mappedBy = "titleInfo")
    private List<Title> titles = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "meta_info_id")
    private MetaInfo metaInfo;

    public TitleInfo(Long id, MetaInfo metaInfo) {
        this.id = id;
        this.metaInfo = metaInfo;
    }
}
