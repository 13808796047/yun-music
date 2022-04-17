package com.summer.yunmusic.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author Summer
 * @since 2022/4/17 13:03
 */
@MappedSuperclass
@Data
public abstract class AbstractEntity {
    @Id
    // 主键生成策略(ksuid)
    @GeneratedValue(generator = "ksuid")
    @GenericGenerator(name = "ksuid",strategy = "com.summer.yunmusic.utils.KsuidIdentifierGenerator")
    private String id;
    @CreationTimestamp
    private Date createdTime;
    @UpdateTimestamp
    private Date updatedTime;
}
