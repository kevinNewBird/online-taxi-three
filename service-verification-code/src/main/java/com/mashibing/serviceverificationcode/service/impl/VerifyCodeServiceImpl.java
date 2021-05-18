package com.mashibing.serviceverificationcode.service.impl;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.constant.RedisKeyExpirationConstant;
import com.mashibing.internalcommon.constant.RedisKeyPrefixConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.serviceverificationcode.response.VerifyCodeResponse;
import com.mashibing.serviceverificationcode.pojo.VerifyCodeLease;
import com.mashibing.serviceverificationcode.service.VerifyCodeService;
import io.vavr.API;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static io.vavr.API.$;
import static io.vavr.API.Case;

/***********************
 * @Description: 验证码业务类<BR>
 * @author: zhao.song
 * @since: 2021/5/11 8:17
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * Description: 生成验证码 <BR>
     *
     * @param identity:
     * @param phoneNumber:
     * @return: {@link ResponseResult< VerifyCodeResponse>}
     * @author: zhao.song   2021/5/11 8:18
     */
    @Override
    public ResponseResult<VerifyCodeResponse> generate(int identity, String phoneNumber) {
        // 0.三档校验。业务方法控制，不能无限制发送短信。
        // redis 1分钟发了三次，限制你5分钟不能发；1小时发了10次，限制24小时不能发
        // TIP: 因为是用户短信服务的验证码,所以在次数上加上限制(如果会被用到其它部门,可以放开这块)
        String errMsg = checkSendCodeTimeLimit(phoneNumber);
        if (!StringUtils.isEmpty(errMsg)) {
            return ResponseResult.fail(500, errMsg);
        }

        String cachePhoneKey = generateKeyPrefixByIdentity(identity).concat(phoneNumber);


        // 1.生成验证码
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));
        VerifyCodeResponse vcr = VerifyCodeResponse.builder().code(code).build();

        // 2.验证码放入缓存,过期时间为60秒; key: prefix_phoneNumber
        redisTemplate.opsForValue().set(cachePhoneKey, code
                , RedisKeyExpirationConstant.CODE_EXPIRE_TIME_SIXTY_SEC.getDuration()
                , RedisKeyExpirationConstant.CODE_EXPIRE_TIME_SIXTY_SEC.getTimeUnit());
        return ResponseResult.success(vcr);
    }


    /**
     * Description: 校验验证码 <BR>
     *
     * @param identity:
     * @param phoneNumber:
     * @param code:
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/12 6:58
     */
    public ResponseResult verify(int identity, String phoneNumber, String code) {
        // 三档验证
        String errMsg = checkSendCodeTimeLimitAbsent(phoneNumber);
        if (!StringUtils.isEmpty(errMsg)) {
            return ResponseResult.fail(500, errMsg);
        }

        String cachePhoneKey = generateKeyPrefixByIdentity(identity).concat(phoneNumber);
        String redisCode = (String) redisTemplate.opsForValue().get(cachePhoneKey);
        if (StringUtils.isNotBlank(code)
                && StringUtils.isNotBlank(redisCode)
                && redisCode.trim().equals(code.trim())) {
            return ResponseResult.success("");
        } else {
            return ResponseResult.fail(CommonStatusEnum.VERIFY_CODE_ERROR.getCode()
                    , CommonStatusEnum.VERIFY_CODE_ERROR.getValue());
        }
    }

    /**
     * Description: 限制发送频率 <BR>
     *
     * @param phoneNumber:
     * @return:
     * @author: zhao.song   2021/5/11 17:02
     */
    public String checkSendCodeTimeLimit(String phoneNumber) {
        // 0. 判断是否超出限制
        String isExceedLimitMsg = checkSendCodeTimeLimitAbsent(phoneNumber);
        if (StringUtils.isNotBlank(isExceedLimitMsg)) {
            return isExceedLimitMsg;
        }

        //TODO 多节点: 考虑增加分布式锁,确保值的修改的原子性
        String cacheFreqLimitPhoneKey = RedisKeyPrefixConstant.SEND_LIMIT_FREQ_CODE_KEY_PREFIX.concat(phoneNumber);
        VerifyCodeLease lease = (VerifyCodeLease) redisTemplate.opsForValue().get(cacheFreqLimitPhoneKey);
        // TIP: redis key的过期时间的说明: 过期时间只能在第一获取验证码时设置,设置为 1小时限制;
        /// 另外一种设置情况就是在
        // 1.首次获取: 验证码获取的租约为null
        long lastObtainTime = System.currentTimeMillis();

        if (Objects.isNull(lease)) {
            lease = VerifyCodeLease.builder().repeatNums(1).firstObtainTime(System.currentTimeMillis())
                    .oneMinLimit(false)
                    .oneHourLimit(false)
                    .build();
            redisTemplate.opsForValue().set(cacheFreqLimitPhoneKey, lease
                    , RedisKeyExpirationConstant.CODE_EXPIRE_TIME_TEN_MIN.getDuration()
                    , RedisKeyExpirationConstant.CODE_EXPIRE_TIME_TEN_MIN.getTimeUnit());
            return null;
            // 2.非首次获取: 1分钟发了三次，限制5分钟不能发；10分钟发了10次，限制24小时不能发
        } else {

            if (lease.getRepeatNums() == 3
                    && (lastObtainTime - lease.getFirstObtainTime()) / (1000 * 60) <= 1) {
                lease.setOneMinLimit(true);
                redisTemplate.opsForValue().set(cacheFreqLimitPhoneKey, lease
                        , RedisKeyExpirationConstant.CODE_EXPIRE_TIME_TEN_MIN.getDuration()
                        , RedisKeyExpirationConstant.CODE_EXPIRE_TIME_TEN_MIN.getTimeUnit());
                return "1分钟发了三次，限制5分钟不能发";
            }

            if (lease.getRepeatNums() == 10
                    && (lastObtainTime - lease.getFirstObtainTime()) / (1000 * 60 * 60) <= 10) {
                lease.setOneHourLimit(true);
                redisTemplate.opsForValue().set(cacheFreqLimitPhoneKey, lease
                        , RedisKeyExpirationConstant.CODE_EXPIRE_TIME_ONE_HOUR.getDuration()
                        , RedisKeyExpirationConstant.CODE_EXPIRE_TIME_ONE_HOUR.getTimeUnit());
                return "10分钟发了10次，限制24小时不能发";
            }
            // 当用户不在限制发送条件下时,才能更新
            if (!lease.isOneMinLimit() && !lease.isOneHourLimit()) {
                lease.resetRepeatNums();
            }

            redisTemplate.opsForValue().set(cacheFreqLimitPhoneKey, lease
                    , RedisKeyExpirationConstant.CODE_EXPIRE_TIME_TEN_MIN.getDuration()
                    , RedisKeyExpirationConstant.CODE_EXPIRE_TIME_TEN_MIN.getTimeUnit());

        }
        return null;
    }

    private String checkSendCodeTimeLimitAbsent(String phoneNumber) {
        String cacheFreqLimitPhoneKey = RedisKeyPrefixConstant.SEND_LIMIT_FREQ_CODE_KEY_PREFIX.concat(phoneNumber);
        VerifyCodeLease lease = (VerifyCodeLease) redisTemplate.opsForValue().get(cacheFreqLimitPhoneKey);
        if (Objects.isNull(lease)) {
            return null;
        }
        long lastObtainTime = System.currentTimeMillis();
        if (lease.isOneMinLimit()
                && ((lastObtainTime - lease.getFirstObtainTime()) / (1000 * 60) <= 5)) {
            return "1分钟发了三次，限制5分钟不能发";
        }
        if (lease.isOneHourLimit()
                && ((lastObtainTime - lease.getFirstObtainTime()) / (1000 * 60 * 60) <= 24)) {
            return "1小时发了10次，限制24小时不能发";
        }
        return null;
    }

    /**
     * Description: 根据身份类型生成对应的缓存key <BR>
     *
     * @param identity:
     * @return: {@link String}
     * @author: zhao.song   2021/5/12 15:36
     */
    private String generateKeyPrefixByIdentity(int identity) {
        return API.Match(identity).of(
                Case($(IdentityConstant.PASSENGER_IDENT), RedisKeyPrefixConstant.PASSENGER_LOGIN_CODE_KEY_PREFIX),
                Case($(IdentityConstant.DRIVER_IDENT), RedisKeyPrefixConstant.DRIVER_LOGIN_CODE_KEY_PREFIX),
                Case($(), ""));
    }
}
