package com.hfc.springboot.entity;

/**
 * Created by hfc on 2021/4/16.
 */
public class ItemList {

    public Integer id;

    public String item;

    public String item_id;

    public Integer item_level;

    public String item_pre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public Integer getItem_level() {
        return item_level;
    }

    public void setItem_level(Integer item_level) {
        this.item_level = item_level;
    }

    public String getItem_pre() {
        return item_pre;
    }

    public void setItem_pre(String item_pre) {
        this.item_pre = item_pre;
    }

}
