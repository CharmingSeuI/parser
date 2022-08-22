package com.copus.parser.domain.level;

import com.copus.parser.domain.enums.Lv5Type;
import com.copus.parser.domain.info.AnnotationInfo;
import com.copus.parser.domain.info.ConnectionInfo;
import com.copus.parser.domain.info.body.BodyInfo;
import com.copus.parser.domain.info.meta.MetaInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lv5 {
    @Id @Column(name = "level_5_id")
    private String id;

    private String DCI;
    private LocalDate createDate;

    @Enumerated(value = EnumType.STRING)
    private Lv5Type type;

    @OneToOne
    @JoinColumn(name = "annotation_info_id")
    private AnnotationInfo annotationInfo;

    @OneToOne
    @JoinColumn(name = "body_info_id")
    private BodyInfo bodyInfo;

    @OneToOne
    @JoinColumn(name = "connection_info_id")
    private ConnectionInfo connectionInfo;

    @ManyToOne
    @JoinColumn(name = "level_4_id")
    private Lv4 lv4;

    @OneToOne
    @JoinColumn(name = "meta_info_id")
    private MetaInfo metaInfo;

    /**
     * level_5_id dci create_date type annotation_info_id body_info_id connection_info_id level_4_id meta_info_id
     */
    public Lv5(String id, String DCI, LocalDate createDate, Lv5Type type, AnnotationInfo annotationInfo, BodyInfo bodyInfo, ConnectionInfo connectionInfo, Lv4 lv4, MetaInfo metaInfo) {
        this.id = id;
        this.DCI = DCI;
        this.createDate = createDate;
        this.type = type;
        this.annotationInfo = annotationInfo;
        this.bodyInfo = bodyInfo;
        this.connectionInfo = connectionInfo;
        this.lv4 = lv4;
        this.metaInfo = metaInfo;
    }
}