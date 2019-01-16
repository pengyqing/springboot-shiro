package com.pyq;

import com.pyq.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class SpringbootShiroApplicationTests {

    @Autowired
    StringEncryptor encryptor;

    @Test
    public void getPass() {
//        String url = encryptor.encrypt("jdbc:mysql://47.97.192.116:3306/sell?characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2b8");
//        String name = encryptor.encrypt("root");
//        String password = encryptor.encrypt("123456");
//        System.out.println(url+"----------------");
//        System.out.println(name+"----------------");
//        System.out.println(password+"----------------");

            User user = new User();
            user.setUsername("user2");
            user.setPassword("111");
            encryptPassword(user);
            System.out.println(user);

    }

    public static void encryptPassword(User user) {
        String newPassword = new SimpleHash("md5", user.getPassword(),  ByteSource.Util.bytes(user.getUsername()), 2).toHex();
        user.setPassword(newPassword);

    }

}
