package com.alibou.security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AREA")
public class Areas{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "AREA_CODE")
    String areaCode;

    @Column(name = "PARENT_CODE")
    String parentCode;

    @Column(name = "AREA_GROUP")
    String areaGroup;

    @Column(name = "PROVINCE")
    String province;

    @Column(name = "DISTRICT")
    String district;

    @Column(name = "PRECINCT")
    String precinct;

    @Column(name = "STREET_BLOCK")
    String streetBlock;

    @Column(name = "NAME")
    String name;

    @Column(name = "FULL_NAME")
    String fullName;

    @Column(name = "CENTER")
    String center;

    @Column(name = "PSTN_CODE")
    String pstnCode;

    @Column(name = "PROVINCE_CODE")
    String provinceCode;

    @Column(name = "STATUS")
    String status;

    @Column(name = "CREATE_USER")
    String createUser;

    @Column(name = "UPDATE_USER")
    String updateUser;

    @Column(name = "CREATE_DATETIME")
    Date createDatetime;

    @Column(name = "UPDATE_DATETIME")
    Date updateDatetime;

    @Column(name = "REGION_ID")
    Integer regionId;

    @Column(name = "VT_MAP_CODE")
    String vtMapCode;

    @Column(name = "SQUARE")
    Integer square;

    @Column(name = "POPULATION")
    Integer population;

    @Column(name = "HOUSEHOLDS")
    Integer households;

    @Column(name = "AREA_TYPE")
    String areaType;

    @Column(name = "VN_CODE")
    String vnCode;

    @Column(name = "VN_NAME")
    String vnName;
}