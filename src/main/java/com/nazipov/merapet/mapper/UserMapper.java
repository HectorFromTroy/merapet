package com.nazipov.merapet.mapper;

import com.nazipov.merapet.dto.UserInfo;
import com.nazipov.merapet.entities.MyUser;

public class UserMapper {

    private UserMapper() {}

    public static MyUser mapToMyUserFromUserInfo(UserInfo userInfo) {
        return MyUser.builder()
                    .setUserId(userInfo.getUserId())
                    .setUsername(userInfo.getUsername())
                    .setPassword(userInfo.getPassword())
                    .setEmail(userInfo.getEmail())
                    .setDateOfBirth(userInfo.getDateOfBirth())
                    .setGender(userInfo.getGender())
                    .build();
    }
}
