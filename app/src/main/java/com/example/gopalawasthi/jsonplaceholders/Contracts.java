package com.example.gopalawasthi.jsonplaceholders;

/**
 * Created by Gopal Awasthi on 13-03-2018.
 */

public class Contracts {

    public static final String DATABASE_NAME = "datawall";
    public static final int ITEM_VERSION = 1;

    static class UserdataBase{
        public static final String TABLE_NAME = "usertable";
        public static final String USER_NAME = "name";
        public static final String USER_ID = "id";

    }
    static class Posts{
        public static final String TABLE_NAME = "posttable";
        public static final String POST = "post";
        public static  final String POST_ID = "id";
        public static final String USER_ID = "user_id";
    }
}
