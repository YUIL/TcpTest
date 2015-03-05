package util;

public class DataConverter {
	public static int bytesToInt(byte[] src) {  
	    int value=0;    
	    for(int i=0;i<src.length;i++){
	    	value=value|((src[i]& 0xFF)<<(i*8));
	    }
	    return value;  
	}  
	public static byte[] intToBytes(int src){
		
		byte[] bytes=new byte[4];
		for (int b = 0; b < bytes.length; b++) {
			bytes[b]=(byte)(src>>(b*8));
		}
		return bytes;
	}
}
