package org.fall.util;


public class ResultEntity<T> {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";

    //请求错误时，返回的错误信息
    private String message;

    //要返回的数据
    private T data;

    //封装当前请求的处理结果是成功还是失败
    private String result;

    //第一个<Type>表示声明一个泛型Type，第二个和return中的<Type>表示使用该泛型
    public static <Type> ResultEntity<Type> successWithoutData(){
        return new ResultEntity<Type>(null,null,SUCCESS);
    }

    public static <Type> ResultEntity<Type> successWithData(Type data){
        return new ResultEntity<Type>(null,data,SUCCESS);
    }

    public static <Type> ResultEntity<Type> failed(String message){
        return new ResultEntity<>(message,null,FAILED);
    }




    public ResultEntity(String message, T data, String result) {
        this.message = message;
        this.data = data;
        this.result = result;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ResultEntity() {
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "message='" + message + '\'' +
                ", data=" + data +
                ", result='" + result + '\'' +
                '}';
    }
}
