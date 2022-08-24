package com.copus.parser.domain.info;


import com.copus.parser.domain.enums.AnnotationType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Annotation {
    @Id
    @Column(name = "annotation_id")
    private Long id;

    @Lob
    private String annotationBody;
    private String annotationName;
    private String contentId;

    //삽화 or 표 라는 항목인데 월고집에서는 확인 불가 일단은 String
    private String imageOrTable;

    @Enumerated(value = EnumType.STRING)
    private AnnotationType type;

    @ManyToOne
    @JoinColumn(name = "annotation_info_id")
    private AnnotationInfo annotationInfo;

    public Annotation(Long id, String annotationBody, String annotationName, String contentId, String imageOrTable, AnnotationType type, AnnotationInfo annotationInfo) {
        this.id = id;
        this.annotationBody = annotationBody;
        this.annotationName = annotationName;
        this.contentId = contentId;
        this.imageOrTable = imageOrTable;
        this.type = type;
        this.annotationInfo = annotationInfo;
    }
}
