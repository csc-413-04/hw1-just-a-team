*data.json file generated by dataGenerator needs to be set in String "Location" in  Response.java<br/>
*the last checker for dataGenerator grader is bugged. Sometimes it cannot detect postid(exsiting data) from data.json.<br/>

*String array<br/>
String FullAddress = "GET /posts?maxlength=13&postid=38 HTTP/1.1";<br/>
String[] urlParts = [GET posts maxlength 13 postid 38 HTTP 1.1];<br/>

urlParts[0] = "GET";<br/>
urlParts[1] = "posts";<br/>
urlParts[2] = "maxlength";<br/>
urlParts[3] = "13";<br/>
urlParts[4] = "postid";<br/>
urlParts[5] = "38";<br/>
urlParts[6] = "HTTP";<br/>
urlParts[7] = "1.1";
