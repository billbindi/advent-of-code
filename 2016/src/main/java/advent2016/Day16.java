package advent2016;

public class Day16 {
    private final static String ORIGINAL_STATE = "01111010110010011";
    private final static int DISK_LENGTH = 272;
    // part 2
    //private final static int DISK_LENGTH = 35651584;

    public static void main(String[] args) {
        System.out.println(solve());
    }

    private static String solve() {
        String fullDisk = fillDisk();
        return checksum(fullDisk);
    }

    private static String checksum(String disk) {
        String checksum = disk;
        while (checksum.length() % 2 == 0) {
            StringBuilder nextChecksum = new StringBuilder();
            for (int i = 0; i < checksum.length(); i += 2) {
                if (checksum.charAt(i) == checksum.charAt(i + 1)) {
                    nextChecksum.append("1");
                } else {
                    nextChecksum.append("0");
                }
            }
            checksum = nextChecksum.toString();
        }
        return checksum;
    }

    private static String fillDisk() {
        String disk = ORIGINAL_STATE;
        while (disk.length() < DISK_LENGTH) {
            String reverse = reverse(disk);
            String swapped = swap(reverse);
            disk = disk + "0" + swapped;
        }
        return disk.substring(0, DISK_LENGTH);
    }

    private static String reverse(String original) {
        return new StringBuilder(original)
                .reverse()
                .toString();
    }

    private static String swap(String original) {
        StringBuilder swapped = new StringBuilder();
        for (Character c : original.toCharArray()) {
            switch (c) {
                case '0':
                    swapped.append('1');
                    break;
                case '1':
                    swapped.append('0');
                    break;
                default:
                    throw new IllegalStateException("Illegal character '" + c + "' in string for swapping: " + original);
            }
        }
        return swapped.toString();
    }
}
