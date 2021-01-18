package perf.ermolaevnk;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FileSplitter {
    public static char LINE_ENDING = '\n';
    public static boolean overwritingFilesPermission = false;

    public static void main(String[] args) {
        int resultFilesCount = 10, resultFilesCounter = 1;
        long resultFileMaxSize;
        String inputFileName = args[0],
                outputFileNameTemplate = args[1],
                line, outputFileName;
        File inputFile, outputFile;
        BufferedWriter writer;
        BufferedReader reader;

        try {
            inputFile = new File(inputFileName);

            outputFileName = createNewOutputFile(outputFileNameTemplate, resultFilesCounter);
            if (outputFileName.equals("Program execution terminated by user")) {
                System.out.println(outputFileName);
                return;
            }
            outputFile = new File(outputFileName);
            writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(outputFileName, false), StandardCharsets.UTF_8)
            );

            resultFileMaxSize = inputFile.length() / resultFilesCount;

            reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(inputFileName), "WINDOWS-1251")
            );

            while ((line = reader.readLine()) != null) {
                writer.append(line).append(String.valueOf(LINE_ENDING));
                if (outputFile.length() >= resultFileMaxSize) {
                    writer.close();
                    resultFilesCounter++;
                    outputFileName = createNewOutputFile(outputFileNameTemplate, resultFilesCounter);
                    if (outputFileName.equals("Program execution terminated by user")) {
                        System.out.println(outputFileName);
                        return;
                    }
                    outputFile = new File(outputFileName);
                    writer = new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(outputFileName, false), StandardCharsets.UTF_8
                            )
                    );
                }
            }
            writer.close();
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static String createNewOutputFile(String FileNameTemplate, int resultFilesCounter) throws IOException {
        Scanner console_input = new Scanner(System.in);
        String outputFileName = FileNameTemplate + resultFilesCounter;
        File outputFile = new File(outputFileName);
        if (outputFile.createNewFile() || overwritingFilesPermission) {
            System.out.println(outputFileName + " created");
        } else {
            System.out.println("File " + outputFileName + " already exists"
                    + "Please allow program to overwrite files, otherwise program will stop execution"
                    + "You can backup your files before proceeding"
                    + "Allow overwriting existing files? (y/n)");
            String answer = console_input.next();
            if (answer.equalsIgnoreCase("Y")) {
                overwritingFilesPermission = true;
            } else {
                return "Program execution terminated by user";
            }
        }
        return outputFileName;
    }
}