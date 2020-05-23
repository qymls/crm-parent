package cn.nine.crm.utils;

import lombok.Data;

@Data
public class JSONResult {

    private boolean success = true;

    //返回的信息【有可能是异常信息】
    private String message = "成功";

    //返回的数据
    private Object data;

    //成功结果
    public static JSONResult ok(){
        return new JSONResult();
    }

    public static JSONResult ok(Object data){
        JSONResult jsonResult = JSONResult.ok();
        jsonResult.setData(data);
        return jsonResult;
    }

    //失败结果
    public static JSONResult error(String errorMessage){
        JSONResult jsonResult = JSONResult.ok();
        jsonResult.setSuccess(false);
        jsonResult.setMessage(errorMessage);
        return jsonResult ;
    }

    public static JSONResult error(String errorMessage,Object data){
        JSONResult jsonResult = JSONResult.error(errorMessage);
        jsonResult.setData(data);
        return jsonResult ;
    }
}
