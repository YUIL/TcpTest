package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import util.JavaDataConverter;


/**
 * @author 宇
 *@version branch
 */
public class Servicer implements Runnable {
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

	public void run() {
		
		TcpData tcpData = new TcpData();
		boolean stop = false;
		//开始服务
		while (!stop) {
			getRemoteData(tcpData);
			switch (tcpData.getType()) {
			case 0:
				stop = true;
				break;
			default:
				System.out.println(tcpData.toString());
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
				// TODO 自动生成的 catch 块
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
				if (buf.length != 0) {// 如果len!=0就读data
					byte[] data = new byte[JavaDataConverter.bytesToInt(buf)];
					inputStream.read(data);
					tcpData.setData(data);
				}
				outStream.write(1);//回客户端一声，表示数据正常接收完了
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		System.out.println("start");
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