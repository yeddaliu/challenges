import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final long MAX_AMOUNT = 1000000000;
    private static final String ZERO = "Zero";
    private static final String HUNDRED = "Hundred";
    private static final String DOLLAR_UNIT = "Dollars";
    private static final String[] DIGITS_LIST = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
    private static final String[] TEENS_LIST = {"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private static final String[] TENS_LIST = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private static final String[] GROUP_CURRENCY_LIST = {"Million", "Thousand", ""};

    public static void main (String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Find no input resource.");
            return;
        }

        BufferedReader buffer = new BufferedReader(new FileReader(args[0]));
        String line;
        while ((line = buffer.readLine()) != null) {
            System.out.println(genAmountText(parseInputLine(line.trim())));
            line = null;
        }
        buffer.close();
        buffer = null;
    }

    private static long parseInputLine(String line) {
        if (line.length() == 0) {
            return 0;
        }
        try {
            return Long.parseLong(line.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private static String genAmountText(long amount) {
        if (amount >= MAX_AMOUNT) 
            return amount + " is out of range";
        if (amount == 0) 
            return ZERO +DOLLAR_UNIT;

        // split 3 digits as group
        String digitsText = String.format("%09d", amount);
        String pattern = "\\d{3}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(digitsText);

        StringBuilder amountText = new StringBuilder();
        int count = 0;
        while(m.find()){
            String groupText = genGroupText(m.group());
            if (groupText.length() > 0) 
                amountText.append(groupText).append(GROUP_CURRENCY_LIST[count]);
            count++;
        }
        amountText.append(DOLLAR_UNIT);
        return amountText.toString();
    }
    
    private static String genGroupText(String groupAmount) {
        int value = Integer.parseInt(groupAmount);
        if (value == 0)
            return "";

        StringBuilder groupText = new StringBuilder();
        int digit1 = Math.floorDiv(value, 100);
        int digit2 = Math.floorMod(Math.floorDiv(value, 10), 10);
        int digit3 = Math.floorMod(value, 10);

        if (digit1 > 0) {
            groupText.append(DIGITS_LIST[digit1]).append(HUNDRED);
        }
        if (digit2 == 0) {
            groupText.append(DIGITS_LIST[digit3]);
        } else if (digit2 == 1) {
            groupText.append(TEENS_LIST[digit3]);
        } else if (digit2 > 1) {
            groupText.append(TENS_LIST[digit2]).append(DIGITS_LIST[digit3]);
        }
        return groupText.toString();
    }
}