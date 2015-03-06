package com.framework.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

public class SocketCustomer {
	
	//813
		@Test
		public void client() {
			try {
				Socket socket = new Socket("127.0.0.1", 3000);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String line = br.readLine();
				System.out.println(line);
				PrintStream ps = new PrintStream(socket.getOutputStream());
				ps.print("Hellos");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

}
