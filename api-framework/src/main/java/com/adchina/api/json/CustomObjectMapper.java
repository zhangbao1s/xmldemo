package com.adchina.api.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 定制 Jackson 的 ObjectMapper
 *
 * @author huangyong
 * @since 1.0.0
 */
public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        // 序列化时排除值为空属性
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 序列化时进行缩进输出
        configure(SerializationFeature.INDENT_OUTPUT, true);
        // 反序列化时将下划线转换为驼峰
        setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
    }
}
