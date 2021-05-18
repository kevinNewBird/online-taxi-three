package com.mashibing.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mashibing.api.CodeServiceApi;
import com.mashibing.api.SmsServiceApi;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.servicesms.request.SmsSendRequest;
import com.mashibing.internalcommon.dto.servicesms.request.SmsTemplateData;
import com.mashibing.internalcommon.dto.servicesms.request.SmsTemplateDto;
import com.mashibing.internalcommon.dto.serviceverificationcode.request.VerifyCodeRequest;
import com.mashibing.internalcommon.dto.serviceverificationcode.response.VerifyCodeResponse;
import com.mashibing.service.VerificationCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/***********************
 * @Description: 验证码服务类 <BR>
 * @author: zhao.song
 * @since: 2021/5/14 7:41
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Service
@Slf4j
public class VerificationCodeServiceImpl implements VerificationCodeService {
    @Autowired
    private CodeServiceApi codeServiceApi;

    @Autowired
    private SmsServiceApi smsServiceApi;


    /**
     * Description: 发送验证码给手机 <BR>
     *
     * @param phoneNumber :
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/13 23:55
     */
    @Override
    public ResponseResult send(String phoneNumber) {
        // 1.调用远程服务生成验证码
        ResponseResult codeResponse = codeServiceApi.generate(IdentityConstant.PASSENGER_IDENT, phoneNumber);

        VerifyCodeResponse verifyCodeResponse = null;
        if (codeResponse.getCode() == CommonStatusEnum.SUCCESS.getCode()) {
            verifyCodeResponse = JSON.parseObject(JSON.toJSONString(codeResponse.getData()), VerifyCodeResponse.class);
        } else {
            return ResponseResult.fail("获取验证码失败!");
        }
        String code = verifyCodeResponse.getCode();

        //2.发送短信验证码
        ResponseResult result = smsServiceApi.send(buildSmsSendRequest(phoneNumber, code));
        if (result.getCode() != CommonStatusEnum.SUCCESS.getCode()) {
            return ResponseResult.fail("发送短信失败");
        }
        log.info(String.format("用户:%s ,发送短信验证码:%s", phoneNumber, code));
        return ResponseResult.success("");
    }

    private SmsSendRequest buildSmsSendRequest(String phoneNumber, String code) {
        List<String> receivers = new ArrayList<>();
        List<SmsTemplateDto> templateData = new ArrayList<>();

        // 接收者
        receivers.add(phoneNumber);
        // 消息模板数据
        templateData.add(SmsTemplateDto.builder()
                .id("SMS_144145499")
                .templateData(SmsTemplateData.builder().code(code).build())
                .build());
        return SmsSendRequest.builder().receivers(receivers).data(templateData).build();
    }

    /**
     * Description: 校验验证码(在用户输入验证码做登录时做校验) <BR>
     *
     * @param phoneNumber :
     * @param code        :
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/13 23:55
     */
    @Override
    public ResponseResult verifyCode(String phoneNumber, String code) {
        VerifyCodeRequest codeRequest = VerifyCodeRequest.builder()
                .identify(IdentityConstant.PASSENGER_IDENT)
                .phoneNumber(phoneNumber)
                .code(code)
                .build();
        return codeServiceApi.verify(codeRequest);
    }
}
