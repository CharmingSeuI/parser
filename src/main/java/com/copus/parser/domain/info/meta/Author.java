package com.copus.parser.domain.info.meta;

import com.copus.parser.domain.enums.NickNameType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Author {
    @Id
    @Column(name = "author_id")
    private Long id;

    private String birth;
    private String birthAlias;
    private String death;
    private String deathAlias;

    private String etc;

    private String nameChn;
    private String nameKor;

    private String nickName;
    @Enumerated(value = EnumType.STRING)
    private NickNameType nickNameType;

    @OneToOne
    @JoinColumn(name = "author_info_id")
    private AuthorInfo authorInfo;

    public Author(Long id, String birth, String birthAlias, String death, String deathAlias, String etc, String nameChn, String nameKor, String nickName, NickNameType nickNameType, AuthorInfo authorInfo) {
        this.id = id;
        this.birth = birth;
        this.birthAlias = birthAlias;
        this.death = death;
        this.deathAlias = deathAlias;
        this.etc = etc;
        this.nameChn = nameChn;
        this.nameKor = nameKor;
        this.nickName = nickName;
        this.nickNameType = nickNameType;
        this.authorInfo = authorInfo;
    }
}
