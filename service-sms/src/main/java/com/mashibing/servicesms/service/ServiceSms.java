package com.mashibing.servicesms.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.servicesms.request.SmsSendRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/***********************
 * @Description: 短消息业务类 <BR>
 * @author: zhao.song
 * @since: 2021/5/12 18:33
 * @version: 1.0
 ***********************/

@SuppressWarnings("all")
public interface ServiceSms {

    /**
     * Description: 发送短消息 <BR>
     *
     * @param smsSendRequest:
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/12 18:35
     */
    ResponseResult sendSms(SmsSendRequest smsSendRequest);
}
