package com.mashibing.internalcommon.util;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * description  id生成器 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/30 14:50
 **/
public final class IdWorker {
    private static AtomicInteger sequence = new AtomicInteger();

    private static String lastTimeStamp = "";

    private static Integer value = 4;

    /**
     * description: 如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环)
     * ，下次再使用时sequence是新值 <BR>
     *
     * @param :
     * @return  {@link String}
     * @author  zhao.song   2021/5/30 14:57
    */
    public static String nextId() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timestamp = df.format(new Date());
        if (lastTimeStamp.equals(timestamp)) {
            System.out.println(true);
            sequence.set(0);
        }
        IdWorker.lastTimeStamp = timestamp;
        return timestamp.concat(fixedLenSeq());
    }

    private static String fixedLenSeq() {
        String seq = "000" + sequence.incrementAndGet();
        if (seq.length() > IdWorker.value) {
            return seq.substring(seq.length() - 4, seq.length());
        }
        return seq;
    }

    public static void main(String[] args) {
        IntStream.range(0,100).forEach(idx->{
            try {
                TimeUnit.MILLISECONDS.sleep(1);
                new Thread(() -> System.out.println(nextId())).start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
//        System.out.println(nextId());
    }
}
