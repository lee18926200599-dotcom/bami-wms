package com.common.framework.config.jackson;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

public class DateSerializer  extends JsonSerializer<Date> {
    private String dateFormat;

    public DateSerializer(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String str = DateUtil.format(value, this.dateFormat);
        gen.writeString(str);
    }
}
