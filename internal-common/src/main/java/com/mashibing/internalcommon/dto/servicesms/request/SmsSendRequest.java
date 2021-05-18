package com.mashibing.internalcommon.dto.servicesms.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/***********************
 * @Description: 短信发送请求参数 <BR>
 * @author: zhao.song
 * @since: 2021/5/12 17:49
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsSendRequest {

    private List<String> receivers;

    private List<SmsTemplateDto> data;

    @Override
    public String toString() {
        return "SmsSendRequest{" +
                "receivers=" + receivers +
                ", data=" + data +
                '}';
    }
}
