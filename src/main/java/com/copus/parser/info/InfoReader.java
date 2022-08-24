package com.copus.parser.info;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

@Component
@RequiredArgsConstructor
@Slf4j
public class InfoReader {
    private final MetaReader metaReader;
    private final BodyReader bodyReader;
    private final ConnectionReader connectionReader;
    private final AnnotationReader annotationReader;
    private final CommentaryReader commentaryReader;

//    public static String nodeToString(Node node) {
//        StringWriter sw = new StringWriter();
//        try {
//            Transformer t = TransformerFactory.newInstance().newTransformer();
//            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//            t.setOutputProperty(OutputKeys.INDENT, "yes");
//            t.transform(new DOMSource(node), new StreamResult(sw));
//        } catch (TransformerException te) {
//            log.info("nodeToString Transformer Exception");
//        }
//        return sw.toString();
//    }

    public void read(Document doc) {
        metaReader.read(doc);
        bodyReader.read(doc);
        connectionReader.read(doc);
        annotationReader.read(doc);
        commentaryReader.read(doc);
    }
}
