package cn.mchina.robot;

import cn.mchina.robot.util.HttpUtils;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 14-2-11
 * Time: 下午1:49
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static final String SIMI_KEY="7f1de4f9-58d0-417c-a940-db470b233805";
    public static final String REQUEST_URL="http://sandbox.api.simsimi.com/request.p?key=" + SIMI_KEY + "&lc=ch&ft=1.0&text=";

//    public static void main(String[] args) {
//        String ask = "你好!";
//        try {
//            String text = HttpUtils.sendGet(REQUEST_URL + ask,
//                    null, "UTF-8");
//            System.out.println(text);
//        } catch (Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//    }
public static void main(String args[]) throws IOException {
    System.out.println("机器人启动");
    String s;
    do {
        Scanner sc = new Scanner(System.in);
        s = sc.next();
        String text = HttpUtils.sendGet(REQUEST_URL + s,
                null, "UTF-8");
        System.out.println(text);
    } while (!s.equals("再见") || !s.equals("exit"));
}
}
