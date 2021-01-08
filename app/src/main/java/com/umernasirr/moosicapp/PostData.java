package com.umernasirr.moosicapp;

import java.util.ArrayList;

public class PostData {


    public ArrayList<PostModel> postList;
    public static PostData postData;

    private PostData() {
        postList = new ArrayList<>();

        PostModel postModel1 = new PostModel("1", "Best Post 1", "This is the best post 1 ever!!", "test.com", "talal" ,"1");
        PostModel postModel2 = new PostModel("2", "Best Post 2", "This is the best post 2 ever!!", "test.com", "talal" ,"1");
        PostModel postModel3 = new PostModel("3", "Best Post 3", "This is the best post 3 ever!!", "test.com", "talal" ,"1");

        postList.add(postModel1);
        postList.add(postModel2);
        postList.add(postModel3);
    }

    public static PostData getInstance() {
        if (postData == null) {
            postData = new PostData();
        }
        return postData;
    }

}
