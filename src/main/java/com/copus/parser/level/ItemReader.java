package com.copus.parser.level;

import com.copus.parser.domain.level.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.persistence.EntityManager;


@Component
@RequiredArgsConstructor
@Transactional
public class ItemReader {
    private final EntityManager em;

    /**
     * (item_id, name, org)
     */
    public void read(Document doc) {
        NodeList items = doc.getElementsByTagName("아이템");

        for (int i = 0; i < items.getLength(); i++) {
            Node item = items.item(i);
            NamedNodeMap itemAttributes = item.getAttributes();
            String item_id = itemAttributes.getNamedItem("id").getNodeValue();
            String name = itemAttributes.getNamedItem("name").getNodeValue();
            String org = itemAttributes.getNamedItem("org").getNodeValue();

            Item itemData = new Item(item_id, name, org);
            em.persist(itemData);
        }
    }
}
