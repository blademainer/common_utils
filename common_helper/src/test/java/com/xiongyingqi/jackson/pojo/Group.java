package com.xiongyingqi.jackson.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/6/4 0004.
 */
public class Group {
    @JsonIgnore
    private int id;
    @org.codehaus.jackson.annotate.JsonIgnore
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
