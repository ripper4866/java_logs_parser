package perf.ermolaevnk;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DelimitersSetter {
    public static char LINE_ENDING = '\n';

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("Too few arguments\n"
                    + "Expecting 3 or more arguments:\n"
                    + "First argument is the regular expression\n"
                    + "Following arguments are input files paths\n"
                    + "Last argument is output file path");
            return;
        }

        String valuesDelimiter = args[0];
        int argNum = 1;
        List<String> inputFiles = new ArrayList<String>();
        while (argNum < args.length - 1) {
            inputFiles.add(args[argNum]);
            argNum++;
        }
        String outputFileName = args[args.length - 1] + ".csv", line;
        String[] values;
        File outputFile;
        BufferedWriter writer;
        BufferedReader reader;

        try {
            outputFile = new File(outputFileName);
            if (outputFile.createNewFile()) {
                System.out.println(outputFileName + " created");
            } else {
                System.out.println("File " + outputFileName + " already exists\n"
                        + "Please allow program to overwrite it, otherwise program will stop execution\n"
                        + "You can backup this file before proceeding\n"
                        + "Allow overwriting existing files? (y/n)");
                Scanner console_input = new Scanner(System.in);
                String answer = console_input.next();
                if (!answer.equalsIgnoreCase("Y")) {
                    System.out.println("Program execution terminated by user");
                    return;
                }
            }
            writer = new BufferedWriter(new FileWriter(outputFileName, false));

            for (String inputFile : inputFiles) {
                reader = new BufferedReader(new FileReader(inputFile));
                while ((line = reader.readLine()) != null) {
                    values = line.split("\\s+");
                    writer.append(String.join(valuesDelimiter, values)).append(String.valueOf(LINE_ENDING));
                }
                reader.close();
            }
            writer.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }
}