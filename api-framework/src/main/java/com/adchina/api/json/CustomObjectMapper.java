package com.adchina.api.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import javax.annotation.PostConstruct;

/**
 * 定制 Jackson 的 ObjectMapper
 *
 * @author huangyong
 * @since 1.0.0
 */
public class CustomObjectMapper extends ObjectMapper {

    private boolean camelCaseToLowerCaseWithUnderscores = false;

    public void setCamelCaseToLowerCaseWithUnderscores(boolean camelCaseToLowerCaseWithUnderscores) {
        this.camelCaseToLowerCaseWithUnderscores = camelCaseToLowerCaseWithUnderscores;
    }

    @PostConstruct
    public void init() {
        // 序列化时排除值为空属性
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 序列化时进行缩进输出
        configure(SerializationFeature.INDENT_OUTPUT, true);
        // 序列化时将驼峰转为下划线
        if (camelCaseToLowerCaseWithUnderscores) {
            setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        }
    }
}
