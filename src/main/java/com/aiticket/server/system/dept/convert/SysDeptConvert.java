package com.aiticket.server.system.dept.convert;

import com.aiticket.server.system.dept.dto.DeptCreateRequest;
import com.aiticket.server.system.dept.dto.DeptUpdateRequest;
import com.aiticket.server.system.dept.entity.SysDept;
import com.aiticket.server.system.dept.vo.DeptVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SysDeptConvert {

    SysDept toEntity(DeptCreateRequest request);

    void updateEntity(DeptUpdateRequest request, @MappingTarget SysDept entity);

    DeptVO toVO(SysDept entity);
}
