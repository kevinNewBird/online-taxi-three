package com.mashibing.servicesms.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.servicesms.request.SmsSendRequest;
import com.mashibing.servicesms.service.ServiceSms;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***********************
 * @Description: 短消息发送视图层 <BR>
 * @author: zhao.song
 * @since: 2021/5/12 17:52
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@RestController
@RequestMapping("/send")
@Slf4j
public class SmsSendController {

    @Autowired
    private ServiceSms serviceSms;


    @PostMapping(value = "/sms-template")
    public ResponseResult send(@RequestBody SmsSendRequest smsTemplate) {
        log.info("/send/sms-template  request:" + smsTemplate);

        return serviceSms.sendSms(smsTemplate);
    }
}
