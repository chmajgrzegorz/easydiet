package pl.grzegorzchmaj.easydiet.passwordencryption;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class PasswordEncryptionSHA256 implements IPasswordEncryption {

    @Override
    public String generate(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(NullPointerException e)
        {
            throw new NullPointerException();
        }
        return generatedPassword;

    }
}
