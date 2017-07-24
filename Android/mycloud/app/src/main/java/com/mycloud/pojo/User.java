package com.mycloud.pojo;

/**
 * Created by clarechen on 2016/2/2.
 */
public class User extends BaseResult {
    public Data data;


    public class Data {
        public String id;
        public String username;
        public String password;
        public String nickname;
        public String area;
        public String phone;
        public String oauthkey;
        public String oauthtype;
        public String score;
        public String permission;
        public String age;
        public String sex;
    }

}
