package com.activedge.interview.exercise.solution.exercise2.entity;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Stock {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "current_price", nullable = false)
    private BigDecimal currentPrice;

    @CreatedDate
    @Column(name = "create_date")
    private Timestamp  createDate = Timestamp.from(Instant.now());

    @LastModifiedDate
    @Column(name = "last_update")
    private Timestamp lastUpdate;
}
