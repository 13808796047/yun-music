package com.summer.yunmusic.repository;

import com.summer.yunmusic.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Summer
 * @since 2022/4/17 13:38
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User getByUsername(String username);

    Optional<User> findByUsername(String username);

    User getById(String id);

    Page<User> findAll(Pageable pageable);

}
