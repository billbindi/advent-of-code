import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class Day4_2 {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String input = "yzbqklnj";
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        for (long curr = 1; curr < Long.MAX_VALUE; curr++) {
            String attempt = input + curr;
            byte[] digest = md5.digest(attempt.getBytes());
            String output = DatatypeConverter.printHexBinary(digest);
            if (first6Zeroes(output)) {
                System.out.println(curr + " " + output);
                return;
            }
            md5.reset();
        }
    }

    private static boolean first6Zeroes(String output) {
        if (output.length() < 6) return false;
        return output.charAt(0) == '0' &&
                output.charAt(1) == '0' &&
                output.charAt(2) == '0' &&
                output.charAt(3) == '0' &&
                output.charAt(4) == '0' &&
                output.charAt(5) == '0';
    }
}
