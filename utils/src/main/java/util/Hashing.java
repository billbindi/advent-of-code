package util;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {

    public static String md5(String input) throws NoSuchAlgorithmException {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = input.getBytes();
            md.update(bytes);
            return DatatypeConverter.printHexBinary(md.digest());
    }

    private Hashing() {
        // no instantiation
    }
}
