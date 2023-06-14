import java.io.*;
import java.net.*;

public class PacketReceiver{
    ServerSocket MyService;

    public PacketReceiver(int portNum){

        try{
            MyServerice = new ServerSocket(portNum);
        }
        catch (IOException e){
            System.out.println(e);
        }

    }
}