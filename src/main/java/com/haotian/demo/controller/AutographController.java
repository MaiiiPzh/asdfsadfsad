package com.haotian.demo.controller;

import com.haotian.demo.bean.Autograph;
import com.haotian.demo.bean.Weburl;
import com.haotian.demo.util.Sha1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.UUID;


@Controller
public class AutographController {


    static String jsapi_titcket = "sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg";



    /**
     * 前段传过来一个url对象该方法对其进行封装
     *
     * @param weburl
     * @return Autograph
     */
    @RequestMapping("/received")
    @ResponseBody
    public Autograph receive(@RequestBody Weburl weburl) {
        Autograph autograph = new Autograph();

        UUID appId = UUID.randomUUID();
        String timestamp = new Date().getTime() + "";
        String noncestr = UUID.randomUUID().toString().replace("-", "");
        String url = weburl.getUrl();
        String jsapi_ticket = "jsapi_ticket=" + jsapi_titcket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + weburl.getUrl();
        Sha1 sha1 = new Sha1();

        String signature = sha1.encode(jsapi_ticket);
        autograph.setAppId(String.valueOf(appId));
        autograph.setSignature(signature);
        autograph.setNonceStr(noncestr);
        autograph.setTimestamp(timestamp);

        autograph.setTimestamp(timestamp);

        return autograph;
    }

    @RequestMapping("/aa123")
    public @ResponseBody  String aa123(){
        return "aa123";
    }

}

