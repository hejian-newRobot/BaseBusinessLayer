package org.cloud.microservice.business.utils;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * bcrypt 加密工具
 *
 * @author hejian
 */
public class BcryptPasswordEncoderUtil {

    public static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

}
