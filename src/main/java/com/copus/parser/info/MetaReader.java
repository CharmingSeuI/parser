package com.copus.parser.info;

import com.copus.parser.domain.enums.*;
import com.copus.parser.domain.info.meta.*;
import com.copus.parser.exception.ExtraAuthorInfoException;
import com.copus.parser.exception.ExtraMetaInfoException;
import com.copus.parser.exception.ExtraPublishInfoException;
import com.copus.parser.level.InfoIdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;

import static com.copus.parser.info.InfoReader.nodeToString;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MetaReader {

    private final EntityManager em;
    private Long metaInfoSequence = 0L;
    private Long titleInfoSequence = 0L;
    private Long titleSequence = 0L;
    private Long authorInfoSequence = 0L;
    private Long authorSequence = 0L;
    private Long publishInfoSequence = 0L;
    private Long storeInfoSequence =0L;
    private Long storeHouseSequence =0L;
    private Long referInfoSequence = 0L;
    private Long referToSequence = 0L;
    private Long referBodySequence = 0L;
    private Long categoryInfoSequence = 0L;
    private Long categorySequence = 0L;
    private Long categoryBodySequence = 0L;
    private Long chapterInfoSequence = 0L;


    public void read(Document doc) {
        NodeList metaInfos = doc.getElementsByTagName("메타정보");

        for (int metaInfoIndex = 0; metaInfoIndex < metaInfos.getLength(); metaInfoIndex++) {
            Node metaInfo = metaInfos.item(metaInfoIndex);

            Long meta_info_id = metaInfoSequence++;
            MetaInfo metaInfoData = new MetaInfo(meta_info_id);
            em.persist(metaInfoData);

            //Save parent level's id for level table Parsing
            String level_id = metaInfo.getParentNode().getAttributes().getNamedItem("id").getNodeValue();
            InfoIdRepository.metaInfoId.put(level_id, meta_info_id);

            //제목, 저자, 간행, 소장, 참조, 분류, 목차 ( 월고집 한정 )
            NodeList metaInfoChildNodes = metaInfo.getChildNodes();
            for (int metaIndex = 0; metaIndex < metaInfoChildNodes.getLength(); metaIndex++) {
                Node meta = metaInfoChildNodes.item(metaIndex);
                String metaTagName = meta.getNodeName();
                switch (metaTagName) {
                    case "제목정보" -> readTitle(meta, metaInfoData);
                    case "저자정보" -> readAuthor(meta, metaInfoData);
                    case "간행정보" -> readPublish(meta, metaInfoData);
                    case "소장정보" -> readStore(meta, metaInfoData);
                    case "참조정보" -> readRefer(meta, metaInfoData);
                    case "분류정보" -> readCategory(meta, metaInfoData);
                    case "목차정보" -> readChapter(meta, metaInfoData);
                    case "#text", "#comment" -> { }
                    default -> {
                        log.info("metaTagName = " + metaTagName);
                        throw new ExtraMetaInfoException("기존의 메타 정보로는 처리할 수 없는 데이터입니다");
                    }
                }
            }
        }
    }

    /**
     * 목차 정보
     */
    private void readChapter(Node meta, MetaInfo metaInfo) {
        String chapter_info_text = nodeToString(meta);
        ChapterInfo chapterInfo = new ChapterInfo(chapterInfoSequence++, chapter_info_text, metaInfo);
        em.persist(chapterInfo);
    }

    /**
     * 분류 정보
     */
    private void readCategory(Node meta, MetaInfo metaInfo) {
        Long category_info_id = categoryInfoSequence++;
        CategoryInfo categoryInfo = new CategoryInfo(category_info_id, metaInfo);
        em.persist(categoryInfo);

        NodeList categories = meta.getChildNodes();
        for (int categoryIndex = 0; categoryIndex < categories.getLength(); categoryIndex++) {
            Node category = categories.item(categoryIndex);
            if (category.getNodeName().equals("#text")) continue;

            CategoryType categoryType = CategoryType.valueOf(category.getAttributes().getNamedItem("type").getNodeValue());
            Long category_id = categorySequence++;
            Category categoryData = new Category(category_id, categoryType, categoryInfo);
            em.persist(categoryData);

            NodeList categoryBodies = category.getChildNodes();
            for (int categoryBodyIndex = 0; categoryBodyIndex < categoryBodies.getLength(); categoryBodyIndex++) {
                Node categoryBody = categoryBodies.item(categoryBodyIndex);
                if (categoryBody.getNodeName().equals("#text")) continue;

                CategoryBodyType categoryBodyType = Optional.ofNullable(categoryBody.getAttributes().getNamedItem("type"))
                        .map(n -> CategoryBodyType.valueOf(n.getNodeValue())).orElse(null);
                String category_body_text = categoryBody.getTextContent().trim();

                CategoryBody categoryBodyData = new CategoryBody(categoryBodySequence++, category_body_text, categoryBodyType, categoryData);
                em.persist(categoryBodyData);
            }
        }
    }

    /**
     * 참조 정보
     */
    private void readRefer(Node meta, MetaInfo metaInfo) {
        Long refer_info_id = referInfoSequence++;
        ReferInfo referInfo = new ReferInfo(refer_info_id, metaInfo);
        em.persist(referInfo);

        NodeList referToes = meta.getChildNodes();
        for (int referToIndex = 0; referToIndex < referToes.getLength(); referToIndex++) {
            Node referTo = referToes.item(referToIndex);
            if (referTo.getNodeName().equals("#text")) continue;

            ReferToType referToType = ReferToType.valueOf(referTo.getAttributes().getNamedItem("type").getNodeValue());
            ReferTo referToData = new ReferTo(referToSequence++, referToType, referInfo);
            em.persist(referToData);

            NodeList referBodies = referTo.getChildNodes();
            for (int referBodyIndex = 0; referBodyIndex < referBodies.getLength(); referBodyIndex++) {
                Node referBody = referBodies.item(referBodyIndex);
                if (referBody.getNodeName().equals("#text")) continue;

                ReferBodyType referBodyType = ReferBodyType.valueOf(referBody.getAttributes().getNamedItem("type").getNodeValue());
                String refer_body_text = referBody.getTextContent().trim();

                ReferBody referBodyData = new ReferBody(referBodySequence++, refer_body_text, referBodyType, referToData);
                em.persist(referBodyData);
            }
        }
    }

    /**
     * 소장 정보
     */
    private void readStore(Node meta, MetaInfo metaInfo) {
        Long store_info_id = storeInfoSequence++;
        StoreInfo storeInfo = new StoreInfo(store_info_id, metaInfo);
        em.persist(storeInfo);

        NodeList storeHouses = meta.getChildNodes();
        for (int storeHouseIndex = 0; storeHouseIndex < storeHouses.getLength(); storeHouseIndex++) {
            Node storeHouse = storeHouses.item(storeHouseIndex);
            if (storeHouse.getNodeName() == "#text") continue;
            String store_house_text = storeHouse.getTextContent().trim();
            StoreHouse storeHouseData = new StoreHouse(storeHouseSequence++, store_house_text, storeInfo);
            em.persist(storeHouseData);
        }
    }

    /**
     * 간행 정보
     */
    private void readPublish(Node meta, MetaInfo metaInfo) {
        NodeList publishInfos = meta.getChildNodes();

        //publishInfos
        String data_format = null;
        LocalDate explanation_date = null;
        String language = null;
        String original_publish_year = null;
        String publish_duration_end = null;
        String publish_duration_start = null;
        String publish_office = null;
        String publish_year = null;
        String zipsu = null;
        String zipsu_end = null;
        String zipsu_index = null;
        String zipsu_start = null;

        for (int publishInfoIndex = 0; publishInfoIndex < publishInfos.getLength(); publishInfoIndex++) {
            Node publishInfo = publishInfos.item(publishInfoIndex);
            NamedNodeMap publishInfoAttributes = publishInfo.getAttributes();
            String publishInfoTextContent = publishInfo.getTextContent().trim();
            switch (publishInfo.getNodeName()) {
                case "집수":
                    zipsu_end = publishInfoAttributes.getNamedItem("끝번호").getNodeValue();
                    zipsu_start = publishInfoAttributes.getNamedItem("시작번호").getNodeValue();
                    zipsu = publishInfoTextContent;
                    break;
                case "간행기간":
                    publish_duration_start = publishInfoAttributes.getNamedItem("시작년").getNodeValue();
                    publish_duration_end = publishInfoAttributes.getNamedItem("종료년").getNodeValue();
                    publish_year = publishInfoTextContent;
                    break;
                case "원문간행년":
                    original_publish_year = publishInfoTextContent;
                    break;
                case "간행처":
                    publish_office = publishInfoTextContent;
                    break;
                case "자료형식":
                    data_format = publishInfoTextContent;
                    break;
                case "언어":
                    language = publishInfoTextContent;
                    break;
                case "집수번호":
                    zipsu_index = publishInfoTextContent;
                    break;
                case "#text":
                    continue;
                default:
                    throw new ExtraPublishInfoException("처리할 수 없는 간행 테이블 입력");
            }
        }

        //null : 월고집에선 확인 불가능한 Attr( explanation_date )
        PublishInfo publishInfo = new PublishInfo(publishInfoSequence++, data_format, explanation_date, language, original_publish_year, publish_duration_end, publish_duration_start, publish_office, publish_year, zipsu, zipsu_end, zipsu_index, zipsu_start, metaInfo);
        em.persist(publishInfo);
    }

    /**
     * 저자 정보
     */
    private void readAuthor(Node meta, MetaInfo metaInfo) {
        Long author_info_id = authorInfoSequence++;
        AuthorType type = AuthorType.valueOf(meta.getAttributes().getNamedItem("type").getNodeValue());
        AuthorInfo authorInfoData = new AuthorInfo(author_info_id, type, metaInfo);
        em.persist(authorInfoData);

        NodeList authors = meta.getChildNodes();
        for (int authorIndex = 0; authorIndex < authors.getLength(); authorIndex++) {
            Node author = authors.item(authorIndex);
            if (author.getNodeName().equals("#text")) continue;

            //authorInfos
            String birth = null;
            String birth_alias = null;
            String death = null;
            String death_alias = null;
            String etc = null;
            String name_chn = null;
            String name_kor = null;
            String nick_name = null;
            NickNameType nick_name_type = null;


            NodeList authorInfos = author.getChildNodes();
            for (int authorInfoIndex = 0; authorInfoIndex < authorInfos.getLength(); authorInfoIndex++) {
                Node authorInfo = authorInfos.item(authorInfoIndex);
                switch (authorInfo.getNodeName()) {
                    case "한글성명":
                        name_kor = authorInfo.getTextContent().trim();
                        break;
                    case "한자성명":
                        name_chn = authorInfo.getTextContent().trim();
                        break;
                    case "생년":
                        birth_alias = authorInfo.getAttributes().getNamedItem("서기년").getNodeValue();
                        birth = authorInfo.getTextContent().trim();
                        break;
                    case "몰년":
                        death_alias = authorInfo.getAttributes().getNamedItem("서기년").getNodeValue();
                        death = authorInfo.getTextContent().trim();
                        break;
                    case "#text":
                        continue;
                    default:
                        throw new ExtraAuthorInfoException("처리할 수 없는 저자 테이블 필드 입력");
                }
            }

            //null : 월고집에선 확인 불가능한 Attr( etc, nick_name, nick_name_type )
            Author authorData = new Author(authorSequence++, birth, birth_alias, death, death_alias, etc, name_chn, name_kor, nick_name, nick_name_type, authorInfoData);
            em.persist(authorData);
        }
    }

    /**
     * 제목 정보
     */
    private void readTitle(Node meta, MetaInfo metaInfo) {
        Long title_info_id = titleInfoSequence++;
        TitleInfo titleInfo = new TitleInfo(title_info_id, metaInfo);
        em.persist(titleInfo);

        NodeList titles = meta.getChildNodes();
        for (int titleIndex = 0; titleIndex < titles.getLength(); titleIndex++) {
            Node title = titles.item(titleIndex);
            if (title.getNodeName().equals("#text")) continue;

            TitleType type = Optional.ofNullable(title.getAttributes().getNamedItem("type"))
                    .map(n -> TitleType.valueOf(n.getNodeValue())).orElse(null);

            String title_text = "";
            NodeList titleContents = title.getChildNodes();
            for (int i = 0; i < titleContents.getLength(); i++) {
                title_text += nodeToString(titleContents.item(i)).trim();
            }

            //null : 월고집에선 확인 불가능한 Attr( date_num, weather )
            Title titleData = new Title(titleSequence++, null, title_text, type, null, titleInfo);
            em.persist(titleData);
        }
    }
}
