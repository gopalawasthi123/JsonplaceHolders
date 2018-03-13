package com.example.gopalawasthi.jsonplaceholders;

/**
 * Created by Gopal Awasthi on 13-03-2018.
 */

public class Comments {

    private String comment_name;
    private int Post_id;
    private  int id;

    public Comments(String comment_name, int post_id, int id) {
        this.comment_name = comment_name;
        Post_id = post_id;
        this.id = id;
    }

    public String getComment_name() {
        return comment_name;
    }

    public void setComment_name(String comment_name) {
        this.comment_name = comment_name;
    }

    public int getPost_id() {
        return Post_id;
    }

    public void setPost_id(int post_id) {
        Post_id = post_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
