package com.mashibing.api;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.serviceverificationcode.request.VerifyCodeRequest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/***********************
 * @Description: 验证码api <BR>
 * @author: zhao.song
 * @since: 2021/5/13 23:39
 * @version: 1.0
 ***********************/
@FeignClient(name = "service-verification-code")
@SuppressWarnings("all")
public interface CodeServiceApi {

    @GetMapping("/verify-code/generate/{identify}/{phoneNumber}")
    public ResponseResult generate(@PathVariable("identify") int identify
            , @PathVariable("phoneNumber") String phoneNumber);


    @PostMapping("/verify-code/verify")
    public ResponseResult verify(@RequestBody VerifyCodeRequest request);
}
