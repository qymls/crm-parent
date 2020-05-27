package cn.nine.crm.util;

import lombok.Data;

@Data
public class Result {
    private Boolean success = true;

    private Long code;

    private String message = "操作成功";
    /*返回的数据*/
    private Object data;

    /**
     * 成功
     *
     * @return
     */
    public static Result ok() {
        return new Result();
    }

    public static Result ok(Object data) {
        Result result = Result.ok();
        result.setData(data);
        return result;
    }

    public static Result ok(Object data,Long code) {
        Result result = Result.ok();
        result.setData(data);
        result.setCode(code);
        return result;
    }

    public static Result singcode(Long code) {
        Result result = Result.ok();
        result.setCode(code);
        return result;
    }

    /**
     * 错误
     *
     * @param message
     * @return
     */
    public static Result error(String message) {
        Result result = new Result();
        result.setMessage(message);
        result.setSuccess(false);
        return result;
    }

    public static Result error(String message, Object data) {
        Result result = Result.error(message);
        result.setData(data);
        return result;
    }
}
