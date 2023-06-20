import java.io.*;
import java.net.*;

public class PacketSender{

    Socket MyClient = null;
    DataInputStream input = null;
    DataOutputStream output = null;

    public PacketSender(String address,int portNum){ 
        
        try{
            MyClient = new Socket(address,portNum);

            input = new DataInputStream(System.in);
            output = new DataOutputStream(MyClient.getOutputStream());
        }
        catch (UnknownHostException u) {
            System.out.println(u);
            return;
        }
        catch (IOException i) {
            System.out.println(i);
            return;
        }

        String line = "";
        String encoded = "";

        while (!line.equals("over")){
            try{
                line = input.readLine();

                encoded = encoder(line);

                output.writeUTF(encoded);
            }
            catch (IOException i) {
                System.out.println(i);
            }
        }
        try {
            input.close();
            output.close();
            MyClient.close();
        }
        catch (IOException i) {
            System.out.println(i);
        }
    }

    public String encoder(String msg){
        String hexa = toHexa(msg);
        return hexa;
    }

    public String toHexa(String str){
        StringBuffer sb = new StringBuffer();
        char ch[] = str.toCharArray();

        for(int i = 0; i < ch.length; i++) {
            String hexString = Integer.toHexString(ch[i]);
            sb.append(hexString);
        }

        String result = sb.toString();
        return result;
    }

    public static void main(String[] args){
        PacketSender sender = new PacketSender(args[0],Integer.parseInt(args[1]));
    }
}