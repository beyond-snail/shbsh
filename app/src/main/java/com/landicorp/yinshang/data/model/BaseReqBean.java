package com.landicorp.yinshang.data.model;

/**
 * Created by u on 2017/1/6.
 */

public class BaseReqBean<T> {

    private String cmd;
    private T params;

    public void setParams(T params) {
        this.params = params;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

}


