package com.leyou.order.pojo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author XuHao
 * @Title: Long2StringSerizlizer
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/2617:30
 */
public class Long2StringSerizlizer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}
