import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
public class Sort {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("input path:");
        String inputFilePath = scanner.next();
        System.out.println("output path:");
        String outputFilePath = scanner.next();
        scanner.close();
        List<Integer> numbers = readNumbersFromFile(inputFilePath);
        Collections.sort(numbers);
        writeNumbersToFile(outputFilePath, numbers);
    }

    private static List<Integer> readNumbersFromFile(String filename) {
        List<Integer> numbers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                numbers.add(Integer.parseInt(line.trim()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return numbers;
    }

    private static void writeNumbersToFile(String filename, List<Integer> numbers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Integer number : numbers) {
                writer.write(number.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
