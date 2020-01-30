package com.grtwwh2019.vhr.model;

public class RespBean {

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 输出信息
     */
    private String message;
    /**
     * 输出内容
     */
    private Object data;

    /**
     * 自己构建RespBean
     */
    public static RespBean build() {
        return new RespBean();
    }

    public static RespBean success(String msg) {
        return new RespBean(200, msg, null);
    }

    public static RespBean success(String msg, Object data) {
        return new RespBean(200, msg, data);
    }

    public static RespBean error(String msg) {
        return new RespBean(500, msg, null);
    }

    public static RespBean error(String msg, Object data) {
        return new RespBean(500, msg, data);
    }

    public RespBean(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public RespBean() {
    }

    public Integer getCode() {
        return code;
    }

    public RespBean setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RespBean setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public RespBean setData(Object data) {
        this.data = data;
        return this;
    }
}
