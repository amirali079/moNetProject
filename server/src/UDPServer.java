import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class UDPServer {
    private static final Integer PORT = 5050;
    private final String group = "230.0.0.0";
    private DatagramSocket datagramSocket;
    private final ArrayList<DatagramPacket> receivedPackets = new ArrayList<>();
    private final ArrayList<Thread> threads = new ArrayList<>();
    private static AtomicInteger voiceReceivedCount = new AtomicInteger(Main.SENDER_COUNT);

    public void allVoiceReceived() {
        if (voiceReceivedCount.get() != 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            allVoiceReceived();
        }
    }

    public void startUDPServer() {
        try {
            datagramSocket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    public void createThreads() {
        Thread thread;
        for (int i = 0; i < Main.SENDER_COUNT; i++) {
            thread = new Thread(this::listenToClients);
            threads.add(thread);
            thread.start();
        }
    }

    public void listenToClients() {
        byte[] bytes = new byte[Main.FILE_SIZE * 1024];
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
        receivedPackets.add(packet);

        try {
            datagramSocket.receive(packet);
//            receivedPackets.forEach(packet1 -> System.out.println(packet1.getData()));
//            System.out.println(Thread.currentThread().getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        voiceReceivedCount.decrementAndGet();

    }

    public void integrateVoices() {
        System.out.println(receivedPackets.size());
//        printFiles();
        ByteBuffer byteBuffer;
        for (int i = 0; i < Main.FILE_SIZE * 1024; i++) {
            byteBuffer = ByteBuffer.allocate(Main.SENDER_COUNT);
            for (int j = 0; j < receivedPackets.size(); j++) {
                byteBuffer.put(receivedPackets.get(j).getData()[i]);
            }
            publishVoices(byteBuffer.array());
        }
        System.out.println("data sent");
//        receivedPackets.forEach(packet -> byteBuffer.put(packet.getData()));
    }

    public void publishVoices(byte[] buffer) {
        try {
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(group), 10000);
            datagramSocket.send(datagramPacket);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printFiles() {

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("fileee.json");
            outputStream.write(receivedPackets.get(0).getData());
            outputStream = new FileOutputStream("fileii.json");
            outputStream.write(receivedPackets.get(1).getData());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
