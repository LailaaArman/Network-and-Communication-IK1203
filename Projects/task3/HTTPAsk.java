import java.net.*;
import java.io.*;
import tcpclient.TCPClient;


public class HTTPAsk {

    static int BUFFERSIZE=1024;

    public static void main( String[] args) throws IOException, NumberFormatException {
        
        ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(args[0]));

        int port = 0;
        String toServer = null;
        String hostname = null;
        
        String header = ("HTTP/1.1 200 OK \r\n\r\n");
        String notFound = ("HTTP/1.1 404 Not Found\r\n\r\n");
        String badRequest = ("HTTP/1.1 400 Bad Request\r\n\r\n");
        

        while (true) {
           
            Socket socket = welcomeSocket.accept();
            byte[] fromClientBuffer = new byte[BUFFERSIZE];
            int fromClientLength = socket.getInputStream().read(fromClientBuffer);

            StringBuilder stringBuilder = new StringBuilder();
            
                for (int i = 0; i < fromClientLength; i++) {
                    stringBuilder.append((char) fromClientBuffer[i]);
                }

            String sb = stringBuilder.toString();

            char[] charArray = sb.toCharArray();

                for (int i = 0; i < charArray.length-1; i++) {
                    if (charArray[i] == '?' && charArray[i-1] != '?' && charArray[i+1] != '?') {
                        charArray[i] = ' ';
                    }
                    if (charArray[i] == '&' && charArray[i-1] != '&' && charArray[i+1] != '&') {
                        charArray[i] = ' ';
                    }
                    if (charArray[i] == '=' && charArray[i-1] != '=' && charArray[i+1] != '=') {
                        charArray[i] = ' ';
                    }
                    

                }
                sb = new String(charArray);

                if (sb.contains("ask?")) {
                    sb = sb.replace("ask?","ask ");
                }

                if (sb.contains("hostname=")) {
                    sb =sb.replace("hostname=","hostname ");
                }

                if(sb.contains("&port=")) {
                    sb = sb.replace("&port=", " port ");
                }
                
                if(sb.contains("&String=")) {
                    sb = sb.replace("&String="," String ");
                }


            String[] splitArray = sb.split("[\\s+]");

            
            for(int i = 0; i < splitArray.length-1; i++)
            {
                System.out.println("STRING number " + i + " " + splitArray[i]);
            }

            for(int i = 0; i < splitArray.length-1; i++)
            {

                if(splitArray[i].equals("/favicon.ico")) {
                    break;
                }

                if(splitArray[i].equals("string")) {
                    toServer = splitArray[i+1].replace("%20", " ");

                }
                if(splitArray[i].equals("port")) {
                    port = Integer.parseInt(splitArray[i+1]);
                }

                if(splitArray[i].equals("hostname") && !splitArray[i-1].contains("?")) {
                    hostname = splitArray[i+1];
                }
                
            
                
            }

            if(hostname != null && port != 0) {
                try {
                    sb = TCPClient.askServer(hostname, port, toServer);
                    socket.getOutputStream().write(header.getBytes());
                    socket.getOutputStream().write(sb.getBytes());
                }
                catch (Exception e) {
                    socket.getOutputStream().write(notFound.getBytes());
                }
            }
            else {
                socket.getOutputStream().write(badRequest.getBytes());
            }

            socket.close();

        }
    }
}