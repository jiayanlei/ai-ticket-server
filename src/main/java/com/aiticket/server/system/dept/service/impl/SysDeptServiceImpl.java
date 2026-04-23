package com.aiticket.server.system.dept.service.impl;

import com.aiticket.server.common.constant.CommonConstants;
import com.aiticket.server.common.exception.BusinessException;
import com.aiticket.server.common.exception.ErrorCode;
import com.aiticket.server.system.dept.convert.SysDeptConvert;
import com.aiticket.server.system.dept.dto.DeptCreateRequest;
import com.aiticket.server.system.dept.dto.DeptQueryRequest;
import com.aiticket.server.system.dept.dto.DeptUpdateRequest;
import com.aiticket.server.system.dept.entity.SysDept;
import com.aiticket.server.system.dept.mapper.SysDeptMapper;
import com.aiticket.server.system.dept.service.SysDeptService;
import com.aiticket.server.system.dept.vo.DeptVO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysDeptConvert deptConvert;

    @Override
    public List<DeptVO> listDepts(DeptQueryRequest request) {
        return list(Wrappers.<SysDept>lambdaQuery()
                .like(StringUtils.hasText(request.getDeptName()), SysDept::getDeptName, request.getDeptName())
                .like(StringUtils.hasText(request.getDeptCode()), SysDept::getDeptCode, request.getDeptCode())
                .eq(StringUtils.hasText(request.getStatus()), SysDept::getStatus, request.getStatus())
                .orderByAsc(SysDept::getSortOrder)
                .orderByAsc(SysDept::getId))
                .stream()
                .map(deptConvert::toVO)
                .toList();
    }

    @Override
    public Long createDept(DeptCreateRequest request) {
        long exists = count(Wrappers.<SysDept>lambdaQuery().eq(SysDept::getDeptCode, request.getDeptCode()));
        if (exists > 0) {
            throw new BusinessException("部门编码已存在");
        }
        SysDept dept = deptConvert.toEntity(request);
        if (dept.getParentId() == null) {
            dept.setParentId(0L);
        }
        if (!StringUtils.hasText(dept.getStatus())) {
            dept.setStatus(CommonConstants.STATUS_ENABLED);
        }
        save(dept);
        return dept.getId();
    }

    @Override
    public void updateDept(Long id, DeptUpdateRequest request) {
        SysDept dept = getById(id);
        if (dept == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        deptConvert.updateEntity(request, dept);
        updateById(dept);
    }

    @Override
    public DeptVO getDept(Long id) {
        SysDept dept = getById(id);
        if (dept == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        return deptConvert.toVO(dept);
    }

    @Override
    public void deleteDept(Long id) {
        if (!removeById(id)) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
    }
}
