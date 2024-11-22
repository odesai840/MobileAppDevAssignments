package edu.uncc.posts.models;

import java.io.Serializable;
import java.util.ArrayList;

public class PostsResponse implements Serializable {

    String pageSize;
    String totalCount;

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    ArrayList<Post> posts;
}
