package com.org.permission.common.org.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class RegistStaffDto extends StaffInfoDto implements Serializable{
    private static final long serialVersionUID = 1L;
    private String initialPassword;

    private String failMsg;

    @Override
    public String toString() {
        return "RegistStaffDto{" +
                ", initialPassword='" + initialPassword + '\'' +
                '}';
    }
}
