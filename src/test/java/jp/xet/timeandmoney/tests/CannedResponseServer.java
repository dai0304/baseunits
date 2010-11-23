/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.timeandmoney.tests;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * あらかじめ用意されたレスポンスを返すサーバ。
 * 
 * This class is to mimic the behavior of a server that delivers a canned
 * response.
 */
public class CannedResponseServer {
	
	private ServerSocket socket;
	
	private Thread processingThread;
	
	private String cannedResponse;
	
	private boolean keepProcessing;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param cannedResponse 返すべきレスポンス
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public CannedResponseServer(String cannedResponse) throws IOException {
		socket = new ServerSocket();
		socket.bind(new InetSocketAddress(InetAddress.getLocalHost().getHostName(), 0));
		this.cannedResponse = cannedResponse;
	}
	
	/**
	 * ホスト名を返す。
	 * 
	 * @return ホスト名
	 */
	public String getHostName() {
		return socket.getInetAddress().getHostName();
	}
	
	/**
	 * ポート番号を返す。
	 * 
	 * @return ポート番号
	 */
	public int getPort() {
		return socket.getLocalPort();
	}
	
	/**
	 * サーバを開始する。
	 */
	public void start() {
		processingThread = new Thread(getServerConnectionProcessor(), getClass().getName() + " processing thread");
		setKeepProcessing(true);
		processingThread.start();
	}
	
	/**
	 * サーバを停止する。
	 * 
	 * @throws IOException 入出力エラーが発生した場合
	 */
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
