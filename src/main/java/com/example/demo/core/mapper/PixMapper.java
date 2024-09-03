package com.example.demo.core.mapper;

import com.example.demo.core.domain.entity.PixChave;
import com.example.demo.core.domain.request.PixChaveRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PixMapper {

    PixChave toEntity(PixChaveRequestDTO request);

}
