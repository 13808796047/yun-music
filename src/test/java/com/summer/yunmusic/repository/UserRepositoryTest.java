package com.summer.yunmusic.repository;

import com.summer.yunmusic.entity.User;
import com.summer.yunmusic.enums.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author Summer
 * @since 2022/4/17 13:42
 */
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void findByUsername() {
        User user = new User();
        user.setUsername("张三");
        user.setNickname("xxx");
        user.setEnabled(true);
        user.setLocked(false);
        user.setPassword("123456");
        user.setGender(Gender.MALE);
        user.setLastLoginIp("127.0.0.1");
        user.setLastLoginTime(new Date());
        User saveUser = userRepository.save(user);
        System.out.println(saveUser.toString());
        System.out.println(userRepository.getByUsername("张三").toString());
    }
}