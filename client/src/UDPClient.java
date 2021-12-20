import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient {
    DatagramSocket datagramSocket;

    public void init(int port){
        try {
            datagramSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendVoice() {

        File file = new File(Main.SENDING_FILE_NAME);
        byte[] bytes = new byte[Main.fileSize * 1024];
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
            inputStream.read(bytes);

            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, InetAddress.getByName("127.0.0.1"), 5050);
            datagramSocket.send(datagramPacket);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
