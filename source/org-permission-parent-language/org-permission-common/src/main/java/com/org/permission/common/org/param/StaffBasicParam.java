package com.org.permission.common.org.param;

import lombok.Data;

import java.util.List;

/**
 * update
 */
@Data
public class StaffBasicParam {
    private List<String>phones;

    private Long buId;

    private List<Long> ids;

    private List<String> codes;

    private List<String> names;
}
