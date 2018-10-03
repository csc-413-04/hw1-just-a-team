package simpleserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class SimpleServer {
  static String[] arg;

  public static String[] Parse(String str){
    String[] split = str.split("/|\\?|=|&");
    return split;
  }

  public static void main(String[] args) throws IOException {
    ServerSocket ding;
    Socket dong = null;
    String resource = null;
    try {
      ding = new ServerSocket(1299);
      System.out.println("Opened socket " + 1299);

      // **Make Database class and load data
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
          arg = Parse(line);   //**Split string and get arg[1]
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
        writer.println("Content-type: text/html"); //** Content-type: application/json
        writer.println("");

        // Body of our response
        // ** put rest of logic here
        //  1. check if input (url) is valid or not!
        //  2. extract the query separated by '&'
        // then use factory
        // processor.process()
        writer.println("<h1>Some cool response!</h1>"); //**outputs (gson.toJson(response))

        dong.close();
      }
    } catch (IOException e) {
      System.out.println("Error opening socket");
      System.exit(1);
    }
  }
}
