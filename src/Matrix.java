import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Matrix {

    // Function to write a matrix to a file
    public static void writeMatrixToFile(String filePath, int[][] matrix) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int[] row : matrix) {
                for (int element : row) {
                    writer.write(element + " ");
                }
                writer.newLine(); // Move to the next line after each row
            }
        }
    }

    // Function to read a matrix from a file
    public static int[][] readMatrixFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int rows = 0;
        int cols = 0;

        // First pass: Count the rows and columns
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.trim().split("\\s+");
            cols = tokens.length;
            rows++;
        }
        reader.close();

        // Initialize the matrix
        int[][] matrix = new int[rows][cols];

        // Second pass: Fill the matrix with values
        BufferedReader resetReader = new BufferedReader(new FileReader(filePath));
        int currentRow = 0;
        while ((line = resetReader.readLine()) != null) {
            String[] elements = line.split("\\s+");
            for (int i = 0; i < elements.length; i++) {
                matrix[currentRow][i] = Integer.parseInt(elements[i]);
            }
            currentRow++;
        }
        resetReader.close();

        return matrix;
    }

    // Function to multiply two matrices
    public static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;

        int[][] result = new int[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }

    // Function to generate a random square matrix
    public static int[][] generateRandomMatrix(int size) {
        Random random = new Random();
        int[][] matrix = new int[size][size]; // Create a square matrix
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = random.nextInt(10); // Random number between 0 and 9
            }
        }
        return matrix;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Check if command-line arguments are provided
            if (args.length == 2) {
                // Case 1: User provides two file names, read matrices from files
                String file1 = args[0];
                String file2 = args[1];

                int[][] matrix1 = readMatrixFromFile(file1);
                int[][] matrix2 = readMatrixFromFile(file2);

                // Perform matrix multiplication
                int[][] resultMatrix = multiplyMatrices(matrix1, matrix2);

                // Save the result to matrix3.txt
                writeMatrixToFile("matrix3.txt", resultMatrix);
                System.out.println("Result matrix saved to matrix3.txt");

            } else {
                // Case 2: User inputs an integer for matrix size
                System.out.println("Enter an integer for the size of square matrices:");
                int size = scanner.nextInt();

                // Generate random matrices
                int[][] matrix1 = generateRandomMatrix(size);
                int[][] matrix2 = generateRandomMatrix(size);

                // Write the generated matrices to files
                writeMatrixToFile("matrix1.txt", matrix1);
                writeMatrixToFile("matrix2.txt", matrix2);

                // Multiply matrices and save result to matrix3.txt
                int[][] resultMatrix = multiplyMatrices(matrix1, matrix2);
                writeMatrixToFile("matrix3.txt", resultMatrix);

                System.out.println("Random matrices saved to matrix1.txt, matrix2.txt, and result saved to matrix3.txt");
            }

        } catch (IOException e) {
            System.err.println("Error writing to file.");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}

