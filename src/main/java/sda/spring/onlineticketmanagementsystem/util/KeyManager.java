package sda.spring.onlineticketmanagementsystem.util;


import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;

@Component
@Getter
public class KeyManager {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    private static final String SECURITY_KEY_DIR = "src/main/resources/security_keys/";
    private static final String PRIVATE_KEY_FILE = SECURITY_KEY_DIR + "private_key.pem";
    private static final String PUBLIC_KEY_FILE = SECURITY_KEY_DIR + "public_key.pem";

    @PostConstruct
    public void init() throws Exception {
        loadKeys();
    }

    private void loadKeys() throws Exception {
        // Create the directory if it doesn't exist
        new File(SECURITY_KEY_DIR).mkdirs();

        // Load keys from files or generate new ones
        if (Files.exists(Paths.get(PRIVATE_KEY_FILE)) && Files.exists(Paths.get(PUBLIC_KEY_FILE))) {
            this.privateKey = loadPrivateKeyFromFile();
            this.publicKey = loadPublicKeyFromFile();
        }else {
            // Previously before persisting the keys this was happening all the time
            generateAndSaveKeys();
        }
    }

    private PrivateKey loadPrivateKeyFromFile() throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(KeyManager.PRIVATE_KEY_FILE));
        return KeyUtil.getPrivateKeyFromBytes(keyBytes);
    }

    private PublicKey loadPublicKeyFromFile() throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(KeyManager.PUBLIC_KEY_FILE));
        return KeyUtil.getPublicKeyFromBytes(keyBytes);
    }

    public void generateAndSaveKeys() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();

        // Save keys to files for later use
        saveKeyToFile(PRIVATE_KEY_FILE, privateKey);
        saveKeyToFile(PUBLIC_KEY_FILE, publicKey);
    }

    private void saveKeyToFile(String filePath, Key Key) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(Key.getEncoded());
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
