import java.io.*;
import java.net.*;

public class PacketReceiver{

    private Socket socket = null;
    private ServerSocket MyServer = null;
    private DataInputStream input = null;


    public PacketReceiver(int portNum){

        try{
            MyServer = new ServerSocket(portNum);

            socket = MyServer.accept();

            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            String line = "";

            while (!line.equals("over"))
            {
                try{
                    line = in.readUTF();
                    System.out.println(line);
 
                }
                catch(IOException i){
                    System.out.println(i);
                }
            }
        }
        catch (IOException e){
            System.out.println(e);
        }

    }

    public static void main(String[] args){
        PacketReceiver receiver = new PacketReceiver(Integer.parseInt(args[0]));
    }
}