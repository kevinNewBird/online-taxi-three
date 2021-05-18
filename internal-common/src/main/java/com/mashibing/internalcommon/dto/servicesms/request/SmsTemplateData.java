package com.mashibing.internalcommon.dto.servicesms.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/***********************
 * @Description: 模板内容 <BR>
 * @author: zhao.song
 * @since: 2021/5/12 17:45
 * @version: 1.0
 ***********************/
@Data
@SuppressWarnings("all")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsTemplateData {

    private String code;

    @Override
    public String toString() {
        return " {" +
                "code='" + code + '\'' +
                '}';
    }
}
