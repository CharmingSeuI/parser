package com.copus.parser.level;

import com.copus.parser.domain.enums.Lv4Type;
import com.copus.parser.domain.info.AnnotationInfo;
import com.copus.parser.domain.info.ConnectionInfo;
import com.copus.parser.domain.info.body.BodyInfo;
import com.copus.parser.domain.info.meta.MetaInfo;
import com.copus.parser.domain.level.Lv3;
import com.copus.parser.domain.level.Lv4;
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


@Component
@RequiredArgsConstructor
@Transactional
public class Level4Reader {
    private final EntityManager em;

    public void read(Document doc) {
        NodeList level4s = doc.getElementsByTagName("레벨4");
        for (int level4Index = 0; level4Index < level4s.getLength(); level4Index++) {
            Node level4 = level4s.item(level4Index);
            NamedNodeMap level4Attributes = level4.getAttributes();

            String level_4_id = level4Attributes.getNamedItem("id").getNodeValue();
            String type = level4Attributes.getNamedItem("type").getNodeValue();
            Lv4Type lv4Type = Lv4Type.valueOf(type);
            String DCI = Optional.ofNullable(level4Attributes.getNamedItem("DCI")).map(Node::getNodeValue).orElse("null");
            LocalDate create_date = LocalDate.parse(level4Attributes.getNamedItem("자료생성일").getNodeValue());
            String level_3_id = level4.getParentNode().getAttributes().getNamedItem("id").getNodeValue();
            Lv3 lv3 = em.find(Lv3.class, level_3_id);

            AnnotationInfo annotationInfo = InfoIdRepository.getAnnotationInfo(level_4_id, em);
            BodyInfo bodyInfo = InfoIdRepository.getBodyInfo(level_4_id, em);
            ConnectionInfo connectionInfo = InfoIdRepository.getConnectionInfo(level_4_id, em);
            MetaInfo metaInfo = InfoIdRepository.getMetaInfo(level_4_id, em);

            Lv4 lv4 = new Lv4(level_4_id, DCI, create_date, lv4Type, annotationInfo, bodyInfo, connectionInfo, lv3, metaInfo);
            em.persist(lv4);
        }
    }
}
