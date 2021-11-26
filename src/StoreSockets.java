import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Stack;

public class StoreSockets {
    private Stack<DataSocket> dataSockets;

    StoreSockets() {
        dataSockets = new Stack<>();
    }

    synchronized void save(Socket socket, String message) {
        System.out.println("save " + socket);
        dataSockets.add(new DataSocket(socket, message));
    }

    synchronized void send() {
        System.out.println("send " + dataSockets.size());

        while (!dataSockets.isEmpty()) {
            DataSocket dataSocket = dataSockets.pop();

            try {
                Socket socket = dataSocket.getSocket();

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                out.println(dataSocket.getMessage());

                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Closed: " + socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}