public class Main {

    public static final String SENDING_FILE_NAME = "file1.json";
    public static final String RECEIVED_FILE_NAME = "result1.json";

    public static final int fileSize = 10;

    public static void main(String[] args) {

        UDPClient udpClient = new UDPClient();
        udpClient.init(5052);
        udpClient.sendVoice();

    }
}
