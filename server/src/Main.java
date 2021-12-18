import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    private static ArrayList<ClientHandler> clients = new ArrayList<>();

    private static Integer id = 0;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(5056);

        while (true) {
            Socket socket = null;

            try {
                // socket object to receive incoming client requests
                socket = serverSocket.accept();

                System.out.println("A new client is connected : " + socket);

                // obtaining input and out streams
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                System.out.println("Assigning new thread for this client");
                // create a new thread object
                ClientHandler t = new ClientHandler(socket, in, out, ("person" + id++));

                clients.add(t);

                // Invoking the start() method
                t.start();

            } catch (Exception e) {
                socket.close();
                e.printStackTrace();
            }
        }
    }


}

