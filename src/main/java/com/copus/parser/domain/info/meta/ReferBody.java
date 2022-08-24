package com.copus.parser.domain.info.meta;

import com.copus.parser.domain.enums.ReferBodyType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReferBody {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refer_body_id")
    private Long id;

    private String referBodyText;

    @Enumerated(value = EnumType.STRING)
    private ReferBodyType type;

    @ManyToOne
    @JoinColumn(name = "refer_to_id")
    private ReferTo referTo;

    public ReferBody(String referBodyText, ReferBodyType type, ReferTo referTo) {
        this.referBodyText = referBodyText;
        this.type = type;
        this.referTo = referTo;
    }
}
