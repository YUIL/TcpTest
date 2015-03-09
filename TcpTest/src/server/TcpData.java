package server;
import util.JavaDataConverter;
/**
 * @author dj-004
 * @changedby dj-004
 */
public class TcpData {
	int type=0;
	int length;
	byte data[]=null;
	
	public TcpData(){
		
	}
	public TcpData(byte[] bytes){
		byte[] buf=new byte[4];
		System.arraycopy(bytes, 0, buf, 0, 4);
		type=JavaDataConverter.bytesToInt(buf);
		System.arraycopy(bytes, 4, buf, 0, 4);
		length=JavaDataConverter.bytesToInt(buf);
		data=new byte[length];
/*		System.arraycopy(bytes, 8, data, 0, length);
		System.out.println("构造函数："+bytes.length);*/
		
	}
	public TcpData(int type,int length,byte[] data){
		this.type=type;
		this.length=length;
		this.data=data;
	}
	public TcpData(String data){
		this.type=2;
		this.data=data.getBytes();
		this.length=this.data.length;
	}


	//______________________________________________________________________
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	//______________________________________________________________________
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	//______________________________________________________________________
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	//______________________________________________________________________
	public void clearData(){
		type=0;
		length=0;
		data=null;
	}
	//______________________________________________________________________
	public String toString() {
		return "TcpData [type=" + type + ", length="
				+ data.length+", data="+(data!=null?new String(data):null)+ "]";
	}
	public byte[] toBytes(){		
		byte[] data=new byte[8+this.data.length];
		System.arraycopy(JavaDataConverter.intToBytes(this.type), 0, data, 0, 4);
		System.arraycopy(JavaDataConverter.intToBytes(this.length), 0, data, 4, 4);
		System.arraycopy(this.data, 0, data, 8, this.length);
		return data;
	}

}
