package advent2016;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day5_1 {
    private static final String INPUT = "ffykfhsq";

    public static void main(String[] args) throws NoSuchAlgorithmException {

        System.out.println(solve());
    }

    private static String solve() throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        StringBuilder password = new StringBuilder();
        int length = 0;
        long id = 0;
        while (length < 8) {
            md5.update((INPUT + id).getBytes());
            byte[] digest = md5.digest();
            String guess = DatatypeConverter
                    .printHexBinary(digest);
            if (guess.startsWith("00000")) {
                password.append(guess.charAt(5));
                length++;
            }
            id++;
        }
        return password.toString();
    }
}
