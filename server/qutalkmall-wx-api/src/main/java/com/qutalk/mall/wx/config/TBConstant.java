package com.qutalk.mall.wx.config;

/**
 * @author Jalon
 * @Description:
 * @create 2018/10/30 - 14:50
 * @package com.wxserver.core.model
 */
public class TBConstant {
    public static final String url = "http://gw.api.taobao.com/router/rest";

    //创建应用时，TOP颁发的唯一标识，TOP通过App Key来鉴别应用的身份。调用接口时必须传入的参数。
    public static final String appkey = "25417232";

    /**
     * SessionKey简单的说就是代表卖家的登录session
     * SessionKey是用户身份的标识，应用获取到了SessionKey即意味着应用取得了用户的授权，可以替用户向TOP请求用户的
     */
    public static final String sessionKey = "*********";

    //App Secret是TOP给应用分配的密钥，开发者需要妥善保存这个密钥，这个密钥用来保证应用来源的可靠性，防止被伪造。
    public static final String secret = "3d1af618ee33a89f562c68f8487df751";
}
