import java.net.*;

public class ConcHTTPAsk {
    public static void main( String[] args) throws Exception {

        //Get number of the port, first argument and open the socket

        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));

              while(true)     {
            MyRunnable myRun = new MyRunnable(serverSocket.accept());

            Thread myThread = new Thread(myRun);

            myThread.start();
        }
    }
}


