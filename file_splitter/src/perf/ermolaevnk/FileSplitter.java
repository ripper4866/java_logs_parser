package perf.ermolaevnk;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileSplitter {
    public static char LINE_ENDING = '\n';

    public static void main(String[] args) throws IOException {
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
        String outputFileName = FileNameTemplate + resultFilesCounter;
        File outputFile = new File(outputFileName);
        if (outputFile.createNewFile()) {
            System.out.println(outputFileName + " created");
        } else System.out.println("File " + outputFileName + " already exists");
        return outputFileName;
    }
}