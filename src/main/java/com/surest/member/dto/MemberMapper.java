package com.surest.member.dto;

import com.surest.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberDTO toDTO(Member member);

    @Mapping(target = "version", ignore = true)
    Member toEntity(MemberDTO memberDTO);
}
