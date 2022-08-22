package com.copus.parser.domain.info.meta;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 메타 정보
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MetaInfo {
    @Id
    @Column(name = "meta_info_id")
    private Long id;

    public MetaInfo(Long id) {
        this.id = id;
    }
}
