package pl.grzegorzchmaj.easydiet.passwordencryption;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PasswordEncryptionSHA256Test {

    private PasswordEncryptionSHA256 passwordEncryptionSHA256;


    @Before
    public void setUp(){
        passwordEncryptionSHA256 = new PasswordEncryptionSHA256();
    }


    @Test
    public void testGenerate_basic() {
        Assert.assertEquals("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", passwordEncryptionSHA256.generate("123456"));
        Assert.assertEquals("d498284de7348831a418a382bd6591f676a81244610f54fca2c9e4ce77e3c5f7", passwordEncryptionSHA256.generate("AJDAI@#ASM"));
    }

    @Test
    public void testGenerate_empty(){
        Assert.assertEquals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", passwordEncryptionSHA256.generate(""));
    }

    @Test (expected = NullPointerException.class)
    public void testGenerate_null(){
        passwordEncryptionSHA256.generate(null);
    }
}