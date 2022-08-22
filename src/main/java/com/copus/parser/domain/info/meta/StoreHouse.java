package com.copus.parser.domain.info.meta;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreHouse {
    @Id
    @Column(name = "store_house_id")
    private Long id;

    private String storeHouseText;

    @ManyToOne
    @JoinColumn(name = "store_info_id")
    private StoreInfo storeInfo;

    public StoreHouse(Long id, String storeHouseText, StoreInfo storeInfo) {
        this.id = id;
        this.storeHouseText = storeHouseText;
        this.storeInfo = storeInfo;
    }
}
