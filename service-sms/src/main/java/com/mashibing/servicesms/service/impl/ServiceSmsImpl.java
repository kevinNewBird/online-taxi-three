package com.mashibing.servicesms.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.servicesms.request.SmsSendRequest;
import com.mashibing.internalcommon.dto.servicesms.request.SmsTemplateData;
import com.mashibing.internalcommon.dto.servicesms.request.SmsTemplateDto;
import com.mashibing.servicesms.constant.ThirdSmsStatusEnum;
import com.mashibing.servicesms.dao.ServiceSmsRecordDao;
import com.mashibing.servicesms.dao.ServiceSmsTemplateDao;
import com.mashibing.servicesms.entity.ServiceSmsRecord;
import com.mashibing.servicesms.entity.ServiceSmsTemplate;
import com.mashibing.servicesms.service.ServiceSms;
import com.mashibing.servicesms.util.ThirdSmsSenderUtil;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/***********************
 * @Description: 短消息业务实现类 <BR>
 * @author: zhao.song
 * @since: 2021/5/12 18:35
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Service
@Slf4j
public class ServiceSmsImpl implements ServiceSms {

    /**
     * 本地缓存服务容器(google guava cache)
     * TIP: 思考--为什么要将模板放入缓存?
     * 模板的内容是有限的.一个模板大小81B左右, 1k大概可以100个, 1M存放就是10w个,所以放入内存完全够用了
     * 另外对于短信模板而言,只会有删除不会有对模板进行更新.如果需要更新,这一套使用就要考虑了
     */
    private static Cache<String, String> localCache = CacheBuilder.newBuilder().build();

    @Autowired
    private ServiceSmsTemplateDao smsTemplateDao;

    @Autowired
    private ServiceSmsRecordDao smsRecordDao;

    /**
     * Description: 发送短消息 <BR>
     *
     * @param smsSendRequest :
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/12 18:35
     */
    @Override
    public ResponseResult sendSms(SmsSendRequest smsSendRequest) {
        smsSendRequest.getReceivers().forEach(receiver -> {
            List<SmsTemplateDto> data = smsSendRequest.getData();
            // 消息记录
            ServiceSmsRecord smsRecord = ServiceSmsRecord.builder().phoneNumber(receiver).build();
            data.forEach(smsTemplateDto -> {
                // 1.获取模板ID
                String templateId = smsTemplateDto.getId();
                // 2.加载模板内容
                String cacheTemplateContent = loadTemplateByCache(templateId);
                if (StringUtils.isBlank(cacheTemplateContent)) {
                    // forEach 中 return 等同于 continue
                    return;
                }
                // 替换占位符
                SmsTemplateData templateData = smsTemplateDto.getTemplateData();
                cacheTemplateContent = StringUtils.replace(cacheTemplateContent
                        , "${code}", templateData.getCode());

                // 3.第三方短信发送
                String fCacheTemplateContent = cacheTemplateContent;
                Try.run(() -> {
                    ThirdSmsStatusEnum statusEnum = sendMsg(receiver, fCacheTemplateContent);

                    smsRecord.setSendTime(new Date());
                    smsRecord.setOperator("");
                    smsRecord.setSendFlag(true);
                    smsRecord.setSendNumber(0);
                    smsRecord.setSmsContent(fCacheTemplateContent);
                    if (statusEnum.getCode() != ThirdSmsStatusEnum.SEND_SUCCESS.getCode()) {
                        throw new Exception("短信发送失败!" + statusEnum.getValue());
                    }
                }).onFailure((ex) -> {
                    smsRecord.setSendFlag(false);
                    smsRecord.setSendNumber(1);
                    log.error("发送短信(" + templateId + ")失败:" + receiver, ex);
                }).andFinally(()->{
                    smsRecord.setCreateTime(new Date());
                    smsRecordDao.insert(smsRecord);
                });
            });
        });

        return ResponseResult.success("");
    }

    /**
     * Description: 获取模板的样式 <BR>
     *
     * @param templateId:
     * @return: {@link String}
     * @author: zhao.song   2021/5/13 10:48
     */
    public String loadTemplateByCache(String templateId) {
        String cacheTemplateContent = localCache.getIfPresent(templateId);
        if (StringUtils.isNotBlank(cacheTemplateContent)) {
            return cacheTemplateContent;
        }
        ServiceSmsTemplate dbTemplate = smsTemplateDao.selectByTemplateId(templateId);
        if (Objects.isNull(dbTemplate)) {
            return null;
        }
        localCache.put(templateId, dbTemplate.getTemplateContent());
        return dbTemplate.getTemplateContent();
    }

    private ThirdSmsStatusEnum sendMsg(String phoneNumber, String templateContent) {
        /**
         * TODO 供应商 发 短信
         */

        return ThirdSmsSenderUtil.sendMsg(phoneNumber, templateContent);
    }

    public static void main(String[] args) {
        LinkedList<String> linkList = new LinkedList<>();

        System.out.println(Try.of(()->linkList.getLast()).getOrNull());
        System.out.println("/ssfas" + File.separator + "fasfd");

        io.vavr.collection.List<String> list = io.vavr.collection.List.of("1", "2", "3", "4");
        list.forEach(ele -> {
            if (ele.equals("2")) {
                return;
            }
            System.out.println(ele);
        });
    }
}
