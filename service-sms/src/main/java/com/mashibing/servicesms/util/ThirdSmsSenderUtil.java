package com.mashibing.servicesms.util;

import com.mashibing.servicesms.constant.ThirdSmsStatusEnum;

/***********************
 * @Description: 第三方 短信发送 工具类<BR>
 * @author: zhao.song
 * @since: 2021/5/13 11:24
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
public final class ThirdSmsSenderUtil {

    public static ThirdSmsStatusEnum sendMsg(String phoneNumber, String templateContent) {
        /**
         * TODO 供应商 发 短信
         */

        return ThirdSmsStatusEnum.SEND_SUCCESS;
    }
}
