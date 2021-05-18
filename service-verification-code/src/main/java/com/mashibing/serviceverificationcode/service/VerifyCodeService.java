package com.mashibing.serviceverificationcode.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.serviceverificationcode.response.VerifyCodeResponse;

/***********************
 * @Description: 验证码业务类 <BR>
 * @author: zhao.song
 * @since: 2021/5/11 7:50
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
public interface VerifyCodeService {
    /**
     * Description: 验证码生成 <BR>
     *
     * @param identify:
     * @param phoneNumber:
     * @return: {@link ResponseResult< VerifyCodeResponse>}
     * @author: zhao.song   2021/5/12 7:09
     */
    public ResponseResult<VerifyCodeResponse> generate(int identify, String phoneNumber);

    /**
     * Description: 验证码校验 <BR>
     *
     * @param identify:
     * @param phoneNumber:电话号码
     * @param code:验证码
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/12 7:09
     */
    public ResponseResult verify(int identify, String phoneNumber, String code);
}
