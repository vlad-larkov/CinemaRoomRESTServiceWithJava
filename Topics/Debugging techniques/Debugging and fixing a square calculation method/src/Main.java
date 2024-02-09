import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Scan for input
        String input = scanner.nextLine();

        //Conversion and calculation
        try {
            int n = Integer.parseInt(input);
            int result = square(n);

            //Output
            System.out.println(result);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number");
        }
    }

    //Function
    static int square(int n) {
        //Bug: Cause an exception here to be caught at runtime
        return n * n;
        //throw new UnsupportedOperationException();
    }
}