package simpleserver;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

class Response {


    private static String JSONallUsers = null;
    private static String JSONallPosts = null;

    private static Users[] allUs = null;
    private static Posts[] allPs = null;

    public static void initializeUsers() {
        Gson gson = new Gson();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("src/simpleserver/Data.json"));
            JsonParser jsonParser = new JsonParser();
            JsonObject obj = jsonParser.parse(br).getAsJsonObject();

            Users[] users = gson.fromJson(obj.get("users"), Users[].class);
            allUs = users;
            JSONallUsers = gson.toJson(users);
            for (int i = 0; i < users.length; i++) {
                users[i] = new Users(users[i].getUserid(), users[i].getUsername());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void initializePosts() {
        Gson gson = new Gson();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("src/simpleserver/Data.json"));
            JsonParser jsonParser = new JsonParser();
            JsonObject obj = jsonParser.parse(br).getAsJsonObject();

            Posts[] posts = gson.fromJson(obj.get("posts"), Posts[].class);
            allPs = posts;
            JSONallPosts = gson.toJson(posts);
            for (int i = 0; i < posts.length; i++) {
                posts[i] = new Posts(posts[i].getPostId(), posts[i].getUserId(), posts[i].getData());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static String getUsers() {
        return JSONallUsers;
    }

    private static String getPosts() {
        return JSONallPosts;
    }


    public Response(String url) {
    }

    public static String getBody(String fullAddress) {

        String[] urlParts = fullAddress.split(" ");
        String parsedUrl = urlParts[1];
        String response = "";
        String statusOK = "{\"status: \"\"OK\", ";
        String statusError = "{\"status: \"\"Error\"} ";
        String statusEntries = "\"entries\" : ";
        String statusEnd = ", \"data\": ";
        Gson gson = new Gson();
        int li;
        int uid;


        if (parsedUrl.equals("/user")) {
            response = String.valueOf(getUsers());
            //System.out.println(response);
            //System.out.println();
            response = statusOK + statusEntries + allUs.length + statusEnd + response;
            return response;
        }
        if (parsedUrl.equals("/User")) {
            response = String.valueOf(getUsers());
            return response;
        }
        if (parsedUrl.startsWith("/user?userid=")) {
            li = parsedUrl.lastIndexOf("=");
            uid = Integer.valueOf(parsedUrl.substring(li + 1));
            //System.out.println("test="+uid);
            if (uid < allUs.length) {
                ArrayList<Users> usersbyUID = new ArrayList<>();
                for (int i = 0; i < allUs.length; i++) {
                    if (allUs[i].getUserid() == uid) {
                        usersbyUID.add(allUs[i]);
                    }
                }
                response = gson.toJson(usersbyUID);
            } else {
                //response = gson.toJson();
            }
            return response;
        }


        if (parsedUrl.equals("/Posts")) {
            response = String.valueOf(getPosts());
            return response;
        }
        if (parsedUrl.equals("/posts")) {
            response = String.valueOf(getPosts());
            return response;
        }

        if (parsedUrl.startsWith("/posts?userid=")) {
            li = parsedUrl.lastIndexOf("=");
            uid = Integer.valueOf(parsedUrl.substring(li + 1));
            //System.out.println("test="+uid);
            if (uid >= allUs.length) {
                //response = gson.toJson("User " + uid + " is not exist");
            } else {
                ArrayList<Posts> postsbyUID = new ArrayList<>();
                for (int i = 0; i < allPs.length; i++) {
                    if (allPs[i].getUserId() == uid) {
                        postsbyUID.add(allPs[i]);
                    }
                }
                System.out.println("entries: " + postsbyUID.size());
                response = gson.toJson(postsbyUID);
            }
            return response;
        }

        if (parsedUrl.startsWith("/posts?postid=")) {
            int li_check = parsedUrl.lastIndexOf("&");
            System.out.println(li_check);
            if (li_check == -1) {
                li = parsedUrl.lastIndexOf("=");
                //System.out.println(li);
                uid = Integer.valueOf(parsedUrl.substring(li + 1));
                //System.out.println(uid);
                //System.out.println("test="+uid);

                ArrayList<Posts> postsbyUID = new ArrayList<>();
                for (int i = 0; i < allPs.length; i++) {
                    if (allPs[i].getPostId() == uid) {
                        postsbyUID.add(allPs[i]);
                    }
                }
                System.out.println("entries: " + postsbyUID.size());
                response = gson.toJson(postsbyUID);
                return response;
            } else {

            }
        }


        return parsedUrl;
    }
}
