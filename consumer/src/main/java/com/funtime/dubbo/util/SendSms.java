package com.funtime.dubbo.util;

/**
 * Created by steven on 2018/9/25.
 */
import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class SendSms {
    public static SendSmsResponse sendSms(String phoneNumber,String code) throws ClientException {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");//不必修改
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");//不必修改
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIqn05yVKZWJGE", "JeqhYWkumPCW6dkTRnrjXiHPLabKIY");//"***"分别填写自己的AccessKey ID和Secret
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");//不必修改
        IAcsClient acsClient = new DefaultAcsClient(profile);//不必修改
        SendSmsRequest request = new SendSmsRequest();//不必修改
        request.setPhoneNumbers(phoneNumber);//****处填写接收方的手机号码
        request.setSignName("GirlsHi2018");//此处填写已申请的短信签名
        request.setTemplateCode("SMS_146280547");//此处填写获得的短信模版CODE
        request.setTemplateParam("{\"code\":\""+code+"\"}");//笔者的短信模版中有${code}, 因此此处对应填写验证码
//        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);//不必修改
        SendSmsResponse sendSmsResponse = null;
        String uuid = StringUtils.uuid();
        sendSmsResponse.setBizId(uuid);
        sendSmsResponse.setCode("1234");
        sendSmsResponse.setMessage("success");
        sendSmsResponse.setRequestId(uuid);
        return sendSmsResponse;
    }
    public static void main(String[] args) throws ClientException, InterruptedException {
        SendSmsResponse response = sendSms("13818939610","123456");
        System.out.printf(""+ JSON.toJSONString(response));
    }
}