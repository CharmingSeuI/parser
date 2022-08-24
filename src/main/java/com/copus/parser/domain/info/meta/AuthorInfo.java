package com.copus.parser.domain.info.meta;

import com.copus.parser.domain.enums.AuthorType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 저자 정보
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_info_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private AuthorType type;

    @OneToOne
    @JoinColumn(name = "meta_info_id")
    private MetaInfo metaInfo;

    public AuthorInfo(AuthorType type, MetaInfo metaInfo) {
        this.type = type;
        this.metaInfo = metaInfo;
    }
}
