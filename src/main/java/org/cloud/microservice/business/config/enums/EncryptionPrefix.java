package org.cloud.microservice.business.config.enums;

/**
 * 项目名称：gateway-server
 * 包名称:common
 * 类描述：
 * 创建人：hejian
 * 创建时间：2019/7/17 19:11
 * 修改人：hejian
 * 修改时间：2019/7/17 19:11
 * 修改备注：
 *
 * @author hejian
 */
public enum EncryptionPrefix {
    /**
     * 枚举常见的加密算法的前缀
     */
    BCRYPT("bcrypt", "{bcrypt}"),
    MD5("MD5", "{MD5}"),
    NOOP("noop", "{noop}"),
    SHA_256("SHA-256", "{SHA-256}");

    public final String name;

    public final String prefix;

    EncryptionPrefix(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
    }
}
