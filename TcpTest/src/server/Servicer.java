package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

import util.JavaDataConverter;


/**
 * @author 宇
 * @branch master
 * @changedby dj-004
 * @madifition mergeTest
 */

public class Servicer implements Runnable {
	public static SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public Socket socket;
	OutputStream outStream;
	InputStream inputStream;

	public Servicer(Socket socket) {

		super();
		this.socket = socket;
		try {
			outStream = socket.getOutputStream();
			inputStream = socket.getInputStream();// 获得socket输入流
		} catch (IOException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		}// 获得socket输出流
		System.out.println(socket.getRemoteSocketAddress() + " connected!");
	}

	public void run() {//服务线程入口
		
		TcpData tcpData = new TcpData();
		boolean stop = false;
		//开始服务
		while (!stop) {//循环接受数据
			getRemoteData(tcpData);//取得一个数据
			switch (tcpData.getType()) {//判断数据类型
			case 0://type为0则中断服务
				stop = true;
				break;
			case 2://type为2则
			default://默认操作：
				if(tcpData.getType()>2){
					System.out.println("类型只能为0、1、2");
					break;
				}
				try {//将接受到的数据发回给客户端
					outStream.write(tcpData.toBytes());	
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(df.format(new Date())+"    "+tcpData.toString());
				tcpData.clearData();
				break;
			}
		}
		//结束服务
		try {
			inputStream.close();
			outStream.close();
			socket.close();
		} catch (IOException e) {
			try {
				throw(e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		System.out.println(socket.getRemoteSocketAddress() + " closed!");
	}

	private void getRemoteData(TcpData tcpData) {
		try {
			byte[] buf = new byte[4];// 用来读取type和len的字节数组
			inputStream.read(buf);			
			tcpData.setType(JavaDataConverter.bytesToInt(buf));
			if (tcpData.getType() != 0) {// 如果type!=0就读len
				buf[0] = buf[1] = buf[2] = buf[3] = 0;
				inputStream.read(buf);
				tcpData.setLength(JavaDataConverter.bytesToInt(buf));
				if (tcpData.getLength()!= 0) {// 如果len!=0就读data
					byte[] data = new byte[tcpData.getLength()];
					inputStream.read(data);
					tcpData.setData(data);
				}				
			}
		}catch(SocketException e){
			System.out.println("client had been closed!");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		System.out.println("server start:");
		try {
			ServerSocket serverSocket = new ServerSocket(8089);
			while (true) {
				Socket socket = serverSocket.accept();
				new Thread(new Servicer(socket)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}