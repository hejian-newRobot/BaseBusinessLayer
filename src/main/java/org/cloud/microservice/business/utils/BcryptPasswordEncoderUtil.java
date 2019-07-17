package org.cloud.microservice.business.utils;


import org.cloud.microservice.business.config.enums.EncryptionPrefix;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * bcrypt 加密工具
 *
 * @author hejian
 */
public class BcryptPasswordEncoderUtil {

    public static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public static String encode(String from) {
        return EncryptionPrefix.BCRYPT.prefix +
                BcryptPasswordEncoderUtil.PASSWORD_ENCODER.encode(from);
    }
}
