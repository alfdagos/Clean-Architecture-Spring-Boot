package it.alf.cleana.security;

import java.security.SecureRandom;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Component;

@Component
public class PBKDF2PasswordHasher {
    private static final int SALT_SIZE = 16;
    private static final int HASH_SIZE = 32; // bytes
    private static final int ITERATIONS = 500_000;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private final SecureRandom rng = new SecureRandom();

    public String hash(String password) {
        byte[] salt = new byte[SALT_SIZE];
        rng.nextBytes(salt);
        byte[] hash = pbkdf2(password.toCharArray(), salt, ITERATIONS, HASH_SIZE * 8);
        return HexFormat.of().formatHex(hash) + "-" + HexFormat.of().formatHex(salt);
    }

    public boolean verify(String password, String stored) {
        String[] parts = stored.split("-");
        if (parts.length != 2) return false;
        byte[] hash = HexFormat.of().parseHex(parts[0]);
        byte[] salt = HexFormat.of().parseHex(parts[1]);
        byte[] inputHash = pbkdf2(password.toCharArray(), salt, ITERATIONS, HASH_SIZE * 8);
        if (hash.length != inputHash.length) return false;
        int diff = 0;
        for (int i = 0; i < hash.length; i++) diff |= hash[i] ^ inputHash[i];
        return diff == 0;
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bits) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bits);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
