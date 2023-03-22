
import java.util.Scanner;

public class WhitelistExample {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter input: ");
        String input = scanner.nextLine();

        if (!input.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")) {
            throw new IllegalArgumentException("Input contains illegal characters, only letters and numbers are allowed");
        }
    }
}