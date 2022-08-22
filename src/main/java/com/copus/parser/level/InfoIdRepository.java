package com.copus.parser.level;

import java.util.HashMap;
import java.util.Map;

public interface InfoIdRepository {
    //Key : LevelId, Value : InfoId
    public static Map<String, Long> metaInfoId = new HashMap<>();
    public static Map<String, Long> commentaryInfoId = new HashMap<>();
    public static Map<String, Long> bodyInfoId = new HashMap<>();
    public static Map<String, Long> connectionInfoId = new HashMap<>();
    public static Map<String, Long> annotationInfoId = new HashMap<>();
}
