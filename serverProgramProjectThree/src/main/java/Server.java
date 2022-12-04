import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;
public class Server {

    int count = 0;
    ArrayList<ClientThread> clientsArray = new ArrayList<ClientThread>();
    private Consumer<Serializable> callback;
    private Consumer<Serializable> callback2;
    private Consumer<Serializable> callback3;
    
    TheServer server;
    int portNumber = 0;
    String result;

    public Server(String portNum, Consumer<Serializable> call, Consumer<Serializable> call2, 
            Consumer<Serializable> call3) {

        callback = call;
        callback2 = call2;
        callback3 = call3;
        this.portNumber = Integer.parseInt(portNum);
        this.result = "";
        server = new TheServer();
        server.start();
    }

    public class TheServer extends Thread {
        public synchronized void run() {
            try (ServerSocket mysocket = new ServerSocket(portNumber);) {
                System.out.println("Server is waiting for a client!");
                while (true) {
                    count++;
                    ClientThread c = new ClientThread(mysocket.accept(), count);
                    clientsArray.add(c);
                    c.start();
                    // count++;
                    callback.accept("Server #" + portNumber);
                    callback2.accept("Clients connected: " + clientsArray.size());
                    callback3.accept("Client #" + count + " has connected to server# " + portNumber);
                    if (clientsArray.size() < 2) {
                        callback3.accept("Waiting for another player to join...");
                    } else {
                        callback3.accept("Game is starting!");
                    }
                }
            } // end of try
            catch (Exception e) {
                callback.accept("Server socket did not launch");
            }
        }// end of while
    }


    class ClientThread extends Thread {

        Socket connection;
        int count;
        ObjectInputStream in;
        ObjectOutputStream out;
        ClientThread(Socket s, int count) {
            this.connection = s;
            this.count = count;
        }

        public synchronized void updateClients(CFourInfo info) {
            for(int i = 0; i < clientsArray.size(); i++) {
                ClientThread t = clientsArray.get(i);
                info.whichPlayer = i + 1;
                try {
                    out.reset();
                    t.out.writeObject(info);
                } catch (Exception e) {
                    System.out.println("Error sending game info to client");
                }
            }
        }

        public synchronized void run() {

            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            } catch (Exception e) {
                System.out.println("Streams not open");
            }
            CFourInfo info = new CFourInfo();
            info.setCurrentPlayer(1);
            info.gameStatus = 0;

            if (clientsArray.size() > 1) {
                info.gameStatus = 3;    // Ready to play
            }

            updateClients(info);

            while (true) {                
                try {
                    CFourInfo data = (CFourInfo) in.readObject();
                    callback3.accept("Player #" + data.whichPlayer + " moved at (" + data.row + ", " + data.col + ")");
                    callback3.accept("Player " + data.getCurrentPlayer() + " turn!");

                    // System.out.println("current player is :" + data.getCurrentPlayer());
                    // System.out.println("count is :" + data.moveCount);

                    if(data.areFourConnected(1)){
                        callback3.accept("Player 1 Win!");
                        data.setPlayer1Win(true);
                        updateClients(data);
                        clientsArray.clear();
                    }
                    else if(data.areFourConnected(2)){
                        callback3.accept("Player 2 Win!");
                        data.setPlayer2Win(true);
                        updateClients(data);
                        clientsArray.clear();
                    }
                    if(data.moveCount == 42
                            && (data.areFourConnected(1) == false 
                            && data.areFourConnected(2) == false)){
                        data.gameDraw = true;
                        System.out.println("Game Draw Boolean" + data.gameDraw);
                        callback3.accept("Game Draw!");
                        updateClients(data);
                        clientsArray.clear();
                    }
                    updateClients(data);
                } 
                catch (Exception e) {
                   // CFourInfo data = (CFourInfo) in.readObject();
                    clientsArray.remove(this);
                    callback3.accept("Client #" + count + " left the game!");
                    count--;
                    callback2.accept("Clients connected: " + clientsArray.size());
                    callback3.accept("Game stopped!");
                    callback3.accept("Waiting for another player to join...");
                    info.gameStatus = 4;
                    // info.clearBoard();
                    System.out.println("reach here in catch exception, game status is " + info.gameStatus  );
                    
                    updateClients(info);
                    clientsArray.clear();
                    break;
                }
            }
        }// end of run

    }// end of client thread
}
