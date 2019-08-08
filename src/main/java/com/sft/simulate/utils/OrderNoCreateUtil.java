package com.sft.simulate.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNoCreateUtil {

    private final static String DATE_FORMAT = "yyMMddHHmmss";
    private final static SimpleDateFormat foramt = new SimpleDateFormat(DATE_FORMAT);
    private static Integer serialNo = 0; // 自增的流水号计数器
    private static final Integer MAX_SERIAL_NO = 9999;// 最大计数值
    private static final int SERIAL_NO_LEN = 4;
    private static final String FILL_CHAR = "00000000";// 不足位数左补零

    /**
     * 生成系统订单号 订单号组成格式： yyMMddHHmmss+三位机器码(IP最后一截左补零)+四位计数器
     *
     * @return
     */
    public static final String getOrderNo() {
        synchronized (serialNo) {
            int sNo = serialNo;
            serialNo++;
            if (serialNo > MAX_SERIAL_NO) {
                serialNo = 0;
            }
            return foramt.format(new Date()) + FILL_CHAR.substring(0, SERIAL_NO_LEN - (sNo + "").length()) + sNo;
        }
    }

}
