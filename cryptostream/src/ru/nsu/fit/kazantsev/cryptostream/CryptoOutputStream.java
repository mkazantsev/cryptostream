package ru.nsu.fit.kazantsev.cryptostream;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.OutputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class CryptoOutputStream {
    private byte[] buffer;
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

    public void write128(byte[] b) throws IOException {
        byte[] encb = null;
        buffer = b;
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
        if (encb != null) {
            os.write(encb);
            os.flush();
        }
    }

    public void write(byte[] b) throws IOException {
        int res = b.length;
        int offset = 0;
        byte[] buf = new byte[CryptoSocket.keySize / 8];
        while (res > CryptoSocket.keySize / 8) {
            for (int i = 0; i < CryptoSocket.keySize / 8; i++)
                buf[i] = b[i + offset];
            write128(buf);
            res -= CryptoSocket.keySize / 8;
            offset += CryptoSocket.keySize / 8;
        }
        int i = 0;
        for (i = 0; i < res; i++)
            buf[i] = b[i + offset];
        for (int j = i; j < CryptoSocket.keySize/8; j++)
            buf[j] = 0;
        write128(buf);
    }
}
