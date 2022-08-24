package com.copus.parser.level;

import com.copus.parser.domain.info.AnnotationInfo;
import com.copus.parser.domain.info.CommentaryInfo;
import com.copus.parser.domain.info.ConnectionInfo;
import com.copus.parser.domain.info.body.BodyInfo;
import com.copus.parser.domain.info.meta.MetaInfo;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface InfoIdRepository {
    //Key : LevelId, Value : InfoId
    Map<String, Long> metaInfoId = new HashMap<>();
    Map<String, Long> commentaryInfoId = new HashMap<>();
    Map<String, Long> bodyInfoId = new HashMap<>();
    Map<String, Long> connectionInfoId = new HashMap<>();
    Map<String, Long> annotationInfoId = new HashMap<>();

    static MetaInfo getMetaInfo(String levelId, EntityManager em) {
        return Optional.ofNullable(InfoIdRepository.metaInfoId.get(levelId))
                .map(n -> em.find(MetaInfo.class, n)).orElse(null);
    }

    static CommentaryInfo getCommentaryInfo(String levelId, EntityManager em) {
        return Optional.ofNullable(InfoIdRepository.commentaryInfoId.get(levelId))
                .map(n -> em.find(CommentaryInfo.class, n)).orElse(null);
    }

    static BodyInfo getBodyInfo(String levelId, EntityManager em) {
        return Optional.ofNullable(InfoIdRepository.bodyInfoId.get(levelId))
                .map(n -> em.find(BodyInfo.class, n)).orElse(null);
    }

    static ConnectionInfo getConnectionInfo(String levelId, EntityManager em) {
        return Optional.ofNullable(InfoIdRepository.connectionInfoId.get(levelId))
                .map(n -> em.find(ConnectionInfo.class, n)).orElse(null);
    }

    static AnnotationInfo getAnnotationInfo(String levelId, EntityManager em) {
        return Optional.ofNullable(InfoIdRepository.annotationInfoId.get(levelId))
                .map(n -> em.find(AnnotationInfo.class, n)).orElse(null);
    }
}
