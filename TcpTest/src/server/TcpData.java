package server;
/**
 * @author dj-004
 *
 */
public class TcpData {
	int type=0;
	int length;
	byte data[]=null;
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
	public String toString() {
		return "TcpData [type=" + type + ", length="
				+ data.length+", data="+new String(data)+ "]";
	}

}
