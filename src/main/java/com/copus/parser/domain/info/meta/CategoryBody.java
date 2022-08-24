package com.copus.parser.domain.info.meta;

import com.copus.parser.domain.enums.CategoryBodyType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryBody {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_body_id")
    private Long id;

    private String categoryBodyText;

    @Enumerated(value = EnumType.STRING)
    private CategoryBodyType type;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public CategoryBody(String categoryBodyText, CategoryBodyType type, Category category) {
        this.categoryBodyText = categoryBodyText;
        this.type = type;
        this.category = category;
    }
}
