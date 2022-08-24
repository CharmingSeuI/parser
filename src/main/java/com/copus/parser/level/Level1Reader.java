package com.copus.parser.level;

import com.copus.parser.domain.enums.Lv1Type;
import com.copus.parser.domain.info.CommentaryInfo;
import com.copus.parser.domain.info.meta.MetaInfo;
import com.copus.parser.domain.level.Item;
import com.copus.parser.domain.level.Lv1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;

/**
 * 해설 정보 누락
 */
@Component
@RequiredArgsConstructor
@Transactional
public class Level1Reader {
    private final EntityManager em;

    public void read(Document doc) {
        NodeList level1s = doc.getElementsByTagName("레벨1");
        for (int i = 0; i < level1s.getLength(); i++) {
            Node level1 = level1s.item(i);
            NamedNodeMap level1Attributes = level1.getAttributes();

            String level_1_id = level1Attributes.getNamedItem("id").getNodeValue();
            String type = level1Attributes.getNamedItem("type").getNodeValue();
            Lv1Type lv1Type = Lv1Type.valueOf(type);

            LocalDate create_date = LocalDate.parse(level1Attributes.getNamedItem("자료생성일").getNodeValue());

            String parent_lv1 = Optional.ofNullable(level1Attributes.getNamedItem("상위서지")).map(Node::getNodeValue).orElse(null);

            String item_id = level1.getParentNode().getAttributes().getNamedItem("id").getNodeValue();
            Item item = em.find(Item.class, item_id);

            CommentaryInfo commentaryInfo = InfoIdRepository.getCommentaryInfo(level_1_id, em);
            MetaInfo metaInfo = InfoIdRepository.getMetaInfo(level_1_id, em);

            Lv1 lv1 = new Lv1(level_1_id, create_date, parent_lv1, lv1Type, item, metaInfo, commentaryInfo);
            em.persist(lv1);
        }
    }
}
