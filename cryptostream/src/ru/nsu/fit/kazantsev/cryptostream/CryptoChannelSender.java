package ru.nsu.fit.kazantsev.cryptostream;

import java.io.OutputStream;
import java.net.Socket;

public class CryptoChannelSender {
    public static void main(String[] args) {
        CryptoSocket cs;
        try {
            cs = new CryptoSocket("127.0.0.1", 2112);
            System.out.println("Connected");
            cs.readPublicKey();
            System.out.println("Received the key");
            System.out.print("Public key: ");
            System.out.println(cs.getPublicKey());
            CryptoOutputStream cos = cs.getCryptoOutputStream();
            System.out.println("Writing text");
            String text = "MessageMessageMessageMessageMessageMessageMessageMessageMessageMessageMessage" +
                    "MessageMessageMessageMessageMessageMessageMessageMessageMessageMessageMessage" +
                    "MessageMessageMessageMessageMessageMessageMessageMessageMessageMessageMessage" +
                    "MessageMessageMessageMessageMessageMessageMessageMessageMessageMessageMessage" +
                    "MessageMessageMessageMessageMessageMessageMessageMessageMessageMessageMessage" +
                    "MessageMessageMessageMessageMessageMessageMessageMessageMessageMessageMessage" +
                    "MessageMessageMessageMessageMessageMessageMessageMessageMessageMessageMessage";
            cos.write(text.getBytes());
            cs.close();

            Socket s = new Socket("127.0.0.1", 2112);
            System.out.println("Connected");
            OutputStream os = s.getOutputStream();
            System.out.println("Writing text");
            os.write(text.getBytes());
            s.close();
        } catch (Exception e) {
            System.out.println("Sender failed");
            e.printStackTrace();
        }
    }
}
