package perf.ermolaevnk;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexFinder {
    public static char LINE_ENDING = '\n';

    public static void main(String[] args) throws IOException {
        Pattern regexPattern = Pattern.compile(args[0]);
        Matcher regexMatcher;

        int argNum = 1;
        List<String> inputFiles = new ArrayList<String>();
        while (argNum < args.length - 1) {
            inputFiles.add(args[argNum]);
            argNum++;
        }
        String outputFileName = args[args.length - 1], line = null;

        File outputFile;
        BufferedWriter writer;
        BufferedReader reader;

        try {
            outputFile = new File(outputFileName);
            if (outputFile.createNewFile()) {
                System.out.println(outputFileName + " created");
            } else {
                System.out.println("File " + outputFileName + " already exists and will be overwritten");
//                System.out.println("Overwrite file? (y/n)");
            }
            writer = new BufferedWriter(new FileWriter(outputFileName, false));

            for (String inputFile : inputFiles) {
                reader = new BufferedReader(new FileReader(inputFile));
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