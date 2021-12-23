public class Listener {
    public static final int senderCount = 2;

    public static void main(String[] args) {
        MulticastReceiver multicastReceiver = new MulticastReceiver();
        multicastReceiver.listen();
    }
}
