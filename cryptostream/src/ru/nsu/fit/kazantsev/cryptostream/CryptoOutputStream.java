package ru.nsu.fit.kazantsev.cryptostream;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.OutputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by IntelliJ IDEA.
 * User: mkaz
 * Date: 23.03.2010
 * Time: 17:58:55
 * To change this template use File | Settings | File Templates.
 */
public class CryptoOutputStream {
    private int[] buffer;
    private OutputStream os;
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public CryptoOutputStream(OutputStream os) {
        this.os = os;
        publicKey = null;
        privateKey = null;
    }

    public CryptoOutputStream(OutputStream os, RSAPublicKey pubKey, RSAPrivateKey privKey) {
        this.os = os;
        this.publicKey = pubKey;
        this.privateKey = privKey;
        System.out.println("CryptoOutputStream: created with publicKey: ");
        System.out.println(publicKey);
        System.out.println("CryptoOutputStream: created with privateKey: ");
        System.out.println(privateKey);
    }

    public void write(byte[] b) throws IOException {
        byte[] encb = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            System.out.println("CryptoOutputStream: encrypting " + b.length + " bytes with");
            System.out.println(publicKey);
            encb = cipher.doFinal(b);
        } catch (Exception e) {
            System.out.println("Text is not encrypted");
            e.printStackTrace();
        }
        System.out.println("CryptoOutputStream: writing " + encb.length + " bytes to stream");
        if (encb != null) {
            os.write(encb);
            os.flush();
        }
        //System.out.println("CryptoOutputStream: sent message '" + new String(encb) + "'");
        System.out.println("CryptoOutputStream: wrote " + encb.length + " bytes to stream");
    }
}
