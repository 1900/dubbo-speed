package com.funtime.dubbo.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.funtime.dubbo.base.Constant;
import com.funtime.dubbo.base.PublicResult;
import com.funtime.dubbo.base.PublicResultConstant;
import com.funtime.dubbo.config.annotation.CurrentUser;
import com.funtime.dubbo.config.annotation.ValidationParam;
import com.funtime.dubbo.dto.UserInfo;
import com.funtime.dubbo.entity.SmsVerify;
import com.funtime.dubbo.entity.User;
import com.funtime.dubbo.service.ISmsVerifyService;
import com.funtime.dubbo.service.IUserService;
import com.funtime.dubbo.util.StringUtils;
import com.funtime.dubbo.util.SmsSendUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/user")
@Api(description = "用户信息")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Reference(version = "1.0.0")
    private IUserService userService;

    @Reference(version = "1.0.0")
    private ISmsVerifyService smsVerifyService;

    /**
     * 获取当前登录用户信息
     *
     * @param user
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取当前登录用户信息", notes = "需要Authorization")
    @RequestMapping(value = {"/currentUser"}, method = {RequestMethod.GET}, produces = {"application/json"})
    public PublicResult<User> getUser(@CurrentUser User user) throws Exception {
        return new PublicResult<User>(PublicResultConstant.SUCCESS, user);
    }

    @PostMapping("/mobile")
    public PublicResult<String> resetMobile(@CurrentUser User currentUser,
                                            @ValidationParam("newMobile,captcha") @RequestBody JSONObject requestJson) {
        String newMobile = requestJson.getString("newMobile");
        if (!StringUtils.checkMobileNumber(newMobile)) {
            return new PublicResult<>(PublicResultConstant.MOBILE_ERROR, null);
        }
        List<SmsVerify> smsVerifies = smsVerifyService.getByMobileAndCaptchaAndType(newMobile,
                requestJson.getString("captcha"), SmsSendUtil.SMSType.getType(SmsSendUtil.SMSType.MODIFYINFO.name()));
        if (StringUtils.isEmpty(smsVerifies)) {
            return new PublicResult<>(PublicResultConstant.VERIFY_PARAM_ERROR, null);
        }
        if (SmsSendUtil.isCaptchaPassTime(smsVerifies.get(0).getCreateDate().getTime())) {
            return new PublicResult<>(PublicResultConstant.VERIFY_PARAM_PASS, null);
        }
        currentUser.setMobile(newMobile);
        userService.updateById(currentUser);
        return new PublicResult<>(PublicResultConstant.SUCCESS, null);
    }

    /**
     * 密码修改
     *
     * @param currentUser
     * @param requestJson
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "密码修改", notes = "需要Authorization")
    @PostMapping("/password")
    public PublicResult<?> resetPassWord(@CurrentUser User currentUser, @ValidationParam("oldPassWord,passWord,rePassWord")
    @RequestBody JSONObject requestJson) throws Exception {
        if (!requestJson.getString("passWord").equals(requestJson.getString("rePassWord"))) {
            return new PublicResult<>(PublicResultConstant.INVALID_RE_PASSWORD, null);
        }
        if (!BCrypt.checkpw(requestJson.getString("oldPassWord"), currentUser.getPassWord())) {
            return new PublicResult<>(PublicResultConstant.INVALID_USERNAME_PASSWORD, null);
        }
        String password = BCrypt.hashpw(requestJson.getString("passWord"), BCrypt.gensalt());
        User result = userService.selectOne(new EntityWrapper<User>().where("id = {0} and status = 0", currentUser.getId()));
        // 密码
        if (result != null && result.getPassWord() != null) {
            result.setPassWord(password);
        }
        Boolean b = userService.updateById(result);
        return new PublicResult<Boolean>(PublicResultConstant.SUCCESS, b);
    }

    @ApiOperation(value = "用户详情", notes = "需要Authorization")
    @RequestMapping(value = {"/currentInfo"}, method = {RequestMethod.GET}, produces = {"application/json"})
    public PublicResult<?> currentUserInfo(@CurrentUser User currentUser) throws Exception {
        logger.info("REST request to get user Info : {}" + JSON.toJSONString(currentUser));
        UserInfo user = userService.getCurrenUserInfo(currentUser);
        return new PublicResult<UserInfo>(PublicResultConstant.SUCCESS, user);
    }

    @ApiOperation(value = "用户查看非自己的详细信息", notes = "需要Authorization,传递需要查询的uid")
    @ApiImplicitParam(name = "uid", value = "用户uid", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = {"/info/{uid}"}, method = {RequestMethod.GET}, produces = {"application/json"})
    public PublicResult<UserInfo> findOneUser(@CurrentUser User currentUser, @PathVariable("uid") String uid) {
        UserInfo user = userService.getUserInfo(currentUser, uid);
        return StringUtils.isEmpty(user) ? new PublicResult<>(PublicResultConstant.INVALID_USER, null) : new PublicResult<>(PublicResultConstant.SUCCESS, user);
    }

    @ApiOperation(value = "用户查看非自己的基本信息,支持多个", notes = "需要Authorization,例: base/info?page=1&limit=10&users=1&users=1045969308774633473")
    @ApiImplicitParam(name = "uid", value = "用户uid", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = {"/base/info"}, method = {RequestMethod.GET}, produces = {"application/json"})
    public PublicResult<?> findOneBaseUser(@CurrentUser User currentUser, @RequestParam(value = "users") String[] users, Integer page, Integer limit) {
        StringBuffer usersStr = new StringBuffer();
        for (int i = 0; i < users.length; i++) {
            if (i > 0) {
                usersStr.append(",");
            }
            usersStr.append("'").append(users[i]).append("'");
        }
        Page<User> result = userService.selectPageByUsers(new Page<>(page, limit), String.valueOf(usersStr));
        return new PublicResult<Page<User>>(PublicResultConstant.SUCCESS, result);
    }

    /**
     * 个人信息保存或更新
     *
     * @param userInfo
     * @return
     * @throws URISyntaxException
     */
    @ApiOperation(value = "个人信息保存或更新", notes = "body体参数,需要Authorization", produces = "application/json")
    @PostMapping({"/saveOrUpdate"})
    public PublicResult<?> saveOrUpdate(@CurrentUser User user, @RequestBody User userInfo)
            throws URISyntaxException {
        logger.info("REST request to save user : {}", JSON.toJSONString(userInfo));
        if (StringUtils.isEmpty(userInfo)) {
            return new PublicResult<>(PublicResultConstant.INVALID_PARAM_EMPTY, null);
        }

        User result = userService.selectOne(new EntityWrapper<User>().where("id = {0} and status = 0", user.getId()));

        // 昵称,用户设置
        if (!StringUtils.isEmpty(userInfo.getNickName())) {
            result.setNickName(userInfo.getNickName());
        }

        // 来源
        if (userInfo != null && userInfo.getSource() != null) {
            result.setSource(userInfo.getSource());
        }
        // 头像
        if (userInfo != null && userInfo.getProfile() != null) {
            result.setProfile(userInfo.getProfile());
        }
        // 性别
        if (userInfo != null && userInfo.getGender() != null) {
            result.setGender(userInfo.getGender());
        }
        // passWord
        if (userInfo != null && userInfo.getPassWord() != null) {
            result.setPassWord(BCrypt.hashpw(userInfo.getPassWord(), BCrypt.gensalt()));
        }
        Boolean b = userService.insertOrUpdate(result);
        return new PublicResult<Boolean>(PublicResultConstant.SUCCESS, b);
    }
}

