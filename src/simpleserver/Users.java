package simpleserver;

import java.util.HashMap;
import java.util.Map;

public class Users {

    private final static Map<Integer, Users> useridMap = new HashMap<>();

    private final int userid;
    private final String username;

    public Users(int userid, String username) {
        this.username = username;
        this.userid = userid;

        useridMap.put(userid, this);
    }

    public int getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

}
