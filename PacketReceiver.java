import java.io.*;
import java.net.*;

public class PacketReceiver{

    private Socket socket = null;
    private ServerSocket MyServer = null;
    private DataInputStream input = null;


    public PacketReceiver(String address, int portNum){

        try{
            MyServer = new ServerSocket(portNum);

            socket = MyServer.accept();

            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            String line = "";
            String decoded = "";

            while (true)
            {
                try{
                    line = input.readUTF();

                    decoded = decoder(line.substring(54));
                    if(decoder(line.substring(54)).equals("over")){
                        break;
                    }

                    System.out.println(decoded);
 
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

    public String decoder(String msg){
        String text = toString(msg);
        return text;
    }

    public String toString(String str){
        String result = new String();
        char[] charArray = str.toCharArray();
        for(int i = 0; i < charArray.length; i=i+2) {
            String st = ""+charArray[i]+""+charArray[i+1];
            char ch = (char)Integer.parseInt(st, 16);
            result = result + ch;
        }
        return result;
    }

     /*
    public void DatagramIP(byte [] buffer, int length){
        DatagramSocket s = new DatagramSocket ();
        byte [] data = new byte [0000];
        DatagramPacket dgrm = new DatagramPacket (data, data.length);
        while (true){
            s.receive (dgrm);
            System.out.println (new String (data));
            s.send (dgrm);
        }
    }

    public void ChecksumReceiver(InetAddress ip,int port)throws IOException {
        c = new Socket(ip,port);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        while (true){
            int i, l, sum = 0, numb;
            l = data.length;
            int data[] = new string[l];
            int c_data[] = new string [l];
            System.out.println("Data received (along with checksum) is");
            
            for (i = 0; i < data.length; i++){
                data[i] = input.nextInt();
                System.out.println(data[i]);
                
                numb = (int)(Math.floor(Math.log(data[i]) / Math.log(2))) + 1;				 
                c_data[i] = ((1 << numb) - 1) ^ data[i];
                
                System.out.println("Calculated Checksum is : "+sum);
                
                if(sum == 0){
                    output.writeUTF("success");
                    break;
                }
                else{
                    output.writeUTF("failure");
                    break;
                }
                input.close();
                output.close();
                c.close();	
            }		
        }			 
    }
    */

    public static void main(String[] args){
        PacketReceiver receiver = new PacketReceiver(args[0],Integer.parseInt(args[1]));
    }
}