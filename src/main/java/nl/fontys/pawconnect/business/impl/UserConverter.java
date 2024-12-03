package nl.fontys.pawconnect.business.impl;


import nl.fontys.pawconnect.domain.User;
import nl.fontys.pawconnect.persistence.entity.UserEntity;

public final class UserConverter {
    private UserConverter(){
    }

    public static User convert(UserEntity user){
        return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .role(user.getRole())
                .build();
    }

}
