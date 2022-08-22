package com.copus.parser.domain.info.body;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {
    @Id
    @Column(name = "content_id")
    private Long id;

    @Lob
    private String contentText;

    @OneToOne
    @JoinColumn(name = "body_info_id")
    private BodyInfo bodyInfo;

    public Content(Long id, String contentText, BodyInfo bodyInfo) {
        this.id = id;
        this.contentText = contentText;
        this.bodyInfo = bodyInfo;
    }
}
