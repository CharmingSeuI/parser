package com.copus.parser.info;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

@Component
@RequiredArgsConstructor
@Slf4j
public class InfoReader {
    private final MetaReader metaReader;
    private final BodyReader bodyReader;
    private final ConnectionReader connectionReader;
    private final AnnotationReader annotationReader;
    private final CommentaryReader commentaryReader;

    public static String nodeToString(Node node){
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "no");
            t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException te) {
            log.info("nodeToString Transformer Exception");
        }
        return sw.toString();
    }

    public void read(Document doc) {
        metaReader.read(doc);
        bodyReader.read(doc);
        connectionReader.read(doc);
        annotationReader.read(doc);
        commentaryReader.read(doc);
    }
}
