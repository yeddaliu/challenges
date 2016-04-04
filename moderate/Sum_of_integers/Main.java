import java.io.*;
import java.lang.ArrayIndexOutOfBoundsException;

public class Main {
    private static final int[] EMPTY_VALS = new int[0];

    public static void main (String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Find no input resource.");
            return;
        }

        BufferedReader buffer = new BufferedReader(new FileReader(args[0]));
        String line;
        while ((line = buffer.readLine()) != null) {
            int[] numberList = parseInputLine(line.trim());
            int maxSum = getMax(numberList);
            System.out.println(maxSum);
            numberList = null;
        }
        buffer.close();
        buffer = null;
    }

    private static int[] parseInputLine(String line) {
        if (line.length() == 0) {
            return EMPTY_VALS;
        }

        try {
            String[] input = line.split(",");
            int[] values = new int[input.length];
            for (int i = 0; i < input.length; i++) {
                values[i] = Integer.parseInt(input[i]);
            }
            input = null;
            return values;
        } catch (NumberFormatException e) {
            return EMPTY_VALS;
        }
    }

    private static int getMax(int[] list) {
        if (list.length == 0) return 0;

        int sum = list[0];
        int max = list[0];
        for(int i=1; i < list.length; i++){
            sum = Math.max(sum+list[i], list[i]);
            max = Math.max(max, sum);
        }
        return max;
     }
}