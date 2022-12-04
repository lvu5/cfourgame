import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;



public class Client extends Thread{

	
	Socket socketClient;
	
	ObjectOutputStream out;
	ObjectInputStream in;

	static int count = 0;

	static int portNumber;
	static String ipAddress;
	static String result;

	private CFourInfo info;
	
	private Consumer<Serializable> callback;
	private Consumer<Serializable> callback2;
	private Consumer<Serializable> callback3;
	
	public Object gameBoard;
	
	Client(Consumer<Serializable> call){
		callback = call;
	}

	Client(String portNumber, String ipAddress, Consumer<Serializable> call) {
		Client.portNumber = Integer.parseInt(portNumber);
		Client.ipAddress = ipAddress;
		callback = call;
	}


	public synchronized void run() {
		try {
			socketClient= new Socket(ipAddress, portNumber);
			out = new ObjectOutputStream(socketClient.getOutputStream());
			in = new ObjectInputStream(socketClient.getInputStream());
			socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {}

		while(true) {
			 
			try {
				this.info = (CFourInfo) in.readObject();
				callback.accept(info);
			}
			catch(Exception e) {}
		}
	
    }
	
	public void send(CFourInfo info) {
		
		try {
			//out.reset();
			out.writeObject(info);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
