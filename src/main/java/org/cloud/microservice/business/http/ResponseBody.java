package org.cloud.microservice.business.http;

/**
 * 项目名称：gateway-server
 * 包名称:common
 * 类描述：
 * 创建人：hejian
 * 创建时间：2019/7/16 10:30
 * 修改人：hejian
 * 修改时间：2019/7/16 10:30
 * 修改备注：
 *
 * @author hejian
 */
public class ResponseBody {

    /**
     * 响应主体
     */
    private Object body;

    /**
     * 状态码信息
     */
    private HttpStatus httpStatus;

    /**
     * 具体的消息，可选
     */
    private String msg;

    public final Object getBody() {
        return body;
    }

    public final ResponseBody setBody(Object body) {
        this.body = body;
        return this;
    }

    public final HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public final ResponseBody setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public final String getMsg() {
        return msg;
    }

    public final ResponseBody setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
