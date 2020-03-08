package com.adotsuarez.core;

/**
 * @author adotsuarez
 * (C) Agustin Suarez Martinez, 2020 - adot.c1.biz
 */
public class Code {
    private String data;
    private String title;
    private String comment;
    private Tag tag;
    private boolean fav;
    private Code next;
    private Code prev;

    // Tags
    public static enum Tag {
        DEFAULT("Default"),
        CYAN("Cyan"),
        PURPLE("Purple"),
        GREEN("Green");

        private String label;

        private Tag(String label) {
            this.label = label;
        }
    }

    // Constructor given all params
    public Code(String data, String title, String comment, Tag tag, boolean fav, Code next, Code prev) {
        this.data = data;
        this.title = title;
        this.comment = comment;
        this.tag = tag;
        this.fav = fav;
        this.next = next;
        this.prev = prev;
    }

    // Constructor for empty info
    public Code() {
        this.data = "";
        this.title = "";
        this.comment = "";
        this.tag = Tag.DEFAULT;
        this.fav = false;
        this.next = null;
        this.prev = null;
    }

    // Getters and Setters
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public Code getNext() {
        return next;
    }

    public void setNext(Code next) {
        this.next = next;
    }

    public Code getPrev() {
        return prev;
    }

    public void setPrev(Code prev) {
        this.prev = prev;
    }
}
