public PacketSender{

    Socket MyClient;

    public PacketReceiver(){ 
        
        try {
            MyClient = new Socket("Machine name",
            PortNumber);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    } 
}