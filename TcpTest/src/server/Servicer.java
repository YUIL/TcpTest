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
	            tcpData.setType(bytesToInt(buf));
	            buf[0]=buf[1]=buf[2]=buf[3]=0;
	            if (tcpData.getType()!=0){
	            	
		            	inputStream.read(buf);
		            	if(buf.length!=0){
		            		byte[] data=new byte[bytesToInt(buf)];
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
    
	public static int bytesToInt(byte[] src) {  
	    int value=0;    
	    for(int i=0;i<src.length;i++){
	    	value=value|((src[i]& 0xFF)<<(i*8));
	    }
	    return value;  
	}  
	
    public static void main(String [] args)  
    {  
    	String s="ÄãºÃ°¡";
    	
    	byte []b2=s.getBytes();
    	byte[] b1=new byte[b2.length];
        System.arraycopy(b2, 0, b1, 0, b2.length);
        System.out.println(new String (b1));
    	try  
        {  
            ServerSocket serverSocket=new ServerSocket(8089);  
            while(true)  
            {  
                Socket socket=serverSocket.accept();  
                new Thread(new Servicer(socket)).start();  
            }  
            //serverSocket.close();  
        }catch(Exception e){
        	e.printStackTrace();
        }  
    }  
  
}  