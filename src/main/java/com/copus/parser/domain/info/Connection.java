package com.copus.parser.domain.info;

import com.copus.parser.domain.enums.ConnectionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Connection {
    @Id
    @Column(name = "connection_id")
    private Long id;

    private String connectionEnd;
    private String connectionStart;
    @Enumerated(value = EnumType.STRING)
    private ConnectionType type;

    @ManyToOne
    @JoinColumn(name = "connection_info_id")
    private ConnectionInfo connectionInfo;

    public Connection(Long id, String connectionEnd, String connectionStart, ConnectionType type, ConnectionInfo connectionInfo) {
        this.id = id;
        this.connectionEnd = connectionEnd;
        this.connectionStart = connectionStart;
        this.type = type;
        this.connectionInfo = connectionInfo;
    }
}
