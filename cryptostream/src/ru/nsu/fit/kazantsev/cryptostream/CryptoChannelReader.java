package ru.nsu.fit.kazantsev.cryptostream;

import java.io.InputStream;
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
            long time1 = System.currentTimeMillis();
            while(cis.available() > 0) {
                byte[] buf = cis.read();
                String out = new String(buf);
                System.out.println("Message received: '" + out + "'");
            }
            byte[] buf = cis.read();
            String out = new String(buf);
            System.out.println("Message received: '" + out + "'");
            long time2 = System.currentTimeMillis();
            long diff = time2 - time1;
            System.out.println(diff + " milliseconds passed");

            s.close();
            s = ss.accept();
            InputStream is = s.getInputStream();
            while (is.available() < 1) Thread.sleep(1);
            time1 = System.currentTimeMillis();
            while(is.available() > 0) {
                is.read(buf);
                out = new String(buf);
                System.out.println("Message received: '" + out + "'");
            }
            is.read(buf);
            out = new String(buf);
            System.out.println("Message received: '" + out + "'");
            time2 = System.currentTimeMillis();
            diff = time2 - time1;
            System.out.println(diff + " milliseconds passed");
        } catch (Exception e) {
            System.out.println("Reader failed");
            e.printStackTrace();
        }
    }
}
