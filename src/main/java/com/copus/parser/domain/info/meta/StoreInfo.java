package com.copus.parser.domain.info.meta;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 소장 정보
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_info_id")
    private Long id;

    @OneToMany(mappedBy = "storeInfo")
    private List<StoreHouse> storeHouse = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "meta_info_id")
    private MetaInfo metaInfo;

    public StoreInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }
}
