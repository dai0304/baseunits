/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package com.domainlanguage.tests;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * This class is to mimic the behavior of a server that delivers a canned
 * response.
 */
public class CannedResponseServer {
	
	private ServerSocket socket;
	
	private Thread processingThread;
	
	private String cannedResponse;
	
	private boolean keepProcessing;
	

	public CannedResponseServer(String cannedResponse) throws IOException {
		socket = new ServerSocket();
		socket.bind(new InetSocketAddress(InetAddress.getLocalHost().getHostName(), 0));
		this.cannedResponse = cannedResponse;
	}
	
	public String getHostName() {
		return socket.getInetAddress().getHostName();
	}
	
	public int getPort() {
		return socket.getLocalPort();
	}
	
	public void start() {
		processingThread = new Thread(getServerConnectionProcessor(), getClass().getName() + " processing thread");
		setKeepProcessing(true);
		processingThread.start();
	}
	
	public void stop() throws IOException {
		socket.close();
		setKeepProcessing(false);
		try {
			processingThread.join();
		} catch (InterruptedException ex) {
			// ignore it
		}
	}
	
	private synchronized boolean getKeepProcessing() {
		return keepProcessing;
	}
	
	private Runnable getServerConnectionProcessor() {
		return new Runnable() {
			
			@Override
			public void run() {
				try {
					while (getKeepProcessing()) {
						Socket client = socket.accept();
						serveCannedResponse(client);
					}
				} catch (SocketException ex) {
					// ignore it, we'll get it during the socket close
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
		};
	}
	
	private void serveCannedResponse(Socket client) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(client.getOutputStream());
		try {
			writer.write(cannedResponse);
		} finally {
			writer.close();
		}
	}
	
	private synchronized void setKeepProcessing(boolean keepProcessing) {
		this.keepProcessing = keepProcessing;
	}
}
