package com.miniweb.main;

import java.net.ServerSocket;
import java.net.Socket;

import com.miniweb.request.Worker;
import com.miniweb.utils.ServerInfomation;
import com.miniweb.utils.ThreadPoolUtils;

public class Start {

	public static void main(String args[]) {
		ServerSocket server = null;
		
		try {
			server = new ServerSocket(ServerInfomation.BIND_PORT);
			System.out.println("bind port :" + ServerInfomation.BIND_PORT +" server start..");
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (true) {
			try {
				Socket client = server.accept();
				ThreadPoolUtils.execute(new Worker(client));
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}

	}

}
