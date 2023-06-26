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
            String decoded = "";

            while (true)
            {
                try{
                    line = input.readUTF();

                    decoded = decoder(line);
                    if(decoder(line.equals("Over"))){
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


    public static void main(String[] args){
        PacketReceiver receiver = new PacketReceiver(Integer.parseInt(args[0]));
    }
}
/*

import java.io.*;
import java.net.*;
import java.util.*;

public class PacketReceiver{ 

	//initialize socket and input stream 
	private Socket socket;
	private ServerSocket MyServer;
	private DataInputStream in;

	

	public PacketReceiver(int port){ 
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
	public String checkSumReceiver(String s) {
        String hexString = new String();
        int x, i, checkSum = 0;
        decoder(hexString);
        x = Integer.parseInt(toString(hexString));

        checkSum += x;

        if (hexString.length() > 8) {
            int carry = Integer.parseInt(("" + hexString.charAt(0)), 16);

            checkSum += carry;
        }
        
        checkSum = checkComplement(checkSum);

        
        return (Integer.toString(checkSum)); 
    }
	public int checkComplement(int checkSum) {

        checkSum = Integer.parseInt("FFFF", 16) - checkSum;
        return checkSum;
    }
    public static void main(String args[]) { 
		 
	} 
}
*/
	
	
	
