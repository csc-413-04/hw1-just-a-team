package simpleserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class SimpleServer {

    public static void main(String[] args) throws IOException {

        Response.initializeUsers();
        Response.initializePosts();
        String urlLink = null;

        ServerSocket ding;
        Socket dong = null;
        String resource = null;
        int setPort = 1299;
        try {
            ding = new ServerSocket(setPort);
            System.out.println("Opened socket " + setPort);
            while (true) {

                // keeps listening for new clients, one at a time
                try {
                    dong = ding.accept(); // waits for client here
                } catch (IOException e) {
                    System.out.println("Error opening socket");
                    System.exit(1);
                }

                InputStream stream = dong.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(stream));

                try {

                    // read the first line to get the request method, URI and HTTP version
                    String line = in.readLine();
                    urlLink = line;
                    System.out.println("----------REQUEST START---------");
                    System.out.println(line);
                    // read only headers
                    line = in.readLine();
                    while (line != null && line.trim().length() > 0) {
                        int index = line.indexOf(": ");
                        if (index > 0) {
                            System.out.println(line);
                        } else {
                            break;
                        }
                        line = in.readLine();
                    }
                    System.out.println("----------REQUEST END---------\n\n");
                } catch (IOException e) {
                    System.out.println("Error reading");
                    System.exit(1);
                }

                BufferedOutputStream out = new BufferedOutputStream(dong.getOutputStream());
                PrintWriter writer = new PrintWriter(out, true);  // char output to the client

                // every response will always have the status-line, date, and server name
                writer.println("HTTP/1.1 200 OK");
                writer.println("Server: TEST");
                writer.println("Connection: close");
                writer.println("Content-type: text/html");
                writer.println("");

                writer.println("<style type=\"text/css\">.tab { margin-left: 40px; }</style>");
                writer.println("<style type=\"text/css\">.tab2 { margin-left: 80px; }</style>");
                writer.println("<style type=\"text/css\">.tab3 { margin-left: 120px; }</style>");

                // Body of our response
                writer.println("<h2>HW1-Rest-API </h2>");
                writer.println("<h2> Group: Just-a-team </h2>");
                writer.println("<h4> Name: Tuan Le </h4>");
                writer.println("<p>Server: TEST</p>");
                writer.println("<p>Port: " + setPort + "</p>");

                writer.println(Response.getBody(urlLink));
                System.out.println("SimpleServer: " + Response.getBody(urlLink));
                writer.println(htmlFormat(Response.getBody(urlLink)));
                dong.close();
            }
        } catch (IOException e) {
            System.out.println("Error opening socket");
            System.exit(1);
        }
    }

    public static String htmlFormat(String str){
        str = "<p>" + str;
        int li = str.lastIndexOf("[");
        System.out.println("[ " + li);
        String temp1 = str.substring(0,li+1);
        String temp2 = str.substring(li+1, str.length());
        System.out.println("temp1:  " + temp1);
        System.out.println("temp2:  " + temp2);

        temp1 = temp1.replace(",", ",</p><p  class=\"tab\">");
        temp1 = temp1.replace("{", "{</p><p  class=\"tab\">");
        temp1 = temp1 +"</p>";

        temp2 = "<p  class=\"tab2\">"+temp2 ;
        temp2 = temp2.replace("{", "{</p><p  class=\"tab3\">");
        temp2 = temp2.replace(",\"", ",</p><p  class=\"tab3\">\"");
        temp2 = temp2.replace("},{", "</p><p  class=\"tab3\">},</p><p  class=\"tab3\">{</p>");
        temp2 = temp2.replace("}]", "</p><p  class=\"tab3\">}]");
        temp2 = temp2.replace("]", "</p><p  class=\"tab2\">]");
        temp2 = temp2 + "</p><p>}</p>";
        str = temp1 + temp2;
        return str;

    }
}
