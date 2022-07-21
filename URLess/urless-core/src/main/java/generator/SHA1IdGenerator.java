package generator;

import exception.FailedToCreatURLException;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SHA1IdGenerator implements IDGenerator {
    private Random random = new Random();

    @Override
    public String generate(String url, Set<String> collisions) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new FailedToCreatURLException();
        }
        byte[] urlBytes = url.getBytes(StandardCharsets.UTF_8);
        boolean collision = false;
        do {
            if (collision) {
                int randomByte = random.nextInt(urlBytes.length);
                urlBytes[randomByte] = (byte) random.nextInt();
            }
            byte[] hash = md.digest(urlBytes);
            String id = Base64.getUrlEncoder().encodeToString(hash).substring(0, 6);
            if (collisions.contains(id)) {
                collision = true;
            } else {
                return id;
            }
        } while (collision);
        throw new FailedToCreatURLException();
    }

    @Override
    public String generate(List<String> urls, Set<String> collisions) throws IOException {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new FailedToCreatURLException();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        for (String element : urls) {
            out.writeUTF(element);
        }
        byte[] urlBytes = baos.toByteArray();
        boolean collision = false;
        do {
            if (collision) {
                int randomByte = random.nextInt(urlBytes.length);
                urlBytes[randomByte] = (byte) random.nextInt();
            }
            byte[] hash = md.digest(urlBytes);
            String id = Base64.getUrlEncoder().encodeToString(hash).substring(0, 5);
            if (collisions.contains(id)) {
                collision = true;
            } else {
                return id;
            }
        } while (collision);
        throw new FailedToCreatURLException();
    }
}
