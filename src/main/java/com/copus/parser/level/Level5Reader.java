package com.copus.parser.level;

import com.copus.parser.domain.enums.Lv5Type;
import com.copus.parser.domain.info.AnnotationInfo;
import com.copus.parser.domain.info.ConnectionInfo;
import com.copus.parser.domain.info.body.BodyInfo;
import com.copus.parser.domain.info.meta.MetaInfo;
import com.copus.parser.domain.level.Lv4;
import com.copus.parser.domain.level.Lv5;
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
public class Level5Reader {
    private final EntityManager em;

    public void read(Document doc) {
        NodeList level5s = doc.getElementsByTagName("레벨5");
        for (int level5Index = 0; level5Index < level5s.getLength(); level5Index++) {
            Node level5 = level5s.item(level5Index);
            NamedNodeMap level5Attributes = level5.getAttributes();

            String level_5_id = level5Attributes.getNamedItem("id").getNodeValue();
            String type = level5Attributes.getNamedItem("type").getNodeValue();
            Lv5Type lv5Type = Lv5Type.valueOf(type);
            String DCI = Optional.ofNullable(level5Attributes.getNamedItem("DCI")).map(Node::getNodeValue).orElse("null");
            LocalDate create_date = LocalDate.parse(level5Attributes.getNamedItem("자료생성일").getNodeValue());
            String level_4_id = level5.getParentNode().getAttributes().getNamedItem("id").getNodeValue();
            Lv4 lv4 = em.find(Lv4.class, level_4_id);

            AnnotationInfo annotationInfo = InfoIdRepository.getAnnotationInfo(level_5_id, em);
            BodyInfo bodyInfo = InfoIdRepository.getBodyInfo(level_5_id, em);
            ConnectionInfo connectionInfo = InfoIdRepository.getConnectionInfo(level_5_id, em);
            MetaInfo metaInfo = InfoIdRepository.getMetaInfo(level_5_id, em);

            Lv5 lv5 = new Lv5(level_5_id, DCI, create_date, lv5Type, annotationInfo, bodyInfo, connectionInfo, lv4, metaInfo);
            em.persist(lv5);
        }
    }
}
