package com.copus.parser.info;

import com.copus.parser.domain.enums.CommentType;
import com.copus.parser.domain.info.AnnotationInfo;
import com.copus.parser.domain.info.Commentary;
import com.copus.parser.domain.info.CommentaryInfo;
import com.copus.parser.domain.info.ConnectionInfo;
import com.copus.parser.domain.info.body.BodyInfo;
import com.copus.parser.domain.info.meta.MetaInfo;
import com.copus.parser.level.InfoIdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.persistence.EntityManager;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
public class CommentaryReader {
    private final EntityManager em;

    private Long commentaryInfoSequence = 0L;

    public void read(Document doc) {
        NodeList commentaryInfos = doc.getElementsByTagName("해설정보");
        Node commentaryInfo = commentaryInfos.item(0);
        Long commentary_info_id = commentaryInfoSequence++;

        CommentaryInfo commentaryInfoData = new CommentaryInfo(commentary_info_id);
        em.persist(commentaryInfoData);

        String level_id = commentaryInfo.getParentNode().getAttributes().getNamedItem("id").getNodeValue();
        InfoIdRepository.commentaryInfoId.put(level_id, commentary_info_id);

        NodeList commentaries = commentaryInfo.getChildNodes();
        for (int i = 0; i < commentaries.getLength(); i++) {
            Node commentary = commentaries.item(i);
            if (commentary.getNodeName().equals("#text")) continue;

            NamedNodeMap commentaryAttributes = commentary.getAttributes();
            String commentary_id = commentaryAttributes.getNamedItem("id").getNodeValue();
            CommentType commentType = CommentType.valueOf(commentaryAttributes.getNamedItem("type").getNodeValue());

            AnnotationInfo annotationInfo = Optional.ofNullable(InfoIdRepository.annotationInfoId.get(commentary_id))
                    .map(n -> em.find(AnnotationInfo.class, n)).orElse(null);
            BodyInfo bodyInfo = Optional.ofNullable(InfoIdRepository.bodyInfoId.get(commentary_id))
                    .map(n -> em.find(BodyInfo.class, n)).orElse(null);
            ConnectionInfo connectionInfo = Optional.ofNullable(InfoIdRepository.connectionInfoId.get(commentary_id))
                    .map(n -> em.find(ConnectionInfo.class, n)).orElse(null);
            MetaInfo metaInfo = Optional.ofNullable(InfoIdRepository.metaInfoId.get(commentary_id))
                    .map(n -> em.find(MetaInfo.class, n)).orElse(null);

            Commentary commentaryData = new Commentary(commentary_id, commentType, annotationInfo, bodyInfo, commentaryInfoData, connectionInfo, metaInfo);
            em.persist(commentaryData);
        }

    }

}