package com.org.permission.server.org.util;

import com.org.permission.common.dto.BaseCustInfoDto;
import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class GeneratorCustCode {

    public String getCustCode(BaseCustInfoDto baseCustInfoDto) {

        Random r = new Random();

        return String.valueOf(r.nextInt(10));

    }

}
