import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UDPServer {
    private static final Integer PORT = 5050;
    private DatagramSocket datagramSocket;
    private final List<DatagramPacket> receivedPackets = new ArrayList<>();
    private final List<Delay> delays = new ArrayList<>();
    private static final AtomicInteger voiceReceivedCount = new AtomicInteger(Main.SENDER_COUNT);

    public void allVoiceReceived() {
        if (voiceReceivedCount.get() != 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            allVoiceReceived();
        }
        calculateAbnormalDelays();
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
            thread.start();
        }
    }

    public void listenToClients() {

        byte[] bytes = new byte[Main.FILE_SIZE * 1024];
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
        receivedPackets.add(packet);

        try {
            long start = System.currentTimeMillis();
            datagramSocket.receive(packet);
            delays.add(new Delay(System.currentTimeMillis() - start, packet.getAddress().getHostAddress(), packet.getPort()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        voiceReceivedCount.decrementAndGet();
    }

    public void integrateVoices() {
//        printFiles();
        ByteBuffer byteBuffer;
        for (int i = 0; i < Main.FILE_SIZE * 1024; i++) {
            byteBuffer = ByteBuffer.allocate(Main.SENDER_COUNT);
            for (DatagramPacket receivedPacket : receivedPackets) {
                byteBuffer.put(receivedPacket.getData()[i]);
            }
            publishVoices(byteBuffer.array());
        }
        System.out.println("data sent");
    }

    public void publishVoices(byte[] buffer) {
        try {
            String group = "230.0.0.0";
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(group), 10000);
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printFiles() {

        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream("fileee.json");
            outputStream.write(receivedPackets.get(0).getData());
            outputStream = new FileOutputStream("fileii.json");
            outputStream.write(receivedPackets.get(1).getData());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void calculateAbnormalDelays() {
        long sum = 0L;
        for (Delay delay : delays)
            sum += delay.getDelay();
        long avg = sum / Main.SENDER_COUNT;

        for (Delay delay : delays)
            if (delay.getDelay() > avg + 10000)
                sendFeedback(delay);

    }

    private void sendFeedback(Delay delay) {
        byte[] bytes = "check your internet state".getBytes(StandardCharsets.UTF_8);
        try {
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length,
                    InetAddress.getByName(delay.getHostAddress()), delay.getHostPort());
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
