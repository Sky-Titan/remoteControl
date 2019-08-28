package serverJava;

import java.awt.FlowLayout;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

public class Main{

	
	public static void main(String[] args) {
		
		try 
		{
			
			int port = 5001;
			System.out.println("Java server start....");
			ServerSocket serverSocket = new ServerSocket(port);

			System.out.println("reading from port....");
			Robot robot = new Robot();
			while(true)
			{
				Socket sock = serverSocket.accept();
				InetAddress clientHost = sock.getLocalAddress();
				int clientPort = sock.getPort();
				System.out.println("Client connect host : "+clientHost+" port: "+clientPort);
				
				ObjectInputStream inputStream = new ObjectInputStream(sock.getInputStream());
				java.lang.Object obj = inputStream.readObject();
				System.out.println("input : "+obj);
				ObjectOutputStream outputStream = new ObjectOutputStream(sock.getOutputStream());
				
				String key = String.valueOf(obj);
				
				if(key.equals("ENTER"))
				{
					robot.keyPress(KeyEvent.VK_ENTER);
				}
				else if(key.equals("SPACE"))
				{ 
					robot.keyPress(KeyEvent.VK_SPACE);
				}
				else if(key.equals("LEFT"))
				{
					robot.keyPress(KeyEvent.VK_LEFT);
				}
				else if(key.equals("RIGHT"))
				{
					robot.keyPress(KeyEvent.VK_RIGHT);
				}
				else if(key.equals("ALT + TAB"))
				{
					robot.keyPress(KeyEvent.VK_ALT);
					robot.keyPress(KeyEvent.VK_TAB);
					
					robot.keyRelease(KeyEvent.VK_ALT);
					robot.keyRelease(KeyEvent.VK_TAB);
				}
				else if(key.equals("กๆ"))
				{
					robot.keyPress(KeyEvent.VK_RIGHT);
				}
				else if(key.equals("ก็"))
				{
					robot.keyPress(KeyEvent.VK_LEFT);
				}
				else if(key.equals("ก่"))
				{
					robot.keyPress(KeyEvent.VK_UP);
				}
				else if(key.equals("ก้"))
				{
					robot.keyPress(KeyEvent.VK_DOWN);
				}
		
				
				outputStream.writeObject(obj+" Complete from server");
				outputStream.flush();
				sock.close();
			}
		
		}
		catch(Exception e)
		{
			
		}	
	}
}
