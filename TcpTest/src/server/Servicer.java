
package server;
import java.io.BufferedReader;  
import java.io.DataOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.net.ServerSocket;  
import java.net.Socket;  
import java.util.InputMismatchException;

import util.JavaDataConverter;
  
/**
 * @author dj-004
 * 
 */
public class Servicer implements Runnable {  
    public Socket socket;  
    
    public Servicer(Socket socket) {  
    	
        super();  
        this.socket = socket;  
        System.out.println(socket.getRemoteSocketAddress()+" connected!");
    }  
  
    public void run() {  
    	TcpData tcpData=new TcpData();
	    try {  
	    	OutputStream outStream=socket.getOutputStream();
	    	InputStream inputStream=socket.getInputStream();
	        while (true) {  
	            byte[] buf=new byte[4];
	            inputStream.read(buf);
	            if(buf[0]==0)
	            	break;
	            tcpData.setType(JavaDataConverter.bytesToInt(buf));
	            buf[0]=buf[1]=buf[2]=buf[3]=0;
	            if (tcpData.getType()!=0){
		            	inputStream.read(buf);
		            	if(buf.length!=0){
		            		byte[] data=new byte[JavaDataConverter.bytesToInt(buf)];
		            		inputStream.read(data);
		            		tcpData.setData(data);
		            	}
	            	
	            	outStream.write(1);
	            }
	            System.out.println(tcpData.toString());
	        }
	        socket.close();  
	        System.out.println(socket.getRemoteSocketAddress()+" closed!");
	    }
	    catch (IOException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }   
    }  
    
	
    public static void main(String [] args)  
    {  
    	
        System.out.println("start");
    	try  
        {  
            ServerSocket serverSocket=new ServerSocket(8089);  
            while(true)  
            {  
                Socket socket=serverSocket.accept();  
                new Thread(new Servicer(socket)).start();  
            }  
        }catch(Exception e){
        	e.printStackTrace();
        }  
    }  
  
}  