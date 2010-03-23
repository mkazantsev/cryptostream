package ru.nsu.fit.kazantsev.cryptostream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by IntelliJ IDEA.
 * User: mkaz
 * Date: 23.03.2010
 * Time: 17:19:31
 * To change this template use File | Settings | File Templates.
 */
public class CryptoSocket {
    public static int keySize = 1024;
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
    private Socket sock;

    public CryptoSocket(Socket sock) {
        this.sock = sock;
        publicKey = null;
        privateKey = null;
    }

	public CryptoSocket(String address, int port) throws UnknownHostException,
            IOException {
		this.sock = new Socket(address, port);
		publicKey = null;
		privateKey = null;
	}

    public void createKeys() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyPairGen.genKeyPair();
        publicKey = (RSAPublicKey) keyPair.getPublic();
        privateKey = (RSAPrivateKey) keyPair.getPrivate();
        System.out.println("CryptoSocket: Keys created");
    }

    public void readPublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (sock.isConnected()) {
			InputStream is = sock.getInputStream();
			int res = 0;
			byte[] buf = new byte[keySize];
            /*
			while (res < keySize) {
				res += is.read(buf, res, keySize-res);
			}
            */
            is.read(buf);
			X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(buf);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = (RSAPublicKey) keyFactory.generatePublic(pubSpec);
        } else {
            System.out.println("CryptoSocket: Can't read public key, Socket is not connected");
        }
    }
    
	public void writeKey() throws IOException {
		if (sock.isConnected()) {
			OutputStream os = sock.getOutputStream();
			os.write(publicKey.getEncoded());
			os.flush();
		}
	}

    public CryptoOutputStream getCryptoOutputStream() throws IOException {
		CryptoOutputStream cos = null;
		cos = new CryptoOutputStream(sock.getOutputStream(), publicKey, privateKey);
		return cos;
    }

	public CryptoInputStream getCryptoInputStream() throws IOException {
		CryptoInputStream cis = null;
		cis = new CryptoInputStream(sock.getInputStream(), publicKey, privateKey);
		return cis;
	}

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }
}
