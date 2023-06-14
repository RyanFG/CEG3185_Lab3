public PacketReceiver{
    ServerSocket MyService;

    public PacketReceiver(){
        try {
            MyServerice = new ServerSocket(PortNumber);
        }
        catch (IOException e) {
            System.out.println(e);
        }

    }
}