package server;
public class TcpData {
	int type=0;
	byte data[]=null;
	public int getType() {
		return type;
	}
	public String toString() {
		return "TcpData [type=" + type + ", length="
				+ data.length+"data="+new String(data)+ "]";
	}

	public void setType(int type) {
		this.type = type;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
}
