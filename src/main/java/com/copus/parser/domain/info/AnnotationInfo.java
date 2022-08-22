package com.copus.parser.domain.info;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 주석 정보
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnnotationInfo {
    @Id
    @Column(name = "annotation_info_id")
    private Long id;

    @OneToMany(mappedBy = "annotationInfo")
    private List<Annotation> annotations = new ArrayList<>();

    public AnnotationInfo(Long id) {
        this.id = id;
    }
}
