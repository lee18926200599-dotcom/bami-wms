package com.org.permission.server.org.util;

import com.org.permission.common.dto.BaseCustSubDto;
import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class GeneratorCustSubCode {

    public String getCustSubCode(BaseCustSubDto baseCustSubDto) {

        Random r = new Random();

        return String.valueOf(r.nextInt(10));

    }

}
