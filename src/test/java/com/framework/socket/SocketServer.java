package com.framework.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

public class SocketServer {
	
	@Test
	public void start() {
		try {
			ServerSocket admin = new ServerSocket(3000);
			while (true) {
				Socket customer = admin.accept();
				PrintStream ps = new PrintStream(customer.getOutputStream());
				ps.println("Hello");
				
				BufferedReader in = new BufferedReader(new InputStreamReader(customer.getInputStream()));
				String line = in.readLine();
				System.out.println(line);
				//ps.close();
				//admin.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
