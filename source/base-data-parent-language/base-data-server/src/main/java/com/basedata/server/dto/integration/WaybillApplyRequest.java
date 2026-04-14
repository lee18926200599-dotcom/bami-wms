package com.basedata.server.dto.integration;

import lombok.Data;

import java.io.Serializable;

@Data
public class WaybillApplyRequest implements Serializable {

    private String cp_code;
}