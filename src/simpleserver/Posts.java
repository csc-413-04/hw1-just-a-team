package simpleserver;

import java.util.HashMap;
import java.util.Map;

public class Posts {

    private final static Map<Integer, Posts> postidMap = new HashMap<>();

    private final int userid;
    private final int postid;
    private final String data;


    public Posts(int uid, int pid, String d) {
        this.data = d;
        this.userid = uid;
        this.postid = pid;

        postidMap.put(postid, this);
    }

    public int getPostId() {
        return postid;
    }

    public int getUserId() {
        return userid;
    }

    public String getData() {
        return data;
    }
}
