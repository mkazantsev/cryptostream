package ru.nsu.fit.kazantsev.cryptostream;

import java.net.ServerSocket;
import java.net.Socket;

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
            while (cis.available() < 1) Thread.sleep(1);
            while(cis.available() > 0) {
                byte[] buf = cis.read();
                String out = new String(buf);
                System.out.println("Message received: '" + out + "'");
            }
            byte[] buf = cis.read();
            String out = new String(buf);
            System.out.println("Message received: '" + out + "'");            
        } catch (Exception e) {
            System.out.println("Reader failed");
            e.printStackTrace();
        }
    }
}
