package com.mashibing.internalcommon.dto.servicesms.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/***********************
 * @Description: 短信模板 <BR>
 * @author: zhao.song
 * @since: 2021/5/12 17:38
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsTemplateDto {
    // 模板id
    private String id;

    // 模板内容
    private SmsTemplateData templateData;

    @Override
    public String toString() {
        return "SmsTemplateDto {" +
                "id='" + id + '\'' +
                ", templateData=" + templateData +
                '}';
    }
}
