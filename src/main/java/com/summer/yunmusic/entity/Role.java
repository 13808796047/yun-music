package com.summer.yunmusic.entity;

import lombok.Data;

import javax.persistence.Entity;

/**
 * @author Summer
 * @since 2022/4/17 14:21
 */
@Entity
@Data
public class Role extends AbstractEntity {
    private String name;
    
    private String title;

}
