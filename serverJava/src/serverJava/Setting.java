package serverJava;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class Setting extends JFrame
{

	private JLabel ip_info, port_info, certify_info;
	private JLabel ip_address;
	private JTextField port_number, certify_number;
	private JButton connect;
	private JLabel client_ip,client_ip2, client_port,client_port2;
	private JLabel receive_info,receive_info2;
	ServerSocket serverSocket;
	int count = 0;
	
	public Setting() {
		super("PC remote");
		
		setLocation(550, 230);
		setSize(300, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
	
		
		port_info = new JLabel("Port 번호 :");
		port_info.setBounds(50, 50, 100, 50);
		add(port_info);
		
		port_number = new JTextField("5001", 1);
		port_number.setDocument(new IntegerDocument());//숫자만 입력
		port_number.setText("5001");
		port_number.setBounds(50, 100, 200, 25);
		port_number.setBackground(Color.WHITE);
		add(port_number);
		
		certify_info = new JLabel("인증코드 :");
		certify_info.setBounds(50, 130, 100, 50);
		add(certify_info);
		
		certify_number = new JTextField("2081",1);
		certify_number.setBounds(50, 180, 200, 25);
		certify_number.setBackground(Color.WHITE);
		add(certify_number);
	
		client_ip = new JLabel("클라이언트 ip :");
		client_ip.setBounds(80, 230, 150, 30);
		add(client_ip);
		
		client_ip2 = new JLabel("x");
		client_ip2.setBounds(80, 250, 150, 30);
		add(client_ip2);
		
		client_port = new JLabel("클라이언트 port : ");
		client_port.setBounds(80, 290, 150, 30);
		add(client_port);
		
		client_port2 = new JLabel("x");
		client_port2.setBounds(80, 310, 150, 30);
		add(client_port2);
		
		receive_info = new JLabel("수신 신호 : ");
		receive_info.setBounds(80, 350, 150, 30);
		add(receive_info);
		
		receive_info2 = new JLabel("x");
		receive_info2.setBounds(80, 370, 150, 30);
		add(receive_info2);
		
		Thread t= new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				connectStart();
			}
		});
		t.start();

		setVisible(true);
	}

	public void connectStart()
	{
		try 
		{
			int port = Integer.parseInt(port_number.getText());
			System.out.println("Java server start....");
			serverSocket = new ServerSocket(port);

			System.out.println("reading from port....");
			Robot robot = new Robot();
			while(true)
			{
				Socket sock = serverSocket.accept();
				InetAddress clientHost = sock.getLocalAddress();
				int clientPort = sock.getPort();
				System.out.println("Client connect host : "+clientHost+" port: "+clientPort);
				ObjectInputStream inputStream;
				java.lang.Object obj;
				
				
				try 
				{
					inputStream = new ObjectInputStream(sock.getInputStream());
					obj = inputStream.readObject();
				}
				catch(Exception e)
				{
					if(!sock.isClosed())
						sock.close();
					System.out.println("exception 발생");
					continue;
				}
				String receive = String.valueOf(obj);
				StringTokenizer strtok = new StringTokenizer(receive, "&");
				
				String key = strtok.nextToken();
				String code = strtok.nextToken();
				
				System.out.println("input : "+key);
				
				boolean isConnect = true;
			
				
				if(!code.equals(certify_number.getText())) //수신한 인증코드와 입력한 인증코드가 같지 않으면 처리 x
				{
					receive_info2.setText("인증코드 에러");
					sock.close();
					continue;
				}
				else
				{
					receive_info2.setText(key);
					client_ip2.setText(clientHost+"");
					client_port2.setText(clientPort+"");
				}
				
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
				else if(key.equals("→"))
				{
					robot.keyPress(KeyEvent.VK_RIGHT);
				}
				else if(key.equals("←"))
				{
					robot.keyPress(KeyEvent.VK_LEFT);
				}
				else if(key.equals("↑"))
				{
					robot.keyPress(KeyEvent.VK_UP);
				}
				else if(key.equals("↓"))
				{
					robot.keyPress(KeyEvent.VK_DOWN);
				}
				else if(key.equals("MOUSE DRAG"))
				{
					String position = strtok.nextToken();
					strtok = new StringTokenizer(position,"|");
					
					int x = Integer.parseInt(strtok.nextToken());
					int y = Integer.parseInt(strtok.nextToken());
					
					PointerInfo pointerInfo = MouseInfo.getPointerInfo();
					
					robot.mouseMove(x + (int)pointerInfo.getLocation().getX(), y + (int)pointerInfo.getLocation().getY());
				}
				else if(key.equals("MOUSE LEFT"))
				{
					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
				}
				else if(key.equals("MOUSE WHEEL BTN"))
				{
					robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
					robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
				}
				else if(key.equals("MOUSE RIGHT"))
				{
					robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
					robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
				}
				
				ObjectOutputStream outputStream = new ObjectOutputStream(sock.getOutputStream());
				outputStream.writeObject(key+" Complete from server");
				outputStream.flush();
				sock.close();
				count++;
				if(count == 10)
				{
					serverSocket.close();
					serverSocket = new ServerSocket(port);
					count=0;
				}
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("exception 발생 22");
		}
	}
	private String getLocalServerIp()
	{
		try
		{
		    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
		    {
		        NetworkInterface intf = en.nextElement();
		        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
		        {
		            InetAddress inetAddress = enumIpAddr.nextElement();
		            if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress())
		            {
		            	return inetAddress.getHostAddress().toString();
		            }
		        }
		    }
		}
		catch (SocketException ex) {}
		return null;
	}



}
