package com.copus.parser.domain.info.meta;

import com.copus.parser.domain.enums.CategoryType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @Column(name = "category_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private CategoryType type;

    @ManyToOne
    @JoinColumn(name = "category_info_id")
    private CategoryInfo categoryInfo;

    @OneToMany(mappedBy = "category")
    private List<CategoryBody> categoryBodies = new ArrayList<>();


    public Category(Long id, CategoryType type, CategoryInfo categoryInfo) {
        this.id = id;
        this.type = type;
        this.categoryInfo = categoryInfo;
    }
}
