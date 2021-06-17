import java.net.*;
import java.io.*;

public class HTTPEcho {

    static int BUFFERSIZE = 1024;
    public static void main( String[] args) throws IOException {
        
        //open a serversocket with the port given by the user
        ServerSocket HTTPSocket = new ServerSocket(Integer.parseInt(args[0]));
       
        //Header to be appended first
        String header = ("HTTP/1.1 200 OK \r\n\r\n");

        while (true) {
            //Create socket and accept client
            Socket connection = HTTPSocket.accept();
            
            byte[] fromClientBuffer = new byte[BUFFERSIZE];
            int fromClientLength = connection.getInputStream().read(fromClientBuffer);

            StringBuilder Echo = new StringBuilder();  //create stringbuilder
                       
            for (int i = 0; i < fromClientLength; i++) {
                Echo.append((char)
                fromClientBuffer[i]);
            }

            String sb = Echo.toString(); 

            
            connection.getOutputStream().write(header.getBytes());
            connection.getOutputStream().write(sb.getBytes());

            //close socket 
            connection.close();

        }
    }
}

