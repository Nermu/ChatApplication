import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {

        String connect;

        //to check singleton server
        Server server = Server.getInstance("FOO");
        System.out.println(server.value);

        Server anotherServer = Server.getInstance("from another server");
        System.out.println(anotherServer.value);

        while (true) {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String[] input = new String[3];
            String serverHost;
            int serverPort;
            input = in.readLine().split(" ");
            connect = input[0];
            serverHost = input[1];
            serverPort = Integer.parseInt(input[2]);

            if (connect.contains("connect")) {
                System.out.println("connect " + serverHost + " " + serverPort);
                server = new Server(serverHost, serverPort);

                BufferedReader inClient = new BufferedReader(new InputStreamReader(System.in));
                String[] inputClient = new String[3];
                String clientHost;
                int clientPort;
                input = in.readLine().split(" ");
                String connectClient = input[0];

                if (connectClient.contains("connect")) {
                    clientHost = input[1];
                    clientPort = Integer.parseInt(input[2]);
                    System.out.println("connect " + clientHost + " " + clientPort);
                    Client client = new Client(clientHost, clientPort);
                    Thread c = new Thread(client);
                    c.start();

                }

                Thread s = new Thread(server);
                s.start();

                break;
            }

        }


    }
}