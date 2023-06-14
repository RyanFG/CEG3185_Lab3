import java.io.*;
import java.net.*;

public class PacketSender{

    Socket MyClient;

    public PacketReceiver(int portNum){ 
        
        try{
            MyClient = new Socket("IP Address",portNum);
        }
        catch (IOException e){
            System.out.println(e);
        }
    } 
}