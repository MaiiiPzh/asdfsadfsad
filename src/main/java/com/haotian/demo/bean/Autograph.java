package com.haotian.demo.bean;

import lombok.Data;

@Data
public class Autograph {
    //公共号的唯一标识(必填)
    private String  appId;
    //生成签名的时间戳(必填)
    private String timestamp;
    //生成签名的的随机字符串(必填)
    private String  nonceStr;
    //签名(必填)
    private String  signature;
}
