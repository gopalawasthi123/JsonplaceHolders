package com.example.gopalawasthi.jsonplaceholders;

/**
 * Created by Gopal Awasthi on 11-03-2018.
 */

public class posts {
   private int user_id;
   private int id;
   private String post;

    public posts(int user_id, int id, String post) {
        this.user_id = user_id;
        this.id = id;
        this.post = post;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
