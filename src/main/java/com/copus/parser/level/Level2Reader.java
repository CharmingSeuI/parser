package com.copus.parser.level;

import com.copus.parser.domain.enums.Lv2Type;
import com.copus.parser.domain.info.AnnotationInfo;
import com.copus.parser.domain.info.ConnectionInfo;
import com.copus.parser.domain.info.body.BodyInfo;
import com.copus.parser.domain.info.meta.MetaInfo;
import com.copus.parser.domain.level.Lv1;
import com.copus.parser.domain.level.Lv2;
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
public class Level2Reader {
    private final EntityManager em;

    public void read(Document doc) {
        NodeList level2s = doc.getElementsByTagName("레벨2");
        for (int level2Index = 0; level2Index < level2s.getLength(); level2Index++) {
            Node level2 = level2s.item(level2Index);
            NamedNodeMap level2Attributes = level2.getAttributes();

            String level_2_id = level2Attributes.getNamedItem("id").getNodeValue();
            String type = level2Attributes.getNamedItem("type").getNodeValue();
            Lv2Type lv2Type = Lv2Type.valueOf(type);

            String DCI = Optional.ofNullable(level2Attributes.getNamedItem("DCI")).map(n -> n.getNodeValue()).orElse("null");
            LocalDate create_date = LocalDate.parse(level2Attributes.getNamedItem("자료생성일").getNodeValue());

            String level_1_id = level2.getParentNode().getAttributes().getNamedItem("id").getNodeValue();
            Lv1 lv1 = em.find(Lv1.class, level_1_id);

            AnnotationInfo annotationInfo = Optional.ofNullable(InfoIdRepository.annotationInfoId.get(level_2_id))
                    .map(n -> em.find(AnnotationInfo.class, n)).orElse(null);
            BodyInfo bodyInfo = Optional.ofNullable(InfoIdRepository.bodyInfoId.get(level_2_id))
                    .map(n -> em.find(BodyInfo.class, n)).orElse(null);
            ConnectionInfo connectionInfo = Optional.ofNullable(InfoIdRepository.connectionInfoId.get(level_2_id))
                    .map(n -> em.find(ConnectionInfo.class, n)).orElse(null);
            MetaInfo metaInfo = Optional.ofNullable(InfoIdRepository.metaInfoId.get(level_2_id))
                    .map(n -> em.find(MetaInfo.class, n)).orElse(null);

            Lv2 lv2 = new Lv2(level_2_id, DCI, create_date, lv2Type, annotationInfo, bodyInfo, connectionInfo, lv1, metaInfo);
            em.persist(lv2);
        }
    }
}
