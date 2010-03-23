package ru.nsu.fit.kazantsev.cryptostream;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: mkaz
 * Date: 23.03.2010
 * Time: 18:07:33
 * To change this template use File | Settings | File Templates.
 */
public class CryptoChannelReader {
	public static void main(String[] args) {
		Socket s;
		CryptoSocket cs;
		try {
			ServerSocket ss = new ServerSocket(2112);
			s = ss.accept();
			cs = new CryptoSocket(s);
			cs.createKeys();
            System.out.print("Public key: ");
            System.out.println(cs.getPublicKey());          
			cs.writeKey();
			CryptoInputStream cis = cs.getCryptoInputStream();
            System.out.println("Trying to read bytes from stream");
            byte[] buf = cis.read();
            System.out.println(buf);
            String out = new String(buf);
			System.out.println("Message received: '" + out + "'");
		} catch (Exception e) {
			System.out.println("Reader failed");
			e.printStackTrace();
		}
	}    
}
