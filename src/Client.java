import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Message: ");
            String message = scanner.nextLine();

            Socket clientSocket = new Socket ("localhost",8080);

            Scanner is = new Scanner(clientSocket.getInputStream());
            PrintWriter outS = new PrintWriter(clientSocket.getOutputStream(), true);

            outS.println(message);

            String receivedData = is.nextLine();
            System.out.println("Getting data: " + receivedData);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}