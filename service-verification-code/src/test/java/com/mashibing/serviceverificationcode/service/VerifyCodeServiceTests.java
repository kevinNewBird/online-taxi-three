package com.mashibing.serviceverificationcode.service;

import com.mashibing.serviceverificationcode.pojo.VerifyCodeLease;
import com.mashibing.serviceverificationcode.service.impl.VerifyCodeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

/***********************
 * @Description: TODO 类描述<BR>
 * @author: zhao.song
 * @since: 2021/5/11 11:09
 * @version: 1.0
 ***********************/
@SpringBootTest
@Slf4j
public class VerifyCodeServiceTests {

    ThreadLocal<String> threadLocal = new ThreadLocal<>();

    boolean flag;

    @Autowired
    private VerifyCodeServiceImpl verifyCodeService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * Description: 验证码生成测试 <BR>
     *
     * @param :
     * @return:
     * @author: zhao.song   2021/5/11 11:21
     */
    @Test
    public void testGenerate() {
        threadLocal.set(null);
        verifyCodeService.generate(1, "178328382939");
        System.out.println(threadLocal.get());
    }

    @Test
    public void testLimitSendFrequency(){
        System.out.println(flag);
        for (int i = 0; i < 10; i++) {
            verifyCodeService.checkSendCodeTimeLimit("123442345454");
        }
    }

    @Test
    public void testRedisHash() {
        VerifyCodeLease lease = VerifyCodeLease.builder().firstObtainTime(System.currentTimeMillis()).build();
        redisTemplate.opsForValue().set("CODE",lease);
        System.out.println(redisTemplate.opsForValue().get("CODE"));
    }

    @Test
    public void testCodeGenerationOptimization() {
        generateCodeByHeap();
        generateCodeByStack();
    }

    private static final int REPEAT_NUM = 1_000_000;

    //通过【堆内】运算
    private static void generateCodeByHeap() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < REPEAT_NUM; i++) {
            String code = String.valueOf(Math.random()).substring(2, 8);
        }
        long end = System.currentTimeMillis();
        log.info("generate by heap, spend time: " + (end - start));
    }

    //通过【栈内】运算
    private static void generateCodeByStack() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < REPEAT_NUM; i++) {
            String code = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));
        }
        long end = System.currentTimeMillis();
        log.info("generate by stack, spend time: " + (end - start));
    }
}
