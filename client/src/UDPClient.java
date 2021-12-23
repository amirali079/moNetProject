import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient {
    private DatagramSocket datagramSocket;

    public void init(int port) {
        try {
            datagramSocket = new DatagramSocket(port);
            new Thread(this::listenToFeedBack).start();
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
            Thread.sleep(15000);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void listenToFeedBack() {
        byte[] buffer = new byte[25];
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        try {
            datagramSocket.receive(datagramPacket);
            System.out.println(new String(datagramPacket.getData()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
