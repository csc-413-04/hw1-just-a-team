package simpleserver;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private final static Map<Integer, Posts> postidMap = new HashMap<>();

    private final int postid;
    private final int userid;
    private final String data;


    public Posts(int pid, int uid, String d) {
        this.data = d;
        this.postid = pid;
        this.userid = uid;

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
