package test.study;

import java.net.InetSocketAddress;

public class SocketTest {
	
	public static void main(String[] args) {
		InetSocketAddress socketAddress = InetSocketAddress.createUnresolved("+123", 80);
		System.out.println(socketAddress.getAddress());
		System.out.println(socketAddress.getAddress().getHostName());
		socketAddress.getAddress().getAddress();
	}
	
	

}
