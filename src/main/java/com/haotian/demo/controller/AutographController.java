package com.haotian.demo.controller;

import com.haotian.demo.bean.Autograph;
import com.haotian.demo.bean.Weburl;
import com.haotian.demo.util.Sha1;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.UUID;


@Controller
public class AutographController {


    static String jsapi_titcket = "bxLdikRXVbTPdHSM05e5u34V7bqwihxibgWW74cx9o685pedmHUYiDeLa5mCgd7YCtO3K47idhNOKK6Z9aLq8A";



    /**
     * 前段传过来一个url对象该方法对其进行封装
     *
     * @param weburl
     * @return Autograph
     */
    @RequestMapping("/received")
    @ResponseBody
    public Autograph receive(@RequestBody Weburl weburl) throws Exception {
        Autograph autograph = new Autograph();

        UUID appId = UUID.randomUUID();
        String timestamp = new Date().getTime() + "";
        timestamp=timestamp.substring(0,10);
        String noncestr = UUID.randomUUID().toString().replace("-", "");
        String url = weburl.getUrl();
        String jsapi_ticket = "jsapi_ticket=" + jsapi_titcket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + weburl.getUrl();
        Sha1 sha1 = new Sha1();
         String signature = DigestUtils.sha1Hex(jsapi_ticket);
        autograph.setAppId(String.valueOf(appId));
        autograph.setSignature(signature);
        autograph.setNonceStr(noncestr);
        autograph.setTimestamp(timestamp);
        System.out.println("a:"+signature);

        return autograph;
    }

    @RequestMapping("/aa123")
    public @ResponseBody  String aa123(){
        return "aa123";
    }

}

