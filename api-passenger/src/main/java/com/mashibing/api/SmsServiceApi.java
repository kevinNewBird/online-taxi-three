package com.mashibing.api;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.servicesms.request.SmsSendRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/***********************
 * @Description: 短信服务api <BR>
 * @author: zhao.song
 * @since: 2021/5/13 23:43
 * @version: 1.0
 ***********************/
@FeignClient(name = "service-sms")
@SuppressWarnings("all")
public interface SmsServiceApi {

    @PostMapping(value = "/send/sms-template")
    public ResponseResult send(@RequestBody SmsSendRequest smsTemplate);
}
