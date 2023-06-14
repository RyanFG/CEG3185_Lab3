import java.io.*;
import java.net.*;

public class PacketSender{

    Socket MyClient;
    DataInputStream input;
    DataOutputStream output;

    public PacketSender(int portNum){ 
        
        try{
            MyClient = new Socket("IP Address",portNum);
        }
        catch (IOException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){
        PacketSender sender = new PacketSender(Integer.parseInt(args[0]));
    }
}