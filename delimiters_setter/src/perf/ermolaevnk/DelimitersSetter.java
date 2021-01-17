package perf.ermolaevnk;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DelimitersSetter {
    public static char LINE_ENDING = '\n';

    public static void main(String[] args) throws IOException {
        String valuesDelimiter = args[0];
        int argNum = 1;
        List<String> inputFiles = new ArrayList<String>();
        while (argNum < args.length - 1) {
            inputFiles.add(args[argNum]);
            argNum++;
        }
        String outputFileName = args[args.length - 1], line = null;
        String[] values;
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

            for (int i = 0; i < inputFiles.size(); i++) {
                reader = new BufferedReader(new FileReader(inputFiles.get(i)));
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