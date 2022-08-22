package com.copus.parser.domain.info.body;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 본문 정보
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BodyInfo {
    @Id
    @Column(name = "body_info")
    private Long id;

    public BodyInfo(Long id) {
        this.id = id;
    }
}
