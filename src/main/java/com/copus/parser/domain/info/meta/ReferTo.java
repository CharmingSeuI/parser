package com.copus.parser.domain.info.meta;

import com.copus.parser.domain.enums.ReferToType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReferTo {
    @Id
    @Column(name = "refer_to_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ReferToType type;

    @ManyToOne
    @JoinColumn(name = "refer_info_id")
    private ReferInfo referInfo;

    @OneToMany(mappedBy = "referTo")
    private List<ReferBody> referBodies = new ArrayList<>();

    public ReferTo(Long id, ReferToType type, ReferInfo referInfo) {
        this.id = id;
        this.type = type;
        this.referInfo = referInfo;
    }
}

