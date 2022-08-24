package com.accountsservices.mappers;

import com.accountsservices.Entity.AppUser;
import com.accountsservices.dto.AppUserRequestDTO;
import com.accountsservices.dto.AppUserResponseDTO;
import com.accountsservices.dto.AppUserUpdateDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUserResponseDTO AppUserTOAppUserResponseDTO(AppUser work_order);

    AppUser AppUserRequestDTOAppUser(AppUserRequestDTO workOrderRequestDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAppUserFromDto(AppUserUpdateDTO dto, @MappingTarget AppUser entity);
}
