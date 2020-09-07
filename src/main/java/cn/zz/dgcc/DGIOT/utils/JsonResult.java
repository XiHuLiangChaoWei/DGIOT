package cn.zz.dgcc.DGIOT.utils;

/**
 * Created by: LT001
 * Date: 2020/4/10 14:02
 * ClassExplain :   http response 返回封装
 * ->
 */
public class JsonResult<T> {
    private Integer state;
    private String message;
    private T data;

    public T getData1() {
        return data1;
    }

    public void setData1(T data1) {
        this.data1 = data1;
    }

    private T data1;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public JsonResult() {
        super();
    }

    public JsonResult(Integer state) {
        super();
        this.state = state;
    }

    public JsonResult(Throwable e) {
        super();
        this.message = e.getMessage();
    }

    public JsonResult(Integer state, T data) {
        this.state = state;
        this.data = data;
    }

    public JsonResult(Integer state, String message) {
        this.state = state;
        this.message = message;
    }

    public JsonResult(Integer state, T data, String message) {
        this.state = state;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "state=" + state +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", data1=" + data1 +
                '}';
    }

    public JsonResult(Integer state, T data, T data1){
        this.state = state;
        this.data = data;
        this.data1=data1;
    }

}
