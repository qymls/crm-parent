package cn.nine.crm.util;

import lombok.Data;

@Data
public class Result {
    private Boolean success = true;

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

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
