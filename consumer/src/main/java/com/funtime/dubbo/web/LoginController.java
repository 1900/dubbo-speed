package com.funtime.dubbo.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.funtime.dubbo.base.Constant;
import com.funtime.dubbo.base.PublicResult;
import com.funtime.dubbo.base.PublicResultConstant;
import com.funtime.dubbo.config.annotation.CurrentUser;
import com.funtime.dubbo.config.annotation.Log;
import com.funtime.dubbo.config.annotation.Pass;
import com.funtime.dubbo.config.annotation.ValidationParam;
import com.funtime.dubbo.dto.SysUserInfo;
import com.funtime.dubbo.entity.SmsVerify;
import com.funtime.dubbo.entity.User;
import com.funtime.dubbo.service.ISmsVerifyService;
import com.funtime.dubbo.service.IUserService;
import com.funtime.dubbo.util.StringUtils;
import com.funtime.dubbo.util.SmsSendUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 登录接口
 */
@RestController
@Api(description = "身份认证,注册登录等")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Reference(version = "1.0.0")
    private IUserService userService;

    @Reference(version = "1.0.0")
    private ISmsVerifyService smsVerifyService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @ApiOperation(value = "手机密码登录", notes = "body体参数,不需要Authorization", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestJson", value = "{\"regionCode\":\"86\",\"mobile\":\"13818939610\",\"passWord\":\"123456\"}"
                    , required = true, dataType = "String", paramType = "body")
    })
    @PostMapping("/login")
    @Log(description = "前台密码登录接口:/login")
    @Pass
    public PublicResult<?> login(
            @ValidationParam("mobile,passWord") @RequestBody JSONObject requestJson) throws Exception {
        String mobile = requestJson.getString("mobile");
        logger.info("REST request to get login: {} " + mobile + "\n");
        if (!StringUtils.checkMobileNumber(mobile)) {
            return new PublicResult<>(PublicResultConstant.MOBILE_ERROR, null);
        }
        User user = userService.selectOne(new EntityWrapper<User>().where("mobile = {0} and status = 0", mobile));
        logger.info("REST request to get user: {} " + user.getUserName() + "\n");
        if (StringUtils.isEmpty(user) || !BCrypt.checkpw(requestJson.getString("passWord"), user.getPassWord())) {
            return new PublicResult<>(PublicResultConstant.INVALID_USERNAME_PASSWORD, null);
        }
        SysUserInfo result = null;
        SysUserInfo value = (SysUserInfo) redisTemplate.opsForValue().get("ACCESS_TOKEN" + ":" + user.getId());
        if (null == value) {
            result = userService.getLoginUserByUserNo(String.valueOf(user.getId()));
            // to redis
            redisTemplate.opsForValue().set("ACCESS_TOKEN" + ":" + user.getId(), result);
            // 时效30天
            redisTemplate.expire("ACCESS_TOKEN" + ":" + user.getId(), 30 * 24 * 60 * 60, TimeUnit.SECONDS);
        } else {
            result = value;
        }

        logger.info("REST request to get result: {} " + result + "\n");
        return new PublicResult<>(PublicResultConstant.SUCCESS, result);
    }

    @ApiOperation(value = "短信验证码登录", notes = "body体参数,不需要Authorization", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestJson", value = "{\"regionCode\":\"86\",\"mobile\":\"13888888888\",\"captcha\":\"5780\"}"
                    , required = true, dataType = "String", paramType = "body")
    })

    @PostMapping("/login/captcha")
    @Log(description = "前台短信验证码登录接口:/login/captcha")
    @Pass
    public PublicResult<?> loginBycaptcha(
            @ValidationParam("mobile,captcha") @RequestBody JSONObject requestJson) throws Exception {
        logger.info("REST request to get login captcha: {} " + requestJson + "\n");
        String mobile = requestJson.getString("mobile");
        if (!StringUtils.checkMobileNumber(mobile)) {
            return new PublicResult<>(PublicResultConstant.MOBILE_ERROR, null);
        }
        User user = userService.getUserByMobile(mobile);
        SysUserInfo result = new SysUserInfo();
        String isNewUser = "0";

// 如果不是启用的状态
        if (!StringUtils.isEmpty(user) && !StringUtils.equals(user.getStatus(), Constant.ENABLE)) {
            return new PublicResult<>("该用户状态不是启用的!", null);
        }
        if (StringUtils.isEmpty(user)) {
            User userRegister = new User();
            //设置默认密码
            userRegister.setPassWord(BCrypt.hashpw("123456", BCrypt.gensalt()));
            userRegister.setMobile(mobile);
            userRegister.setUserName(mobile);
            user = userService.register(userRegister, Constant.RoleType.USER);
            // 新用户
            isNewUser = "1";
        }
        result = userService.getLoginUserByUserNo(String.valueOf(user.getId()));
        result.setIsNewUser(isNewUser);
        return new PublicResult<>(PublicResultConstant.SUCCESS, result);
    }


    @ApiOperation(value = "手机验证码注册", notes = "body体参数,不需要Authorization,备注:language(1 中文,2 英文,3 繁体);gender(0 男, 1 女 ) ", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestJson", value = "{\"regionCode\":\"86\",\"language\":\"1\",\"mobile\":\"15577144885\",</br>" +
                    "\"captcha\":\"5780\",\"passWord\":\"123456\",</br>\"gender\":\"0\"}"
                    , required = true, dataType = "String", paramType = "body")
    })
    @PostMapping("/register")
    @Log(description = "注册接口:/register")
    @Pass
    public PublicResult<User> register(@ValidationParam("gender,passWord,language,mobile,captcha")
                                       @RequestBody JSONObject requestJson) {
        User userRegister = requestJson.toJavaObject(User.class);
        if (!StringUtils.checkMobileNumber(userRegister.getMobile())) {
            return new PublicResult<>(PublicResultConstant.MOBILE_ERROR, null);
        }
        if (!userRegister.getPassWord().equals(requestJson.getString("passWord"))) {
            return new PublicResult<>(PublicResultConstant.INVALID_RE_PASSWORD, null);
        }
        List<SmsVerify> smsVerifies = smsVerifyService.getByMobileAndCaptchaAndType(userRegister.getMobile(),
                requestJson.getString("captcha"), SmsSendUtil.SMSType.getType(SmsSendUtil.SMSType.REG.name()));
        if (StringUtils.isEmpty(smsVerifies)) {
            return new PublicResult<>(PublicResultConstant.VERIFY_PARAM_ERROR, null);
        }
        //验证码是否过期
        if (SmsSendUtil.isCaptchaPassTime(smsVerifies.get(0).getCreateDate().getTime())) {
            return new PublicResult<>(PublicResultConstant.VERIFY_PARAM_PASS, null);
        }
        userRegister.setPassWord(BCrypt.hashpw(requestJson.getString("passWord"), BCrypt.gensalt()));
        userService.register(userRegister, Constant.RoleType.USER);
        return new PublicResult<>(PublicResultConstant.SUCCESS, null);
    }


    @ApiOperation(value = "忘记密码", notes = "body体参数,不需要Authorization", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestJson", value = "{\"regionCode\":\"86\",\"mobile\":\"13888888888\",\"captcha\":\"5780\",</br>" +
                    "\"passWord\":\"123456\",\"rePassWord\":\"123456\"}"
                    , required = true, dataType = "String", paramType = "body")
    })
    @PostMapping("/forget/password")
    @Pass
    public PublicResult<String> resetPassWord(@ValidationParam("mobile,captcha,passWord,rePassWord")
                                              @RequestBody JSONObject requestJson) throws Exception {

        String mobile = requestJson.getString("mobile");
        logger.info("REST request to resetPassWord: {} " + mobile);
        if (!StringUtils.checkMobileNumber(mobile)) {
            return new PublicResult<>(PublicResultConstant.MOBILE_ERROR, null);
        }
        if (!requestJson.getString("passWord").equals(requestJson.getString("rePassWord"))) {
            return new PublicResult<>(PublicResultConstant.INVALID_RE_PASSWORD, null);
        }
        User user = userService.getUserByMobile(mobile);
        if (StringUtils.isEmpty(user)) {
            return new PublicResult<>(PublicResultConstant.INVALID_USER, null);
        }
        List<SmsVerify> smsVerifies = smsVerifyService.getByMobileAndCaptchaAndType(mobile,
                requestJson.getString("captcha"), SmsSendUtil.SMSType.getType(SmsSendUtil.SMSType.FINDPASSWORD.name()));
        if (StringUtils.isEmpty(smsVerifies)) {
            return new PublicResult<>(PublicResultConstant.VERIFY_PARAM_ERROR, null);
        }
        if (SmsSendUtil.isCaptchaPassTime(smsVerifies.get(0).getCreateDate().getTime())) {
            return new PublicResult<>(PublicResultConstant.VERIFY_PARAM_PASS, null);
        }
        user.setPassWord(BCrypt.hashpw(requestJson.getString("passWord"), BCrypt.gensalt()));
        userService.updateById(user);
        return new PublicResult<String>(PublicResultConstant.SUCCESS, null);
    }

    /**
     * 检查用户是否注册过
     *
     * @param mobile
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/check/mobile"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Pass
    public PublicResult loginBycaptcha(@RequestParam("mobile") String mobile) throws Exception {
        logger.info("REST request to loginBycaptcha: {} " + mobile);
        User user = userService.getUserByMobile(mobile);
        return new PublicResult<>(PublicResultConstant.SUCCESS, !StringUtils.isEmpty(user));
    }

    /**
     * 登出
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public PublicResult logout(@CurrentUser User user) {
        redisTemplate.delete("ACCESS_TOKEN" + ":" + user.getId());
        return new PublicResult<String>(PublicResultConstant.SUCCESS, null);
    }
}
