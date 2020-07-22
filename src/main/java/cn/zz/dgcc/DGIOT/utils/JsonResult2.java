package cn.zz.dgcc.DGIOT.utils;

/**
 * Created by: LT001
 * Date: 2020/6/18 16:21
 * ClassExplain :
 * ->
 */
public class JsonResult2<T> {
    private Integer code;
    private String message;
    private Integer count;
    private T data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public JsonResult2() {
    }

    public JsonResult2(Integer code, T data, Integer count) {
        this.code = code;
        this.data = data;
        this.count = count;
    }

    public JsonResult2(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public JsonResult2(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public JsonResult2(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
