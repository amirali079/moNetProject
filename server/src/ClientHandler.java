import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler extends Thread {


//    static final String line = "\n------------------------------------------\n";


//    private String name ;
//
//    private File file;

//    private final InputStream in;
//    private final OutputStream out;
//    private final Socket socket;
//
//    private OutputStream fileWriter;
//
//
//    ClientHandler(Socket socket, InputStream in, OutputStream out,String name) {
//        this.socket = socket;
//        this.in = in;
//        this.out = out;
//        this.name = name;
//    }
//
//    @Override
//    public void run() {
//
//        file = new File(name + ".json");
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            System.out.println("File not created. ");
//        }
//
//
//        try {
//            fileWriter = new FileOutputStream(file);
//        } catch (FileNotFoundException ex) {
//            System.out.println("File not found. ");
//        }
//
//
//        try {
//            byte[] bytes = new byte[16 * 1024];
//
//            int count;
//            while ((count = in.read(bytes)) > 0) {
//                fileWriter.write(bytes, 0, count);
//            }
//
//            out.close();
//            in.close();
//            socket.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }




}