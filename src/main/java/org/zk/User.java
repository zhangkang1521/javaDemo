package org.zk;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhangkang on 2018/4/3.
 */
public class User {
    private int id;
    @JsonProperty("username-xx")
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
