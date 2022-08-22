package com.copus.parser.domain.info;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 연계 정보
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConnectionInfo {
    @Id
    @Column(name = "connection_info_id")
    private Long id;

    @OneToMany(mappedBy = "connectionInfo")
    private List<Connection> connections = new ArrayList<>();

    public ConnectionInfo(Long id) {
        this.id = id;
    }
}