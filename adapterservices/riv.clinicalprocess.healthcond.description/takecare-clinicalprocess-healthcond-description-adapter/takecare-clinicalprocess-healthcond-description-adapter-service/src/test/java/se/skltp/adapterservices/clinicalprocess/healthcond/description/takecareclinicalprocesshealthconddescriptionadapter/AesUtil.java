package se.skltp.adapterservices.clinicalprocess.healthcond.description.takecareclinicalprocesshealthconddescriptionadapter;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.soitoolkit.commons.mule.util.MiscUtil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AesUtil {
  private static final String ALGORITHM = "AES";
  private static final String CIPHER_TRANSFORMATION = "AES/ECB/PKCS5Padding";

  String secretKey = null;

  public static void main(String[] args) throws Exception {
    AesUtil util = new AesUtil();
    util.setSecretKey("TheBestSecretKey");
    //String clearText = "This is some sec";
    String clearText = MiscUtil.readFileAsString("src/test/resources/testfiles/importRaRegistryData/request-input.xml");
    String encryptedText = util.encrypt(clearText);
    String decryptedText = util.decrypt(encryptedText);

    System.out.println("Clear text : [" + clearText + "]");
    System.out.println("clearText.length : " + clearText.length());
    System.out.println("Encrypted text : [" + encryptedText + "]");
    System.out.println("Decrypted text : [" + decryptedText + "]");

  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String encrypt(String data) throws Exception {
    Key key = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
    Cipher c = Cipher.getInstance(CIPHER_TRANSFORMATION);
    c.init(Cipher.ENCRYPT_MODE, key);
    byte[] encVal = c.doFinal(data.getBytes());
    String encryptedValue = new BASE64Encoder().encode(encVal);
    return encryptedValue;
  }

  public String decrypt(String encryptedData) throws Exception {
    Key key = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
    Cipher c = Cipher.getInstance(CIPHER_TRANSFORMATION);
    c.init(Cipher.DECRYPT_MODE, key);
    byte[] decodedValue = new BASE64Decoder().decodeBuffer(encryptedData);
    byte[] decValue = c.doFinal(decodedValue);
    String decryptedValue = new String(decValue);
    return decryptedValue;

  }

}
