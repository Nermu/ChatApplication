import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client implements Runnable {

    private String hostName;
    private int portNumber;
    private Socket socket = null;
    BufferedReader bufferedReader = null;
    PrintStream printStream = null;
    String data = " ";
    String line = " ";

    Client(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    @Override
    public void run() {
        connectServer();
        sentMsgToServer();
    }

    public void connectServer() {
        while (true) {
            try {
                socket = new Socket(hostName, portNumber);
                System.out.println("Connected Successfully....");

                System.out.println("Please enter username : ");
                break;

            } catch (IOException e) {
                System.out.println("Failed to connect, try again");
            }
        }
    }

    public void sentMsgToServer() {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            printStream = new PrintStream(socket.getOutputStream());

            String user = bufferedReader.readLine();
            while (true) {

                System.out.print("User <[" + user + "]> : ");
                data = bufferedReader.readLine();
                printStream.println("\nMessage<[" + data + "]>");
                printStream.println("[" + user + "] : [" + data + "]");

                if (data.contains("exit")) {
                    while (true) {
                        try {
                            socket.close();
                            Socket newSocket = new Socket(hostName, portNumber);
                            System.out.println("Connection Successfully");
                            System.out.println("Please enter new username");

                            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                            PrintStream newPrintStream = new PrintStream(newSocket.getOutputStream());

                            String newUser = br.readLine();
                            while (true) {
                                System.out.print("User <[" + newUser + "]> : ");
                                data = br.readLine();
                                newPrintStream.println("\nMessage<[" + data + "]>");
                                newPrintStream.println("[" + newUser + "] : [" + data + "]");
                            }
                        }catch(Exception e){
                            System.out.println("Failed to connect again, try again");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}