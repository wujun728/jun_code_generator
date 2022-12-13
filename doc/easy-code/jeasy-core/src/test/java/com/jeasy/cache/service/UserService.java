package com.jeasy.cache.service;

import com.jeasy.common.str.StrKit;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class UserService {

    @Cacheable(key = "'user'+#id", value = {"default"})
    public User getUserById(Long id) {
        return this.getUserByIdWithJdk(id);
    }

    @Cacheable(key = "'username'+#id", value = {"day"})
    public String getUserNameByIdWithFastJson(Long id) {
        return this.getUserNameByIdWithJdk(id);
    }

    @Cacheable(key = "'user'+#id", value = {"day"})
    public User getUserByIdWithFastjson(Long id) {
        return this.getUserById(id);
    }

    @Cacheable(key = "'username'+#id", value = {"default"})
    public String getUserNameByIdWithJdk(Long id) {
        System.out.println("根据id获取用户名，id：" + id);
        return "billy--" + id;
    }

    @Cacheable(key = "'user'+#id", value = {"default"})
    public User getUserByIdWithJdk(Long id) {
        System.out.println("根据id获取用户，id：" + id);
        User user = new User();
        if (id == 1l) {
            user.setId(1l);
            user.setAge(18);
            user.setName("倪billy");
        } else if (id == 2l) {
            user.setId(2l);
            user.setAge(20);
            user.setName("高MM");
        } else {
            user.setId(id);
            user.setAge(20);
            user.setName("高MM # " + id);
        }
        return user;
    }

    public static class User implements Serializable {

        /** */
        private static final long serialVersionUID = -4260332486904930483L;
        private Long id;
        private String name;
        private int age;
        private String nickname = StrKit.S_EMPTY;

        private User partner;

        public User() {

        }

        /**
         * get id
         *
         * @return
         */
        public Long getId() {
            return id;
        }

        /**
         * set id
         *
         * @param id
         */
        public void setId(Long id) {
            this.id = id;
        }

        /**
         * get name
         *
         * @return
         */
        public String getName() {
            return name;
        }

        /**
         * set name
         *
         * @param name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * get age
         *
         * @return
         */
        public int getAge() {
            return age;
        }

        /**
         * set age
         *
         * @param age
         */
        public void setAge(int age) {
            this.age = age;
        }

        /**
         * get nickname
         *
         * @return
         */
        public String getNickname() {
            return nickname;
        }

        /**
         * set nickname
         *
         * @param nickname
         */
        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        /**
         * get partner
         *
         * @return
         */
        public User getPartner() {
            return partner;
        }

        /**
         * set partner
         *
         * @param partner
         */
        public void setPartner(User partner) {
            this.partner = partner;
        }
    }
}
