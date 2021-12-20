import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;

public class MulticastReceiver{
    private static final int PORT = 10000;
    private static final String MULTICAST_ADDRESS = "230.0.0.0";

    protected MulticastSocket socket = null;
    protected byte[] singlePartBuffer;
    private ByteBuffer buffer = ByteBuffer.allocate(Listener.senderCount * Main.fileSize * 1024);

    public void listen() {

        try {
            socket = new MulticastSocket(PORT);
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            socket.joinGroup(group);
            for (int i = 0; i < Main.fileSize * 1024; i++)
             {
                 System.out.println(i);
                singlePartBuffer = new byte[Listener.senderCount];
                DatagramPacket packet = new DatagramPacket(singlePartBuffer, singlePartBuffer.length);
                socket.receive(packet);
                buffer.put(singlePartBuffer);
            }

            writeResult(buffer.array());
            socket.leaveGroup(group);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeResult(byte[] bytes) {
        try {
            File file = new File(Main.RECEIVED_FILE_NAME);
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


