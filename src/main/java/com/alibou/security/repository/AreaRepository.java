package com.alibou.security.repository;

import com.alibou.security.model.dto.AreaDTO;

import java.util.List;

public interface AreaRepository {

    List<AreaDTO> getArea(String parentCode, String areaCode);
}
