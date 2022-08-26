package com.copus.parser.info;

import com.copus.parser.domain.info.body.BodyInfo;
import com.copus.parser.domain.info.body.Content;
import com.copus.parser.level.InfoIdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.persistence.EntityManager;

import static com.copus.parser.info.InfoReader.nodeToString;

@Component
@RequiredArgsConstructor
@Transactional
public class BodyReader {
    private final EntityManager em;


    public void read(Document doc) {
        NodeList bodyInfos = doc.getElementsByTagName("본문정보");
        for (int i = 0; i < bodyInfos.getLength(); i++) {
            Node bodyInfo = bodyInfos.item(i);

            BodyInfo bodyInfoData = new BodyInfo();
            em.persist(bodyInfoData);
            Long body_info_id = bodyInfoData.getId();

            String level_id = bodyInfo.getParentNode().getAttributes().getNamedItem("id").getNodeValue();
            InfoIdRepository.bodyInfoId.put(level_id, body_info_id);

            NodeList bodyInfoChildNodes = bodyInfo.getChildNodes();
            String content_text = "";
            for (int j = 0; j < bodyInfoChildNodes.getLength(); j++) {
                if (bodyInfoChildNodes.item(j).getNodeName() == "내용") {
                    String textContent = bodyInfoChildNodes.item(j).getTextContent();
                    content_text = nodeToString(bodyInfoChildNodes.item(j));
                }
            }
            Content content = new Content(content_text, bodyInfoData);
            em.persist(content);
        }
    }
}
