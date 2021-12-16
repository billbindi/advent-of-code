package advent2016;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day5_2 {
    private static final String INPUT = "ffykfhsq";

    public static void main(String[] args) throws NoSuchAlgorithmException {

        System.out.println(solve());
    }

    private static String solve() throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        char[] password = emptyPassword(8);
        int length = 0;
        long id = 0;
        while (length < 8) {
            md5.update((INPUT + id).getBytes());
            byte[] digest = md5.digest();
            String guess = DatatypeConverter
                    .printHexBinary(digest);
            if (guess.startsWith("00000")) {
                char indexChar = guess.charAt(5);
                int index = indexChar - '0';
                if (index >= 0 && index <= 7 && password[index] == 'X') {
                    password[index] = guess.charAt(6);
                    length++;
                }
            }
            id++;
        }
        return new String(password);
    }

    private static char[] emptyPassword(int length) {
        char[] empty = new char[length];
        for (int i = 0; i < length; i++) {
            empty[i] = 'X';
        }
        return empty;
    }
}
