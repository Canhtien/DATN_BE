package com.alibou.security.service.Impl;

import com.alibou.security.repository.AreaRepository;
import com.alibou.security.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaServiceImpl implements AreaService {


    @Autowired
    private AreaRepository areaRepository;

    @Override
    public Object getArea(String parentCode, String areaCode) {
        return areaRepository.getArea(parentCode, areaCode);
    }
}
