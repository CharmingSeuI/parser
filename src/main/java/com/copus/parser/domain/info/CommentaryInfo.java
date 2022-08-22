package com.copus.parser.domain.info;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 해설 정보
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentaryInfo {
    @Id
    @Column(name = "commentary_info_id")
    private Long id;

    @OneToMany(mappedBy = "commentaryInfo", cascade = CascadeType.ALL)
    private List<Commentary> commentaries = new ArrayList<>();

    public CommentaryInfo(Long id) {
        this.id = id;
    }
}
