
public class Main {
    public static final int SENDER_COUNT = 2;
    public static final int FILE_SIZE = 10;
    public static void main(String[] args) {

        UDPServer udpServer = new UDPServer();
        udpServer.startUDPServer();
        udpServer.createThreads();
        udpServer.allVoiceReceived();
        udpServer.integrateVoices();

    }
}

