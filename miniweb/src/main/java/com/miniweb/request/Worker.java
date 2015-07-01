package com.miniweb.request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.miniweb.response.LoadContext;

public class Worker implements Runnable {
	private Socket connection;
	
	public Worker(Socket connection) {
		this.connection = connection;
	}
	
	@Override
	public void run() {
		try {
			List<String> requests = new ArrayList<String>();
			BufferedReader is = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			PrintWriter os = new PrintWriter(connection.getOutputStream());
			String line = is.readLine();
			while (line != null && line.length() > 0) {
				System.out.println(line);
				requests.add(line);
				line = is.readLine();
			} 
			String html = LoadContext.getInstance().getContext(AnalysisHTTPHearder.getInstance().analysisRequest(requests));
			os.println(html);
			os.flush();
			os.close(); 
			is.close(); 
			connection.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}

}
