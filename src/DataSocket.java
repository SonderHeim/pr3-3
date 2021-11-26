import java.net.Socket;

public class DataSocket {
    private final Socket socket;
    private final String message;

    public DataSocket(Socket socket, String message) {
        this.socket = socket;
        this.message = message;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getMessage() {
        return message;
    }
}