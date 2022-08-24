package com.copus.parser.info;

import com.copus.parser.domain.enums.ConnectionType;
import com.copus.parser.domain.info.Connection;
import com.copus.parser.domain.info.ConnectionInfo;
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

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class ConnectionReader {

    private final EntityManager em;

    public void read(Document doc) {
        NodeList connectionInfos = doc.getElementsByTagName("연계정보");
        for (int i = 0; i < connectionInfos.getLength(); i++) {
            Node connectionInfo = connectionInfos.item(i);
            ConnectionInfo connectionInfoData = new ConnectionInfo();
            em.persist(connectionInfoData);
            Long connection_info_id = connectionInfoData.getId();

            String level_id = connectionInfo.getParentNode().getAttributes().getNamedItem("id").getNodeValue();
            InfoIdRepository.connectionInfoId.put(level_id, connection_info_id);

            NodeList connections = connectionInfo.getChildNodes();
            for (int j = 0; j < connections.getLength(); j++) {
                if (connections.item(j).getNodeName() == "연계항목") {
                    NamedNodeMap connectionAttributes = connections.item(j).getAttributes();
                    ConnectionType type = ConnectionType.valueOf(connectionAttributes.getNamedItem("type").getNodeValue());
                    String connection_start = connectionAttributes.getNamedItem("연계시작").getNodeValue().trim();
                    String connection_end = connectionAttributes.getNamedItem("연계종료").getNodeValue().trim();

                    Connection connection = new Connection(connection_end, connection_start, type, connectionInfoData);
                    em.persist(connection);
                }
            }
        }


    }
}
