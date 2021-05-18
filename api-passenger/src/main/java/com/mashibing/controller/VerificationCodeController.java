package com.mashibing.controller;

import com.mashibing.request.ShortMsgRequest;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***********************
 * @Description: 验证码获取 <BR>
 * @author: zhao.song
 * @since: 2021/5/13 23:16
 * @version: 1.0
 ***********************/
@RestController
@RequestMapping("/verify-code")
@SuppressWarnings("all")
public class VerificationCodeController {
    @Autowired
    private VerificationCodeService verificationCodeService;

    @PostMapping("/send")
    public ResponseResult send(@RequestBody /*@Validated*/ ShortMsgRequest shortMsgRequset) {
        return verificationCodeService.send(shortMsgRequset.getPhoneNumber());
    }
}
