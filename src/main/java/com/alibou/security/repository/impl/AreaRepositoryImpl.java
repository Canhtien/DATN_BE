package com.alibou.security.repository.impl;

import com.alibou.security.model.dto.AreaDTO;
import com.alibou.security.repository.AreaRepository;
//import com.viettel.etc.xlibrary.core.repositories.CommonDataBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class AreaRepositoryImpl implements AreaRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<AreaDTO> getArea(String parentCode, String areaCode) {

        StringBuilder sql = new StringBuilder();
        List<Object> arrParams = new ArrayList<>();

        sql.append("select AREA_CODE, NAME, FULL_NAME as fullName, PROVINCE, CONCAT(PROVINCE, DISTRICT) AS DISTRICT, CONCAT(PROVINCE, DISTRICT, PRECINCT) AS PRECINCT, \n");
        sql.append("DISTRICT AS districtShop, PROVINCE AS provinceShop, \n");
        sql.append("(SELECT NAME FROM AREA WHERE AREA_CODE = CONCAT(A.PROVINCE, A.DISTRICT, A.PRECINCT) and A.PRECINCT IS NOT NULL) precinctName, \n");
        sql.append("(SELECT NAME FROM AREA WHERE AREA_CODE = CONCAT(A.PROVINCE, A.DISTRICT) AND A.DISTRICT IS NOT NULL) districtName, \n");
        sql.append("(SELECT NAME FROM AREA WHERE AREA_CODE = A.PROVINCE AND A.PROVINCE IS NOT NULL) provinceName \n");
        sql.append("from AREA A \n");
        sql.append("where 1=1 \n");

        if (parentCode == null && areaCode == null) {
            //lay danh sach tinh/ thanh pho
            sql.append("and PARENT_CODE is null \n");
        } else if (areaCode == null) {
            //lay danh sach quan/huyen, phuong/xa
            sql.append("and PARENT_CODE = ? \n");
            arrParams.add(parentCode);
        } else if (parentCode == null) {
            //lay chi tiet thong tin theo areacode
            sql.append("and AREA_CODE = ? \n");
            arrParams.add(areaCode);
        }
        sql.append("order by NAME COLLATE utf8mb4_unicode_ci ASC\n");

//        return (List<AreaDTO>) getListData(sql, arrParams, null, null, AreaDTO.class);
        return jdbcTemplate.query(
                sql.toString(),
                arrParams.toArray(),
                new BeanPropertyRowMapper<>(AreaDTO.class)
        );
    }


}
