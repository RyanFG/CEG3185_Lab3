import java.io.*;
import java.net.*;

public class PacketReceiver{
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream input = null;
 

    public PacketReceiver(int portNum){

        try{
            server = new ServerSocket(portNum);
            System.out.println("Server started");
 
            System.out.println("Waiting for a client ...");
 
            socket = server.accept();
            System.out.println("Client accepted");
 
            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
 
            String line = "";
            String decoded = "";

            while (true){

                try{
                    line = input.readUTF();

                    decoded = decoder(line);
                    if(decoded.equals("Over")){
                        break;
                    }

                    System.out.println(decoded);
 
                }
                catch(IOException i){
                    System.out.println(i);
                }
                socket.close();
                input.close();
            }
        }
        catch (IOException e){
            System.out.println(e);
        }

    }

    public String decoder(String msg){
        String text = toString(msg.substring(36));
        String header = msg.substring(0,3);
        String headerIP = msg.substring(4,7);
        String ident = msg.substring(8,11);
        String flag = msg.substring(12,15);
        String ttl = msg.substring(16,19);
        String checksum = msg.substring(20,23);
        String srcHex1 = msg.substring(24,27);
        String srcHex2 = msg.substring(28,31);
        String dstHex1 = msg.substring(32,35);
        String dstHex2 = msg.substring(36,39);

        Boolean sum = hexaAdd(Integer.parseInt(header,16),Integer.parseInt(headerIP,16),Integer.parseInt(ident,16),Integer.parseInt(flag,16),
        Integer.parseInt(ttl,16),Integer.parseInt(checksum,16),Integer.parseInt(srcHex1,16),Integer.parseInt(srcHex2,16),
        Integer.parseInt(dstHex1,16),Integer.parseInt(dstHex2,16));

        if(!sum){
            return("An error has occured during transmission.");
        }

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

    public Boolean hexaAdd(int header,int headerIP,int ident,int flag,int ttl,int checksum,int srcIP1,int srcIP2, int dstIP1, int dstIP2){
        int sum = header+headerIP+ident+ttl+checksum+srcIP1+srcIP2+dstIP1+dstIP2;

        if(sum-65535 != 0){
            return false;
        }

        return true;
    }


    public static void main(String[] args){
        PacketReceiver receiver = new PacketReceiver(Integer.parseInt(args[0]));
    }

    /*
	public String checkSumReceiver(String s) {
        int checksum = 0;
		String y = decoder(checkSumSend);

        if (((header.length())+(headerIP.length())) % 8 == 0) {
            headerIP = headerIP + "0";
		    decoder(headerIP);
	    }else { 
		    decoder(headerIP);
	    }

        checkSum += y;

	    String hexString = Integer.toHexString(checkSum);

        if (y.length() > 4) {
            int carry = Integer.parseInt(("" + y.charAt(0)), 16);
            y += carry;
        }
		int complement = Integer.parseInt("FFFF", 16) - y;
		int checkSumReceived = complement;

        return (Integer.toString(checkSumReceived)); 
    }*/


    /*
     * public PacketReceiver(int port){ 
		try{ 
		
			MyServer = new ServerSocket(port); 
			//System.out.println("Server started"); 

			//System.out.println("Waiting for a client ..."); 

	
			socket = MyServer.accept(); 
			System.out.println("Client accepted"); 

			
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream())); 

			String line = ""; 

			while (!line.equals("Stop")) 
			{ 
				try
				{ 
					line = in.readUTF(); 
					System.out.println(line); 

				} 
				catch(IOException i) 
				{ 
					System.out.println(i); 
				} 
			} 
			System.out.println("Closing connection"); 

	
			socket.close(); 
			in.close(); 
		} 
		catch(IOException e) 
		{ 
			System.out.println(e); 
		} 
	} 
     */

}

	
	
	
