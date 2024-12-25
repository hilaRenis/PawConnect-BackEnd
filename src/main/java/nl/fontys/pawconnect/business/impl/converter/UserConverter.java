package nl.fontys.pawconnect.business.impl.converter;


import nl.fontys.pawconnect.domain.User;
import nl.fontys.pawconnect.domain.UserDTO;
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
                .fullName(user.getFullName())
                .role(user.getRole())
                .avatar(user.getAvatar() != null ? ImageConverter.convert(user.getAvatar()) : null)
                .build();
    }

    public static UserDTO convertToDTO(UserEntity user){
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole())
                .avatar(user.getAvatar() != null ? ImageConverter.convert(user.getAvatar()) : null)
                .build();
    }

}
