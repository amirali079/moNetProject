import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    private final static String host = "localhost";
    private final static Integer port = 5056;


    public static void main(String[] args) {


        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;
        InetAddress ip;

        try {
            ip = InetAddress.getByName(host);

            socket = new Socket(ip, port);

//        DataInputStream dis = new DataInputStream(socket.getInputStream());
//        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            File file = new File("input.json");
            // Get the size of the file
            long length = file.length();
            byte[] bytes = new byte[16 * 1024];
            in = new FileInputStream(file);
            out = socket.getOutputStream();

            int count;
            while ((count = in.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }

            out.close();
            in.close();
            socket.close();

        } catch (IOException e) {

        }

    }
}
