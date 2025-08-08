package com.anamariafelix.ms_ticket_manager.mapper;

import com.anamariafelix.ms_ticket_manager.dto.UserCreateDTO;
import com.anamariafelix.ms_ticket_manager.dto.UserResponseDTO;
import com.anamariafelix.ms_ticket_manager.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static User toUser(UserCreateDTO userCreateDTO){

        return new ModelMapper().map(userCreateDTO, User.class);
    }

    public static UserResponseDTO toUserDTO(User user){

        return new ModelMapper().map(user, UserResponseDTO.class);
    }
}
