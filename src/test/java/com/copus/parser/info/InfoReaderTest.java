package com.copus.parser.info;

import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Map;

class InfoReaderTest {

    private String fileName = "moon-light.xml";
    private static Map<String, Boolean> lineTag = Map.of("단락", true, "단락제목", true, "목차", true, "목차제목", true);

    @Test
    void text() {
        String a = "";
        addString(a);
        System.out.println("a = " + a);
    }

    void addString(String a) {
        a += "good";
    }

    @Test
    void nodeToStringForContent() throws Exception{
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        Document doc = builder.parse(fileName);

        NodeList contents = doc.getElementsByTagName("내용");
        Node content = contents.item(944);

        String text = myNodeToString(content, 5);
        System.out.println(text);
    }

    @Test
    public void nodeToStringForChapter() throws Exception{
        //given
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        Document doc = builder.parse(fileName);

        NodeList chapters = doc.getElementsByTagName("목차정보");
        Node chpater_info = chapters.item(0);

        //when
        String text = myNodeToString(chpater_info, 0);
        //then
        System.out.println(text);
     }

     @Test
     public void nodeToStringForTitleInfo() throws Exception{
         //given
         DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
         Document doc = builder.parse(fileName);

         NodeList titles = doc.getElementsByTagName("제목");

         //when
         for (int i = 0; i < titles.getLength(); i++) {
             String text = myNodeToString(titles.item(i), 0);
             //then
             System.out.println(text);
         }

      }

    private static String myNodeToString(Node node, int level) {
        String nodeName = node.getNodeName();
        if(nodeName.equals("line") || nodeName.equals("br")) return "</"+ nodeName +">\n" + "    ".repeat(level);
        String text = "";
        if (lineTag.get(nodeName) != null) {
            text += "\n" + "    ".repeat(level);
        }

        String tagName = "<" + nodeName;

        String attrString = ">";
        NamedNodeMap attributes = node.getAttributes();
        for (int i = attributes.getLength() - 1; i >= 0; i--) {
            Node attribute = attributes.item(i);
            String attrName = attribute.getNodeName();
            String attrValue = attribute.getNodeValue();
            attrString = " " + attrName + "=\"" + attrValue + "\"" + attrString;
        }

        if(nodeName.equals("페이지")) return tagName + attrString.replace(">", "/>");
        text += tagName + attrString;

        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item.getNodeName().equals("#text")) {
                if (StringUtils.hasText(item.getNodeValue())) {
                    text += item.getNodeValue().trim();
                } else {
                    continue;
                }
            } else {
                text += myNodeToString(item, level + 1);
            }
        }
        if(nodeName.equals("목차정보") || nodeName.equals("내용")) text += "\n";
        text += "</" + nodeName + ">";
        return text;
    }
}