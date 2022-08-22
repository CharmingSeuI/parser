package com.copus.parser.level;

import com.copus.parser.domain.enums.Lv3Type;
import com.copus.parser.domain.info.AnnotationInfo;
import com.copus.parser.domain.info.ConnectionInfo;
import com.copus.parser.domain.info.body.BodyInfo;
import com.copus.parser.domain.info.meta.MetaInfo;
import com.copus.parser.domain.level.Lv2;
import com.copus.parser.domain.level.Lv3;
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
public class Level3Reader {
    private final EntityManager em;

    public void read(Document doc) {
        NodeList level3s = doc.getElementsByTagName("레벨3");
        for (int level3Index = 0; level3Index < level3s.getLength(); level3Index++) {
            Node level3 = level3s.item(level3Index);
            NamedNodeMap level3Attributes = level3.getAttributes();

            String level_3_id = level3Attributes.getNamedItem("id").getNodeValue();
            String type = level3Attributes.getNamedItem("type").getNodeValue();
            Lv3Type lv3Type = Lv3Type.valueOf(type);
            String DCI = Optional.ofNullable(level3Attributes.getNamedItem("DCI")).map(n -> n.getNodeValue()).orElse("null");
            LocalDate create_date = LocalDate.parse(level3Attributes.getNamedItem("자료생성일").getNodeValue());
            String level_2_id = level3.getParentNode().getAttributes().getNamedItem("id").getNodeValue();
            Lv2 lv2 = em.find(Lv2.class, level_2_id);

            AnnotationInfo annotationInfo = Optional.ofNullable(InfoIdRepository.annotationInfoId.get(level_3_id))
                    .map(n -> em.find(AnnotationInfo.class, n)).orElse(null);
            BodyInfo bodyInfo = Optional.ofNullable(InfoIdRepository.bodyInfoId.get(level_3_id))
                    .map(n -> em.find(BodyInfo.class, n)).orElse(null);
            ConnectionInfo connectionInfo = Optional.ofNullable(InfoIdRepository.connectionInfoId.get(level_3_id))
                    .map(n -> em.find(ConnectionInfo.class, n)).orElse(null);
            MetaInfo metaInfo = Optional.ofNullable(InfoIdRepository.metaInfoId.get(level_3_id))
                    .map(n -> em.find(MetaInfo.class, n)).orElse(null);

            Lv3 lv3 = new Lv3(level_3_id, DCI, create_date, lv3Type, annotationInfo, bodyInfo, connectionInfo, lv2, metaInfo);
            em.persist(lv3);
        }
    }
}
