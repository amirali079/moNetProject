public class Delay {
    private final long delay;
    private final String hostAddress;
    private final int hostPort;

    public long getDelay() {
        return delay;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public int getHostPort() {
        return hostPort;
    }

    public Delay(long delay, String hostAddress, int hostPort) {
        this.delay = delay;
        this.hostAddress = hostAddress;
        this.hostPort = hostPort;
    }
}
