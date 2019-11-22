package com.liangwc.demo.controller;

import com.liangwc.demo.base.lang.Constants;
import com.liangwc.demo.base.lang.Result;
import com.liangwc.demo.base.util.RegrexUtils;
import com.liangwc.demo.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author liangweicheng
 * @date 2019/11/21 15:53
 */
@Slf4j
@Controller
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private MailService mailService;

    private static final String TEMPLATE = "email_code.ftl";

    @GetMapping("/sendcode")
    @ResponseBody
    public Result sendCode(String email) {
        if (null == email || email.length() == 0) {
            return Result.failure(Constants.ERROR_CODE, "请输入邮箱地址!");
        }
        if (!RegrexUtils.checkEmail(email)) {
            return Result.failure(Constants.ERROR_CODE, "邮箱格式有误!请重新输入");
        }
        Result result = mailService.send(TEMPLATE, email);
        if (result.isOk()) {
            log.info("验证邮件发送成功...");
        } else {
            log.error("验证邮件发送失败...");
        }
        return result;
    }
}
