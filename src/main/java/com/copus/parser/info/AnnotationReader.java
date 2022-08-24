package com.copus.parser.info;

import com.copus.parser.domain.enums.AnnotationType;
import com.copus.parser.domain.info.Annotation;
import com.copus.parser.domain.info.AnnotationInfo;
import com.copus.parser.exception.AnnotationTableException;
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


@Component
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AnnotationReader {

    private final EntityManager em;

    public void read(Document doc) {
        NodeList annotationInfos = doc.getElementsByTagName("주석정보");
        for (int i = 0; i < annotationInfos.getLength(); i++) {
            Node annotationInfo = annotationInfos.item(i);
            AnnotationInfo annotationInfoData = new AnnotationInfo();
            em.persist(annotationInfoData);
            Long annotation_info_id = annotationInfoData.getId();

            String level_id = annotationInfo.getParentNode().getAttributes().getNamedItem("id").getNodeValue();
            InfoIdRepository.annotationInfoId.put(level_id, annotation_info_id);

            NodeList annotationChilds = annotationInfo.getChildNodes();
            for (int j = 0; j < annotationChilds.getLength(); j++) {
                if (annotationChilds.item(j).getNodeName() == "주석항목") {
                    Node annotation = annotationChilds.item(j);
                    NamedNodeMap annotationAttributes = annotation.getAttributes();
                    String id = annotationAttributes.getNamedItem("id").getNodeValue();
                    AnnotationType type = AnnotationType.valueOf(annotationAttributes.getNamedItem("type").getNodeValue());
                    String annotation_name = null;
                    String annotation_body = null;
                    String imageOrTable = null;

                    NodeList annotationChildNodes = annotation.getChildNodes();
                    for (int h = 0; h < annotationChildNodes.getLength(); h++) {
                        if (annotationChildNodes.item(h).getNodeName() == "주석명") {
                            annotation_name = annotationChildNodes.item(h).getTextContent().trim();
                        } else if (annotationChildNodes.item(h).getNodeName() == "주석내용") {
                            annotation_body = annotationChildNodes.item(h).getTextContent().trim();
                        } else if (annotationChildNodes.item(h).getNodeName() != "#text") {
                            log.info("처리할 수 없는 주석 필드입니다");
                            throw new AnnotationTableException();
                        }
                    }
                    Annotation annotationData = new Annotation(annotation_body, annotation_name, id, imageOrTable, type, annotationInfoData);
                    //em.persist(annotationData);
                }
            }
        }
    }
}
