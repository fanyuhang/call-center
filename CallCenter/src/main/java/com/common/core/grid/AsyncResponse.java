package com.common.core.grid;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 执行异步请求返回的实体
 * spring mvc 中的json conventer将对该实体进行转换，返回对应的json字符串给客户端
 * IsError 用于标记异步请求是否有错误
 * message 异步请求处理之后返回的消息，通常请求失败后，该字段中会填入失败原因
 * Data 异步请求成功之后，返回给前端的数据
 */
public class AsyncResponse {

    @JsonProperty("IsError")
    private boolean IsError;

    @JsonProperty("Data")
    private List Data = new ArrayList();

    @JsonProperty("Message")
    private String Message;

    public AsyncResponse() {
    }

    public AsyncResponse(boolean isError, String message) {
        IsError = isError;
        this.Message = message;
    }

    public AsyncResponse(boolean isError, String message, Collection data) {
        IsError = isError;
        Message = message;
        this.Data.addAll(data);
    }

    @JsonIgnore
    public boolean isIsError() {
        return IsError;
    }

    @JsonIgnore
    public void setIsError(boolean isError) {
        IsError = isError;
    }

    @JsonIgnore
    public List getData() {
        return Data;
    }

    @JsonIgnore
    public void setData(List data) {
        Data = data;
    }

    public void addData(Collection data) {
        this.Data.addAll(data);
    }

    @JsonIgnore
    public String getMessage() {
        return Message;
    }

    @JsonIgnore
    public void setMessage(String message) {
        this.Message = message;
    }
}
