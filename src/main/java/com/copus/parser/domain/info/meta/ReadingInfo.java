package com.copus.parser.domain.info.meta;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 성독 정보
 * 보류
 */
@Entity
@Getter
class ReadingInfo {
    @Id
    @Column(name = "reading_info_id")
    private Long id;

    @OneToMany(mappedBy = "readingInfo")
    private List<Reading> readings = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "meta_info_id")
    private MetaInfo metaInfo;
}
