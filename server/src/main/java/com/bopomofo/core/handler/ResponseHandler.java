package com.bopomofo.core.handler;

import com.bopomofo.core.entity.UnifyResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * @author sunke
 * @date 2021/4/10 10:32
 */
@Slf4j
@RestControllerAdvice(basePackages = {"com.bopomofo"})
public class ResponseHandler implements ResponseBodyAdvice<Object> {
    /**
     * 重写方法，判断返回类型是否为指定的类型，如果不是，则需要统一处理。
     *
     * @param returnType    返回类型
     * @param converterType HttpMessageConverter类型
     * @return 如果返回类型不等于UnifyResponse.class，则返回true；否则返回false
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getParameterType().equals(UnifyResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> converterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(new UnifyResponse<>(body));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("错误");
            }
        }
        // 如果mediaType不包含MediaType.APPLICATION_JSON，则直接返回body
        if (!mediaType.includes(MediaType.APPLICATION_JSON)) {
            return body;
        }
        // 如果返回类型是UnifyResponse，则直接返回（防御式编程，防止supports拦截失败）
        if (body instanceof UnifyResponse) {
            return body;
        }
        return new UnifyResponse<>(body);
    }
}
