import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUtil {
    public ConsoleUtil(){
    }

    public Integer readIntInput() {

        Scanner prompt = new Scanner(System.in);
        Integer output = null;
        try { //checks for integer input
            return prompt.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Please enter valid input");
        }
        return output;
    }

    public String readStrInput() {
        Scanner prompt = new Scanner(System.in);
        return prompt.nextLine();
    }

    public void print(String input){
        System.out.println(input);
    }

}
