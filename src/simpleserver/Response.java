package simpleserver;

interface iData{}

class User implements iData{
    int userid;
}
class Post implements iData{
    int postid;
}

public class Response {
    public String status;
    public int entries;
    iData[] data;

    void doStuff(iData datatype){

    }
}

//    Separate file
//class userProcessor{
//    Response process(){
//        Response response = new Response();
//        // attach stuff to response
//
//        return response;
//    }
//}