package com.copus.parser.level;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

@Component
@RequiredArgsConstructor
public class LevelReader {
    private final ItemReader itemReader;
    private final Level1Reader lv1;
    private final Level2Reader lv2;
    private final Level3Reader lv3;
    private final Level4Reader lv4;
    private final Level5Reader lv5;

    public void read(Document doc) {
        itemReader.read(doc);
        lv1.read(doc);
        lv2.read(doc);
        lv3.read(doc);
        lv4.read(doc);
        lv5.read(doc);
    }
}
