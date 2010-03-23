package ru.nsu.fit.kazantsev.cryptostream;

/**
 * Created by IntelliJ IDEA.
 * User: mkaz
 * Date: 23.03.2010
 * Time: 18:03:33
 * To change this template use File | Settings | File Templates.
 */
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
		} catch (Exception e) {
			System.out.println("Sender failed");
			e.printStackTrace();
		}
	}
}
