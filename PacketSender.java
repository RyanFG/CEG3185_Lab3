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
        String all = "";
        String hexa = toHexa(msg);
        String datagram = "";
        String header = "4500";
        Double num = 20.0+(hexa.length()/2);
        String headerIP = toHexa(Integer.toString((int)Math.floor(num)));
        String ident = "1C46";
        String flag = "4000";
        String ttl = "4006";
        String checksum = "0000";
        String srcIP = toHexa("192.168.0.3");
        String dstIP = toHexa("192.168.0.1");

        String sum = hexaAdd(header,headerIP,ident,flag,ttl,checksum,srcIP,dstIP);

        datagram = header+headerIP+ident+flag+ttl+checksum+srcIP+dstIP;
        all = datagram+hexa;

        return all;
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

    public String hexaAdd(String header,String headerIP,String ident,String flag,String ttl,String checksum,String srcIP,String dstIP){
        String src1 = srcIP.substring(0,3);
        String src2 = srcIP.substring(4,7);
        String dst1 = dstIP.substring(0,3);
        String dst2 = dstIP.substring(4,7);

        String sum = "";
        return sum;
    }

    /*
    public void DatagramIP(byte [] buffer, int length){
        DatagramSocket s = null;
        try{
            s = new DatagramSocket();
            byte buffer;
            buffer = new String ("Send me a datagram").getBytes ();
            InetAddress ip = InetAddress.get("192.168.0.3");
            
            dgrm = new DatagramIP ();
            
            s.send(dgrm);
            
            
            byte [] buffer2 = new byte [0000];
            dgrm = new DatagramIP (buffer2, buffer.length, ip, portNum);
            s.receive (dgrm);
            
            System.out.println (new String (dgrm.getData ()));
		}
		catch (IOException e){
			System.out.println (e.toString ());
		}
		finally{
			if (s != null)
			    s.close ();
		}
	}

    public void ChecksumSender(InetAddress ip,int port)throws IOException {
        c = new Socket(ip,port);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        while (true){
        int i, l, sum = 0, numb;
        l = data.length;
        int data[] = new string [MAX];
		int c_data[] = new string [MAX];
		System.out.println("data to send");
		 
		 
		for (i = 0; i < l; i++){
			data[i] = sc.nextInt();
			numb = (int)(Math.floor(Math.log(data[i]) / Math.log(2))) + 1;
			 
			c_data[i] = ((1 << numb) - 1) ^ data[i];
			 
			sum += c_data[i];
		}
		 
		data[i] = sum;
        l += 1;
         
        System.out.println("Checksum Calculated is : " + sum);
        System.out.println("Data being sent along with Checksum.....");
		
		if (dis.readUTF().equals("failure")){
			System.out.println("Message was not received successfully!");
			break;
		}
		else if (dis.readUTF().equals("success")){
			System.out.println("Message received Successfully!");
			break;			
        }
	}
	output.close();
	input.close();
	socket.close();
	  
    }
    */

    public static void main(String[] args){
        PacketSender sender = new PacketSender(args[0],Integer.parseInt(args[1]));
    }
}

/*

import java.io.*;
import java.net.*;
import java.util.*;

public class PacketSender { 

	private Socket MyClient;
	private BufferedReader input;
	private DataOutputStream output;

	
	public PacketSender(InetAddress address, int port){ 

		try
		{ 
			
			MyClient = new Socket(address, port); 
			System.out.println("Connected"); 

			
			input = new BufferedReader(new InputStreamReader(System.in));

			
			output = new DataOutputStream(MyClient.getOutputStream()); 
		} 
		catch(UnknownHostException e) 
		{ 
			System.out.println(e); 
		} 
		catch(IOException e) 
		{ 
			System.out.println(e); 
		} 

		
		String line = ""; 

		
		while (!line.equals("Stop")) 
		{ 
			try
			{ 
				line = input.readLine();
				
				output.writeUTF(line); 
			} 
			catch(IOException i) 
			{ 
				System.out.println(i); 
			} 
		} 
		try
		{ 
			input.close(); 
			output.close(); 
			MyClient.close(); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 
	} 
	
	public void DatagramSender(byte [] buf, int length, InetAddress address, int port){
		try{
		 DatagramSocket dsck = new DatagramSocket ();
		 String msg = new String();
		 address = InetAddress.getLocalHost();
		 DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), address, port);
		 dsck.send(packet);
		 dsck.close();
		}
		catch(SocketException e) 
			{ 
				System.out.println(e); 
			}
		catch (UnknownHostException e) {
			System.out.println(e); 
		}
		catch(IOException e) 
		{ 
			System.out.println(e); 
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
	public String checkSumSender(String s) {
		
        int checkSumReceiver = checkSumReceiver(s);
        checkSumReceiver = checkComplement(checkSumReceiver);
        int m = checkSumReceiver + checkSum;
        m = checkComplement(m);
        
        if (m == 0) {
            System.out.println("Data received from"+ address + "is" + msg);
        } else {
            System.out.println("The verification of the checksum demonstrates that the packet received is correct.");
        }
    }
}
*/
