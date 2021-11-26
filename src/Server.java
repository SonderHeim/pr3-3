import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(8080)) {
            System.out.println("Start server...");

            StoreSockets storeSockets = new StoreSockets();

            setInterval(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Sending message");
                    storeSockets.send();
                }
            }, 5000L);

            var pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new Capitalizer(listener.accept(), storeSockets));
            }
        }
    }

    private static void setInterval(TimerTask task, Long delay) {
        Timer timer = new Timer();
        timer.schedule(task, 0L, delay);
    }

    private static class Capitalizer implements Runnable {
        private Socket socket;
        private StoreSockets storeSockets;

        Capitalizer(Socket socket, StoreSockets storeSockets) {
            this.socket = socket;
            this.storeSockets = storeSockets;
        }

        @Override
        public void run() {
            System.out.println("Connect: " + socket);
            try {
                var in = new Scanner(socket.getInputStream());
                storeSockets.save(socket, in.nextLine());
            } catch (Exception e) {
                System.out.println("Error:" + socket);
            }
        }
    }
}