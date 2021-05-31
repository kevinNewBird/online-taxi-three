package com.mashibing.internalcommon.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/***********************
 * @Description: Rest请求工具类 <BR>
 * @author: zhao.song
 * @since: 2021/5/26 9:59
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Configuration
public class RestTemplateHelper {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    /**
     * Description: 将ResponseResult解析为指定的类 <BR>
     *
     * @param result:
     * @param clazz:
     * @return: {@link T}
     * @author: zhao.song   2021/5/26 10:03
    */
    public static <T> T parse(@Nullable ResponseResult result, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(mapper.writeValueAsString(Objects.isNull(result) ? result.getData() : null), clazz);
    }

    public static <T> T parse(@Nullable String jsonStr, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonStr, clazz);
    }
}
