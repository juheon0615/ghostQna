//QNA SERVER

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class QnaServer{
	ServerScoket server = null;
	Socket sock = null;
	String index1 = null;

//Starting server and opening Socket commit
	public QnaSever(){
		System.out.println("Qna Server Started...");
		try{
			server = new ServerScoekt(port);
			while(true){
				sock = server.accept();
				InputThread inth = new InputThread(sock);
				inth.start();
			}
		}catch (Exception ex){
			System.out.println(ex);
		}
			System.out.println(ex);
	} finally {
		try {
			if (sock != null)
				sock.close();
		} catch (Exception ex) {
		}
	}

	// main
	public static void main(String[] args) {
		new RoomContentServer();
	}



}
public class RoomContentServer {
	ServerSocket server = null;
	Socket sock = null;
	String index1 = null;
	String index2 = null;
	String index3 = null;

	static int port = 33000;

	public RoomContentServer() {
		System.out.println("RoomList Server started...");
		try {
			server = new ServerSocket(port);
			while (true) {
				sock = server.accept();
				InputThread inth = new InputThread(sock);
				inth.start();
			}

		} catch (Exception ex) {

			System.out.println(ex);
		} finally {
			try {
				if (sock != null)
					sock.close();
			} catch (Exception ex) {
			}
		}
	}

	public static void main(String[] args) {
		new RoomContentServer();
	}
//// making code based on carpool server
	class InputThread extends Thread {
		Socket sock = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		String requestLine = null;
		String[] requestContent = null;
		String id = null;
		String passWord = null;
		String writeData = null;

		public InputThread(Socket sock) {
			this.sock = sock;

			try {

				System.out.println(sock.getInetAddress() + " is connected to RoomListServer");
				br = new BufferedReader(new InputStreamReader(
						sock.getInputStream(),"UTF-8"));
				
				pw = new PrintWriter(new OutputStreamWriter(
						sock.getOutputStream()));


			} catch (Exception ex) {
				System.out.println("Ŭ???̾?Ʈ????????");
				System.out.println(ex);
			}
		}

		public void run() {
			try {
				
				if ((requestLine = br.readLine()) != null){ 
					requestContent = requestLine.split("<>");
					}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			// idPasswordSpliter[0]; ?˻? or????

			if (requestContent[0].equals("?˻?")) {
				
				index1 = requestContent[1];
				index2 = requestContent[2];
				index3 = requestContent[3];
				RoomContentsViewer searchRoom = new RoomContentsViewer(index1,
						index2, index3);// index ???߳??? <>??????<>??????
				writeData = searchRoom.writeData;
				
				pw.println(writeData);
				pw.flush();
				
			}else if (requestContent[0].equals("????")) {
				EnterRoom enterRoom = null;
				try {
					index1 = requestContent[1];
					
					index2 = requestContent[2];
					enterRoom = new EnterRoom(index1, index2);
				} catch (ClassNotFoundException | IOException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//??Ű<>?й?
				//writeData = "join";
				writeData = enterRoom.writeData;
				
				pw.println(writeData);
				pw.flush();
			}
			else if(requestContent[0].equals("????"))
			{
				index1 = requestContent[1];//roomkey
				
				index2 = requestContent[2];//stuNum
				RegisterRoom registerRoom = new RegisterRoom(index1, index2);
				writeData = registerRoom.writeData;
				
				pw.println(writeData);
				pw.flush();
			}
			
		}
	}
}