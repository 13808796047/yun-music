package com.summer.yunmusic.repository;

import com.summer.yunmusic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Summer
 * @since 2022/4/17 13:38
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User getByUsername(String username);
}
