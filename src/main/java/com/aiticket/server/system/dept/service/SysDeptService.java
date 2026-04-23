package com.aiticket.server.system.dept.service;

import com.aiticket.server.system.dept.dto.DeptCreateRequest;
import com.aiticket.server.system.dept.dto.DeptQueryRequest;
import com.aiticket.server.system.dept.dto.DeptUpdateRequest;
import com.aiticket.server.system.dept.entity.SysDept;
import com.aiticket.server.system.dept.vo.DeptVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysDeptService extends IService<SysDept> {

    List<DeptVO> listDepts(DeptQueryRequest request);

    Long createDept(DeptCreateRequest request);

    void updateDept(Long id, DeptUpdateRequest request);

    DeptVO getDept(Long id);

    void deleteDept(Long id);
}
