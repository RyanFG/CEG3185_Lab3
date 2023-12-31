import java.io.*;
import java.net.*;
import java.util.Random;

public class PacketSender{
    private Socket client = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
 
    public PacketSender(String address, int portNum){

        try {
            client = new Socket(address, portNum);
            System.out.println("Connected");
 
            input = new DataInputStream(System.in);
 
            output = new DataOutputStream(client.getOutputStream());
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

        while (!line.equals("Over")){
            try{
                line = input.readLine();

                encoded = encoder(address,line);

                output.writeUTF(encoded);
            }

            catch (IOException i) {
                System.out.println(i);
            }
        }
        try {
            input.close();
            output.close();
            client.close();
        }

        catch (IOException i) {
            System.out.println(i);
        }
    }

    public String encoder(String address,String msg){
        String all = "";
        String hexa = toHexa(msg);

        String datagram = "";
        String header = "4500";
        while(!((20+msg.length())%8 == 0)){
            msg = msg+"0";
        }
        int num = (int)Math.floor(20+(hexa.length()/2));
        String headerIP = Integer.toHexString(num);
        while(headerIP.length() < 4){headerIP = "0"+headerIP;}
        String ident = "1C46";
        String flag = "4000";
        String ttl = "4006";
        String checksum = "0000";

        //Converting source address to ints and then ints to 2-bit hexadecimal values
        //first 4-bits of source (Ex. 192 -> C0, 168 -> A3 = C0A3)
        String src1 = Integer.toHexString(Integer.parseInt(address.substring(0,address.indexOf("."))));
        if(src1.length() < 2){src1 = "0"+src1;}
        String address1 = address.replace(String.valueOf(src1)+".","");
        String src2 = Integer.toHexString(Integer.parseInt(address1.substring(0,address1.indexOf("."))));
        if(src2.length() < 2){src2 = "0"+src2;}
        String srcHex1 = src1+src2;

        //second 4-bits of source (Ex. 0 -> 00, 3 -> 03 = 0003)
        String address2 = address1.replace(String.valueOf(src2)+".","");
        String src3 = Integer.toHexString(Integer.parseInt(address2.substring(0,address2.indexOf("."))));
        if(src3.length() < 2){src3 = "0"+src3;}
        String address3 = address2.replace(String.valueOf(src3)+".","");
        String src4 = Integer.toHexString(Integer.parseInt(address3.substring(0,address3.indexOf("."))));
        if(src4.length() < 2){src4 = "0"+src4;}
        String srcHex2 = src3+src4;

        //create destination IP address
        Random random = new Random();
        String dst1 = Integer.toHexString((random.nextInt(256)));
        if(dst1.length() < 2){dst1 = "0"+dst1;}
        String dst2 = Integer.toHexString((random.nextInt(256)));
        if(dst2.length() < 2){dst2 = "0"+dst2;}
        String dstHex1 = dst1+dst2;

        String dst3 = Integer.toHexString((random.nextInt(256)));
        if(dst3.length() < 2){dst3 = "0"+dst3;}
        String dst4 = Integer.toHexString((random.nextInt(256)));
        if(dst4.length() < 2){dst4 = "0"+dst4;}
        String dstHex2 = dst3+dst4;


        String sum = hexaAdd(Integer.parseInt(header,16),Integer.parseInt(headerIP,16),Integer.parseInt(ident,16),Integer.parseInt(flag,16),
        Integer.parseInt(ttl,16),Integer.parseInt(checksum,16),Integer.parseInt(srcHex1,16),Integer.parseInt(srcHex2,16),
        Integer.parseInt(dstHex1,16),Integer.parseInt(dstHex2,16));

        datagram = header+headerIP+ident+flag+ttl+checksum+srcHex1+srcHex2+dstHex1+dstHex2;
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
        System.out.println(result);
        return result;
    }

    public String hexaAdd(int header,int headerIP,int ident,int flag,int ttl,int checksum,int srcIP1,int srcIP2, int dstIP1, int dstIP2){
        int sum = header+headerIP+ident+ttl+checksum+srcIP1+srcIP2+dstIP1+dstIP2;

        String hexString = Integer.toHexString(sum);

        while(hexString.length() > 4){
            int carry = Integer.parseInt(("" + hexString.charAt(0)), 16);
		    int calcedCheckSum = Integer.parseInt(hexString, 16);
            calcedCheckSum += carry;
            hexString = Integer.toHexString(calcedCheckSum);
        }
	
	    int complement = Integer.parseInt("FFFF", 16) - Integer.parseInt(hexString, 16);
	    int checkSumSend = complement;

        return (Integer.toString(checkSumSend));
    }

    public static void main(String[] args){
        PacketSender sender = new PacketSender(args[0],Integer.parseInt(args[1]));
    }
}




