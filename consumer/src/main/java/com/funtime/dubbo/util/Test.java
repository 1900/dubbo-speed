package com.funtime.dubbo.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;

/**
 * Created by steven on 2018/10/2.
 */
public class Test {
    public static void main(String[] args) {
//       String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTm8iOiJ1c2VyLTAwNmVmZWNlNzZjODQzM2Q4OTc0YzFhMmY5ODQyMmI2IiwiZXhwIjoxNTM4MjEwNzM4fQ.MqY7btJhYy6c0kqvMP0vj7YMrN-457ON0qtk19FM7gs";
//
//        String userNo = JWTUtil.getUserNo(token);
//        System.out.println("#####"+userNo);//检测是否插入成功
//
//        System.out.printf("---" + JWTUtil.verify(token, userNo, "XZUY77Pq41M6jaGeR2q1yMaPOrmemy6A"));

//        String APP_ID ="2018101861686686";
//        String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDPpnkbr5VrtcXjVAQShpG2nflXWdsDlSCltlndKQUz7DwWlsPp9x38Z6/4UAF1ROGjMU/qYy57nUBTXT+7V5XuKZCvY6AHSCin9mbgxbPYczeK3xmjkFVezP6YYKmi5NPjfVpElPESTlEfEi4Y5yNy57GxFszXmz7zN/e6GLR0mVifXF4iAdZYwsiAF27SUUYdM10Md7icow8x+2MHeGW+hUULvT4jJHQ4pp6fEawfSGl68Bwz90akIj1BI73P/9VXBeQG5Y8u0xsaGOcE9Uw9leE4uct8QPuKG1L4TgGA8xbUf3GS9zdBXQ8Cq51Z68ql1Jff4wlgZvCMDHdRq+gdAgMBAAECggEAa5gQzL2wKL1pKh+QdKDxw7QbTJ2slDCjV/oimiv1HDBd+j5oiUSYbv6y+XFLMB5e5BZbGFIH9dlHSWrZ6RK7B0rWQ2rVsrA7ELzmFbWbOJ6KuxRazputAJAro30CqPr0p71+FmcuepPGCNl8tT6e6Z0QP3hW76jrd08at3yxlHApV1aRu3HZ2vJnh6QS0dLPjx921xv/slTjqUIKALBNy80THMXuR7pxfP5istHQSQSucZjVwkDLSlOoD/0dEtcR+10wV1xt83tJwI2SxwMbwA1VzOiOWHP2M80UaR1efq7ichYi4Ig3k+l8wQuYmS9ybY0bLAIuh1SdW4E8mrD8KQKBgQD0mxRF8GeJYdiGddLrOkNJ1WEMXC6jXoKpQgIKHCGABx9tLtP1iyYStHhX8eLpvMFwSl5C1w/CKRvB0IEWYek27RudVn3pHwhkk//SkFIYGEOE6cFHrRpwWYUsLUrLymbTh7dBiy0IV/XpXDk4XyfN/YKMh5kIbg7TROg6+xOz1wKBgQDZUrM6Dr8vc7GvDLpuaTJbX+CfvEAqh5B753UfqiwiT9bfOMzqOf7S9pq+3WV/SLE5dnll94yxw8gt4l7rCaQsvdesO0cGuVA26NSHHX2f/GxqX7gYNXqYdbEfnVwUSTX/DVTpy20vSaY54U57j0cxnfc+EnSIe0Ta84hZYMKFKwKBgQDu8pOSYu9xuGDBsVXyrj7PYJ/maCKzOv8fAzlk0Og03kBn80fh5SWzPhMVrljBhr/cxvlgCCD/xhK0ec+wosMlAelpAPYNBmC3iBBvjMZ1fTZwH73NHB/owrqiVjhr7fQXDcwKPkAml27FoW7QT7xdsRmX2Fq5bnITFoxaGa28jwKBgHp/aDEokNGhiB1gslF8EHt67rV7Ojv+0A1XaSihFmF82WRXswGwERg/EvjU+NonUmZOhp1YTUECV3qtp80aUSU7GS+m9iEvIazhzrIeiGo+sanALJfQxTrc1Cs62uVAYyRSEnP6S6AlBoKHomJ5X6rzHKMrae5odWZpcYjXxq2jAoGAOpH5e+pGfgSeICBYK5840mOivrDkewvmxB/OFc91Yg8XHV56PwY2KYBS28c0YqUPKOLKvwEkgA841ZQ+U7Joj4xcd2LbtaBNTlEyvtQXCn0kOUMHZ2UHDJEs8k5psbMciuK129rkyJ2j0LIHuJBJiURy9NnuxAKA5iKmFDGUOwM=";
//        String CHARSET = "utf-8";
//        String ALIPAY_PUBLIC_KEY ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjyIOVvOdBLFOTWWe5LavrwNI4BoIlD5OyVC6lFnOpBitvQ5BOIlwVW4dmMogMVM+BED9xqVjBNv6HcAovatwHECtxyrEagS6H+7V9bycAEyDg1byrRVG44OHkfvumh+yLpIOh/wk85SLopuy8dd2h2/gPUYddqoL3PhbCQnid2aPMWJAjSUyM4OO2+gP8V6BzxfBrCjF1pqGiodTNN6CiloiCYEr7SJ6j6mu6rO6kL8tVx+/r1Iz/KDIiVybAXYRaXiGQqmrOvS5gn0IG0y314yaGc1qPMCr81BeUEG7juylWX6HGg2qKN38SMwJIbNFX1mriCnYIXvs9TarSIu4MQIDAQAB";
//        String outtradeno="pay_12121212";
//
//        //实例化客户端
//        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
////实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
//        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
////SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
//        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//        model.setBody("我是测试数据");
//        model.setSubject("App支付测试Java");
//        model.setOutTradeNo(outtradeno);
//        model.setTimeoutExpress("30m");
//        model.setTotalAmount("0.01");
//        model.setProductCode("QUICK_MSECURITY_PAY");
//        request.setBizModel(model);
//        request.setNotifyUrl("http://101.37.77.94:5555/api/v1/alipay/callback");
//        try {
//            //这里和普通的接口调用不同，使用的是sdkExecute
//            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
//            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }


        String jsonStr= HttpUtil.get("http://webforex.hermes.hexun.com/forex/quotelist?code=FOREXUSDCNY&column=Code,Price");
//        JSONArray jarr=JSONArray.parseArray(jsonStr);
//        for (int i = 0; i < jarr.size(); i++) {
            System.out.printf("----" + Double.parseDouble(jsonStr.split(",")[1].split("]")[0])/10000 +"\n");

//        }


    }
}
