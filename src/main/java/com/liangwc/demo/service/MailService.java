package com.liangwc.demo.service;

import com.liangwc.demo.base.lang.Result;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * @author liangweicheng
 * @date 2019/11/22 11:28
 */
public interface MailService {
    Result send(String template, String email);
}
