package com.enjoy.entity;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "LOGIN_INFO".
 */
@Entity
public class LoginInfo {

    @Id
    private Long id;
    private String username;
    private String userpasswd;
    private String movie_preference;
    private String music_preference;
    private String user_photo_url;
    private String nickname;
    private String sex;
    private String age;

    @Generated
    public LoginInfo() {
    }

    public LoginInfo(Long id) {
        this.id = id;
    }

    @Generated
    public LoginInfo(Long id, String username, String userpasswd, String movie_preference, String music_preference, String user_photo_url, String nickname, String sex, String age) {
        this.id = id;
        this.username = username;
        this.userpasswd = userpasswd;
        this.movie_preference = movie_preference;
        this.music_preference = music_preference;
        this.user_photo_url = user_photo_url;
        this.nickname = nickname;
        this.sex = sex;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpasswd() {
        return userpasswd;
    }

    public void setUserpasswd(String userpasswd) {
        this.userpasswd = userpasswd;
    }

    public String getMovie_preference() {
        return movie_preference;
    }

    public void setMovie_preference(String movie_preference) {
        this.movie_preference = movie_preference;
    }

    public String getMusic_preference() {
        return music_preference;
    }

    public void setMusic_preference(String music_preference) {
        this.music_preference = music_preference;
    }

    public String getUser_photo_url() {
        return user_photo_url;
    }

    public void setUser_photo_url(String user_photo_url) {
        this.user_photo_url = user_photo_url;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}
