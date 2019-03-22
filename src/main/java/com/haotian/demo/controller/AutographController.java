package com.haotian.demo.controller;

import com.haotian.demo.bean.Autograph;
import com.haotian.demo.bean.Weburl;
import com.haotian.demo.util.Cache;
import com.haotian.demo.util.Sha1;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

@Controller
public class AutographController {


    public  String getCode(int n) {
        String string = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";//保存数字0-9 和 大小写字母
        char[] ch = new char[n]; //声明一个字符数组对象ch 保存 验证码
        for (int i = 0; i < n; i++) {
            Random random = new Random();//创建一个新的随机数生成器
            int index = random.nextInt(string.length());//返回[0,string.length)范围的int值    作用：保存下标
            ch[i] = string.charAt(index);//charAt() : 返回指定索引处的 char 值   ==》保存到字符数组对象ch里面
        }
        //将char数组类型转换为String类型保存到result
        //String result = new String(ch);//方法一：直接使用构造方法      String(char[] value) ：分配一个新的 String，使其表示字符数组参数中当前包含的字符序列。
        String result = String.valueOf(ch);//方法二： String方法   valueOf(char c) ：返回 char 参数的字符串表示形式。
        return result;
    }




    /**
     * 前段传过来一个url对象该方法对其进行封装
     *
     * @param weburl
     * @return Autograph
     */
    @RequestMapping("/received")
    @ResponseBody
    public Autograph receive(@RequestBody Weburl weburl) throws Exception {
        String abc=this.getCode(20);
        Autograph autograph = new Autograph();
        String key = "ticket";
        String jsapi_ticket;
        // 进行缓存判断
        System.out.println("key:" + key + ", value:" + Cache.get(key));
        if (Cache.get(key) != null) {
            // String jsapi_ticket=smsClient2.getJsapiTicket(new HashMap<String,String>());
            jsapi_ticket = (String) Cache.get(key);

        } else {
            jsapi_ticket =  abc;
            // 将生成的值放入缓存中
            Cache.put(key, jsapi_ticket, 50000);
        }
        System.out.println(Cache.get(key));


        UUID appId = UUID.randomUUID();
        String timestamp = new Date().getTime() + "";
        timestamp = timestamp.substring(0, 10);
        String noncestr = UUID.randomUUID().toString().replace("-", "");
        String url = weburl.getUrl();
        jsapi_ticket = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url="
                + weburl.getUrl();

        String signature = DigestUtils.sha1Hex(jsapi_ticket);
        autograph.setAppId(String.valueOf(appId));
        autograph.setSignature(signature);
        autograph.setNonceStr(noncestr);
        autograph.setTimestamp(timestamp);
        System.out.println("a:" + signature);

        return autograph;
    }

    @RequestMapping("/aa123")
    public @ResponseBody  String aa123(){
        return "aa123";
    }

}

