package tcpclient;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets; 

public class TCPClient {

private static int BUFFERSIZE = 1024; 

    public static String askServer(String hostname, int port, String ToServer) throws  IOException {
       //if we dont have an ToServer argument we jump to next method 
        if(ToServer == null)   
        return askServer(hostname, port);

        // Pre-allocate byte buffers for reading/receiving 
       //encode to bytes, data that we write from keyboard
        int timeOut = 3000;
        byte [] fromUserBuffer = ToServer.getBytes(StandardCharsets.UTF_8); 
        byte [] fromServerBuffer = new byte[BUFFERSIZE]; 
        StringBuilder stringBuilder = new StringBuilder(); 
        //int fromServerLength = 1;

        //create socket 
        Socket clientSocket = new Socket(hostname, port);

        OutputStream output = clientSocket.getOutputStream();
        output.write(fromUserBuffer);

        InputStream inputSocket = clientSocket.getInputStream();

        //socket.setSoTimeout(2000); //Timeout if answer takes to long

        long durationTime = System.currentTimeMillis();
        
        long stTime = System.currentTimeMillis();

               do 
             {
                int fromServerLength = inputSocket.read(fromServerBuffer);
                stringBuilder.append(new String(fromServerBuffer, 0, fromServerLength, StandardCharsets.UTF_8));
             } 
            
            while 
            (inputSocket.available() > 0 && (timeOut > (stTime-durationTime))); //while loop for att skicka ut mer än som finns plats på arrayen.


        clientSocket.close();
        return stringBuilder.toString();

    }   


    public static String askServer(String hostname, int port) throws  IOException {
       
        byte[] fromServerBuffer = new byte[BUFFERSIZE];  //allocate byte buffers

        StringBuilder stringBuilder = new StringBuilder();
        int timeOut = 3000;
        
        Socket clientSocket = new Socket(hostname, port); //create socket 

        //socket.setSoTimeout(2000); //Timeout if answer takes to long

        InputStream inputSocket = clientSocket.getInputStream();
        
        long durationTime = System.currentTimeMillis();

        long stTime = System.currentTimeMillis();

        do 
        {

            int fromServerLength = inputSocket.read(fromServerBuffer);
            stringBuilder.append(new String(fromServerBuffer, 0, fromServerLength, StandardCharsets.UTF_8));
            durationTime = System.currentTimeMillis();

          } 
        while (inputSocket.available() > 0 && (timeOut > (stTime-durationTime)));

        clientSocket.close(); 
        return stringBuilder.toString();
        
    }
}
