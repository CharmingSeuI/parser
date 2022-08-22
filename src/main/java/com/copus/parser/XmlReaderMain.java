package com.copus.parser;

import com.copus.parser.info.InfoReader;
import com.copus.parser.level.LevelReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class XmlReaderMain {
    private final LevelReader lr;
    private final InfoReader ir;

    public void read(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        Document doc = builder.parse(fileName);

        ir.read(doc);
        lr.read(doc);
    }
}
