package com.mashibing.eureka.listener;

import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/***********************
 * @Description: 服务监听<BR>
 * @author: zhao.song
 * @since: 2021/5/7 22:31
 * @version: 1.0
 ***********************/
@Component
public class TestEvent {
    private static final ThreadLocal<String> threadLocalCache = new ThreadLocal<>();

    @EventListener
    public void listen(EurekaInstanceCanceledEvent event) {
        // 发邮件 短信

    }

    public static void main(String[] args) {
        threadLocalCache.remove();
        // timer的缺陷

        TimerTask t0 = new TimerTask() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t0");
            }
        };

        TimerTask t1 = new TimerTask() {
            @Override
            public void run() {
                int i = 1 / 0;
                System.out.println("sss");
            }
        };
        Timer timer = new Timer();
        timer.schedule(t1, 0);
        //由于 t1 任务的异常抛出， 直接导致 t0 任务终止
        timer.schedule(t0, 0);
    }

}
