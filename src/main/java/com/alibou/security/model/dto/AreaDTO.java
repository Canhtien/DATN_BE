package com.alibou.security.model.dto;

import com.alibou.security.entity.Areas;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AreaDTO    {

    String area_code;

    String province;

    String name;

    String district;

    String precinct;

    String provinceName;

    String districtName;

    String precinctName;

    String fullName;

    Integer startrecord;

    Boolean getAll;

    Integer pagesize;

    String districtShop;

    String provinceShop;

    public AreaDTO(Areas areaEntity) {
        if (areaEntity == null) {
            return;
        }
        area_code = areaEntity.getAreaCode();
        province = areaEntity.getProvince();
        name = areaEntity.getName();
        district = areaEntity.getDistrict();
        precinct = areaEntity.getPrecinct();

    }
}