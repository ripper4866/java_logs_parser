package perf.ermolaevnk;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexFinder {
    public static char LINE_ENDING = '\n';

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.out.println("Too few arguments\n"
                    + "Expecting 3 or more arguments:\n"
                    + "First argument is the regular expression\n"
                    + "Second argument is input files path\n"
                    + "Third argument is output file path");
            return;
        }

        Pattern regexPattern = Pattern.compile(args[0]);
        Matcher regexMatcher;

        File inputFilesPath = new File(args[1]);
        File[] inputFiles = inputFilesPath.listFiles();
        String outputFileName = args[2] + ".log", line;

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

            assert inputFiles != null;
            for (File inputFile : inputFiles) {
                System.out.println("Reading " + inputFile.getAbsolutePath());
                reader = new BufferedReader(new FileReader(inputFile.getAbsolutePath()));
                while ((line = reader.readLine()) != null) {
                    regexMatcher = regexPattern.matcher(line);
                    while (regexMatcher.find()) {
                        writer.append(regexMatcher.group()).append(String.valueOf(LINE_ENDING));
                    }
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