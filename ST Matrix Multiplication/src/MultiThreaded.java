import java.util.Scanner;
import java.util.Random;

public class MultiThreaded {
    private static Matrix matrix1;
    private static Matrix matrix2;

    public static void createMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Create Matrix");
            System.out.println("2. Auto Generate Matrix");
            System.out.println("3. Addition");
            System.out.println("4. Multiply Matrix");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createMatrix(scanner);
                    break;
                case 2:
                    autoGenerateMatrix(scanner);
                    break;
                case 3:
                    performAddition();
                    break;
                case 4:
                    performMultiplication();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void createMatrix(Scanner scanner) {
        System.out.print("Enter number of rows: ");
        int rows = scanner.nextInt();
        System.out.print("Enter number of columns: ");
        int cols = scanner.nextInt();
        Matrix matrix = new Matrix(rows, cols);
        System.out.println("Enter values for the matrix:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("Value at (" + i + "," + j + "): ");
                matrix.setValue(i, j, scanner.nextInt());
            }
        }
        if (matrix1 == null) {
            matrix1 = matrix;
        } else {
            matrix2 = matrix;
        }
    }

    private static void autoGenerateMatrix(Scanner scanner) {
        System.out.print("Enter number of rows: ");
        int rows = scanner.nextInt();
        System.out.print("Enter number of columns: ");
        int cols = scanner.nextInt();
        Matrix matrix = new Matrix(rows, cols);
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix.setValue(i, j, random.nextInt(100));  // Random values between 0 and 99
            }
        }
        if (matrix1 == null) {
            matrix1 = matrix;
        } else {
            matrix2 = matrix;
        }
        System.out.println("Auto-generated matrix:");
        matrix.display();
    }

    private static void performAddition() {
        if (matrix1 == null || matrix2 == null) {
            System.out.println("Please create two matrices first.");
            return;
        }
        try {
            long startTime = System.nanoTime();
            Matrix result = Matrix.add(matrix1, matrix2);
            long endTime = System.nanoTime();
            System.out.println("Resultant matrix after addition:");
            result.display();
            System.out.println("Time taken for single-threaded addition: " + (endTime - startTime) + " ns");

            startTime = System.nanoTime();
            result = Matrix.addMultiThreaded(matrix1, matrix2);
            endTime = System.nanoTime();
            System.out.println("Resultant matrix after multi-threaded addition:");
            result.display();
            System.out.println("Time taken for multi-threaded addition: " + (endTime - startTime) + " ns");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void performMultiplication() {
        if (matrix1 == null || matrix2 == null) {
            System.out.println("Please create two matrices first.");
            return;
        }
        try {
            long startTime = System.nanoTime();
            Matrix result = Matrix.multiply(matrix1, matrix2);
            long endTime = System.nanoTime();
            System.out.println("Resultant matrix after multiplication:");
            result.display();
            System.out.println("Time taken for single-threaded multiplication: " + (endTime - startTime) + " ns");

            startTime = System.nanoTime();
            result = Matrix.multiplyMultiThreaded(matrix1, matrix2);
            endTime = System.nanoTime();
            System.out.println("Resultant matrix after multi-threaded multiplication:");
            result.display();
            System.out.println("Time taken for multi-threaded multiplication: " + (endTime - startTime) + " ns");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        createMenu();
    }
}
