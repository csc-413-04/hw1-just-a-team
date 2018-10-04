package simpleserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.lang.Object;

class Response {


    private static String JSONallUsers = null;
    private static String JSONallPosts = null;

    private static Users[] allUs = null;
    private static Posts[] allPs = null;

    private static Boolean Found = false;

    public static void initializeUsers() {
        Gson gson = new Gson();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("../dataGenerator/data.json"));
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
            br = new BufferedReader(new FileReader("../dataGenerator/data.json"));
            JsonParser jsonParser = new JsonParser();
            JsonObject obj = jsonParser.parse(br).getAsJsonObject();

            Posts[] posts = gson.fromJson(obj.get("posts"), Posts[].class);
            allPs = posts;
            JSONallPosts = gson.toJson(posts);
            for (int i = 0; i < posts.length; i++) {
                posts[i] = new Posts(posts[i].getUserId(), posts[i].getPostId(), posts[i].getData());
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

    private static Boolean PostfindUid(int uid){
        Found = false;
        for (int i = 0; i < allPs.length; i++) {
            if (allPs[i].getUserId() == uid) {
                Found = true;
            }
        }
        return Found;
    }

    private static Boolean PostfindPid(int uid){
        Found = false;
        for (int i = 0; i < allPs.length; i++) {
            if (allPs[i].getPostId() == uid) {
                Found = true;
            }
        }
        return Found;
    }

    public Response(String url) {
    }

    public static String getBody(String fullAddress) {

        String[] urlParts = fullAddress.split(" /| |/|\\?|=|&");
        String endpoint = urlParts[1];
        String response = "";
        String statusOK = ",\"status\":\"OK\",";
        String statusError = "{\"status\": \"Error - Not Value\"}";
        String statusEntries = "\"entries\":";
        String statusEnd = "{\"data\":";
        String End = "}";
        Gson gson = new Gson();
        int li;
        int uid;


        if (endpoint.equalsIgnoreCase("user")) {
            if (urlParts[2].equalsIgnoreCase("userid")) {
                uid = Integer.parseInt(urlParts[3]);
                int count = 0;
                if (uid < allUs.length) {
                    ArrayList<Users> usersbyUID = new ArrayList<>();
                    for (int i = 0; i < allUs.length; i++) {
                        if (allUs[i].getUserid() == uid) {
                            usersbyUID.add(allUs[i]);
                            count++;
                        }
                    }
                    response = statusEnd + gson.toJson(usersbyUID) + statusOK + statusEntries + count + End;
                } else {
                    response = statusError;
                }
                return response;
            }
            response = String.valueOf(getUsers());
            response = statusEnd + response + statusOK + statusEntries + allUs.length + End;
            return response;
        }

        if (endpoint.equalsIgnoreCase("Posts")) {
            if (urlParts[2].equalsIgnoreCase("userid")) {
                uid = Integer.parseInt(urlParts[3]);
                int count = 0;
                PostfindUid(uid);
                if (Found == false) {
                    //response = gson.toJson("User " + uid + " is not exist");
                    response = statusError;
                } else {
                    ArrayList<Posts> usersbyUID = new ArrayList<>();
                    for (int i = 0; i < allPs.length; i++) {
                        if (allPs[i].getUserId() == uid) {
                            usersbyUID.add(allPs[i]);
                            count++;
                        }
                    }
                    System.out.println("entries: " + usersbyUID.size());
                    response = statusEnd + gson.toJson(usersbyUID) + statusOK + statusEntries + count + End;
                }
                return response;
            }
            else if (urlParts[2].equalsIgnoreCase("postid")) {
                uid = Integer.parseInt(urlParts[3]);
                PostfindPid(uid);
                if(Found == false){
                    response = statusError;
                } else {
                    int count = 0;
                    ArrayList<Posts> postsbyPID = new ArrayList<>();
                    if (urlParts.length > 6) {
                        if (urlParts[4].equalsIgnoreCase("maxlength")){
                            for (int i = 0; i < allPs.length; i++) {
                                if (allPs[i].getPostId() == uid) {
                                    String newData = "";
                                    if(Integer.parseInt(urlParts[5]) > allPs[i].getData().length()){
                                        newData = allPs[i].getData();
                                    }else{
                                        newData = allPs[i].getData().substring(0, Math.min(allPs[i].getData().length(), Integer.parseInt(urlParts[5])));
                                    }
                                    Posts newPost = new Posts( allPs[i].getUserId(),allPs[i].getPostId(), newData);
                                    postsbyPID.add(newPost);
                                    count++;
                                }
                            }
                            response = statusEnd + gson.toJson(postsbyPID) + statusOK + statusEntries + count + End;
                            return response;
                        }
                    }else{
                        for (int i = 0; i < allPs.length; i++) {
                            if (allPs[i].getPostId() == uid) {
                                postsbyPID.add(allPs[i]);
                                count++;
                            }
                        }
                    }
                    response = statusEnd + gson.toJson(postsbyPID) + statusOK + statusEntries + count + End;
                }
                return response;
            }
            else if (urlParts[2].equalsIgnoreCase("maxlength")) {
                uid = Integer.parseInt(urlParts[5]);
                PostfindPid(uid);
                if(Found == false){
                    response = statusError;
                } else {
                    int count = 0;
                    ArrayList<Posts> postsbyPID = new ArrayList<>();
                    if (urlParts.length > 6) {
                        if (urlParts[4].equalsIgnoreCase("postid")){
                            for (int i = 0; i < allPs.length; i++) {
                                if (allPs[i].getPostId() == uid) {
                                    String newData = "";
                                    if(Integer.parseInt(urlParts[5]) > allPs[i].getData().length()){
                                        newData = allPs[i].getData();
                                    }else{
                                    newData = allPs[i].getData().substring(0, Math.min(allPs[i].getData().length(), Integer.parseInt(urlParts[5])));
                                    }
                                    Posts newPost = new Posts( allPs[i].getUserId(),allPs[i].getPostId(), newData);
                                    postsbyPID.add(newPost);
                                    count++;
                                }
                            }
                            response = statusEnd + gson.toJson(postsbyPID) + statusOK + statusEntries + count + End;
                            return response;
                        }
                    }else{
                        for (int i = 0; i < allPs.length; i++) {
                            if (allPs[i].getPostId() == uid) {
                                postsbyPID.add(allPs[i]);
                                count++;
                            }
                        }
                    }
                    response = statusEnd + gson.toJson(postsbyPID) + statusOK + statusEntries + count + End;
                }
                return response ;
            }
            response = String.valueOf(getPosts());
            response = statusEnd + response + statusOK + statusEntries + allUs.length + End;
            return response;
        }
        //return endpoint;
        return response;
    }
}
