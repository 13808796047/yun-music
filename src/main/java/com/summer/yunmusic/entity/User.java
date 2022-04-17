package com.summer.yunmusic.entity;

import com.summer.yunmusic.enums.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Summer
 * @since 2022/4/17 12:51
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class User extends AbstractEntity {


    @Column(unique = true)
    private String username;

    private String nickname;
    // 关联模型
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"
            ))
    private List<Role> roles;

    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Boolean locked;
    private Boolean enabled;
    private String lastLoginIp;
    private Date lastLoginTime;

}
