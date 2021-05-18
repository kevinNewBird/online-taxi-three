package com.mashibing.serviceverificationcode.controller;

import com.alibaba.fastjson.JSON;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.serviceverificationcode.request.VerifyCodeRequest;
import com.mashibing.serviceverificationcode.service.VerifyCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***********************
 * @Description: 验证码视图层 <BR>
 * @author: zhao.song
 * @since: 2021/5/11 7:30
 * @version: 1.0
 ***********************/
@RestController
@RequestMapping("/verify-code")
@Slf4j
@SuppressWarnings("all")
public class VerifyCodeController {

    @Autowired
    private VerifyCodeService verifyCodeService;

    @GetMapping("/generate/{identify}/{phoneNumber}")
    public ResponseResult generate(@PathVariable("identify") int identify
            , @PathVariable("phoneNumber") String phoneNumber) {
        return verifyCodeService.generate(identify, phoneNumber);
    }

    /**
     * 这种写法: 通过feign调用时, code获取为空
     */
//    @GetMapping("/verify/{identify}/{phoneNumber}")
//    public ResponseResult verify(@PathVariable("identify") int identify
//            , @PathVariable("phoneNumber") String phoneNumber, String code) {
//        return verifyCodeService.verify(identify, phoneNumber, code);
//    }

    @PostMapping("/verify")
    public ResponseResult verify(@RequestBody VerifyCodeRequest request) {
        log.info("/verify-code/verify  request:"+ JSON.toJSONString(request));
        //获取手机号和验证码
        String phoneNumber = request.getPhoneNumber();
        int identity = request.getIdentify();
        String code = request.getCode();

        return verifyCodeService.verify(identity, phoneNumber, code);

    }
}
