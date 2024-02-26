package com.desafiopitang.api.mapper;

import com.desafiopitang.api.domain.Car;
import com.desafiopitang.api.domain.User;
import com.desafiopitang.api.dto.CarDTO;
import com.desafiopitang.api.dto.UserDTO;
import com.desafiopitang.api.dto.UserMeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    //Cars
    Car toCarEntity(CarDTO dto);

    @Mapping(target = "user", ignore = true)
    CarDTO toCarDTO(Car entity);

    //User
    User toUserEntity(UserDTO dto);

    @Mapping(target = "cars", ignore = true)
    UserDTO toUserDTO(User entity);

    @Mapping(target = "cars", ignore = true)
    UserMeDTO toUserMeDTO(User entity);


}


