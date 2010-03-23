package ru.nsu.fit.kazantsev.cryptostream;

import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by IntelliJ IDEA.
 * User: mkaz
 * Date: 23.03.2010
 * Time: 17:51:36
 * To change this template use File | Settings | File Templates.
 */
public class CryptoInputStream {
    private InputStream is;
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public CryptoInputStream(InputStream is) {
        this.is = is;
        publicKey = null;
        privateKey = null;
    }

    public CryptoInputStream(InputStream is,
                             RSAPublicKey publicKey,
                             RSAPrivateKey privateKey) {
        this.is = is;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public byte[] read() throws IOException {
        byte[] encb = new byte[CryptoSocket.keySize / 8];
        byte[] b;
        int res = 0;
        /*
		while (res < keySize/8) {
			res += is.read(encb, res, keySize/8-res);
		}
		*/
        is.read(encb);
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            System.out.println("CryptoInputStream: Try to decrypt with ");
            System.out.println(privateKey);
            b = cipher.doFinal(encb);
            //System.out.println("CryptoInputStream: '" + new String(encb) + "'");
            return b;
        } catch (Exception e) {
            System.out.println("CryptoInputStream: Text is not decrypted");
            e.printStackTrace();
        }
        return null;
    }
}
