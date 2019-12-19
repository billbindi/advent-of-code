public class Day10_2 {

    public static void main(String[] args) {
        String input = "1113222113";
        String output = iterate(input, 50);
        System.out.println(output);
        System.out.println(output.length());
    }

    private static String iterate(String curr, int n) {
        String next = curr;
        for (int i = 0; i < n; i++) {
            next = iterateOnce(next);
        }
        return next;
    }

    private static String iterateOnce(String input) {
        StringBuilder output = new StringBuilder();
        for (int index = 0; index < input.length();) {
            char curr = input.charAt(index);
            int counter = 0;
            do {
                if (index + counter < input.length()
                        && input.charAt(index) == input.charAt(index + counter)) {
                    counter++;
                } else {
                    break;
                }
            } while (true);
            output.append(counter);
            output.append(curr);
            index += counter;
        }
        return output.toString();
    }
}
