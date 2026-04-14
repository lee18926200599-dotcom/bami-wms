package com.org.permission.server.org.util;

import com.org.permission.common.dto.BaseOrganizationDto;
import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class GeneratorOrgCode {

    public String getOrgCode(BaseOrganizationDto baseOrganizationDto) {

        Random r = new Random();

        return String.valueOf(r.nextInt(10));

    }

}
