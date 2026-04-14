package com.org.permission.server.permission.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class GroupParamValue implements Serializable {
	private static final long serialVersionUID = 4179298111768776892L;
	private String name;
	private String select;

}
