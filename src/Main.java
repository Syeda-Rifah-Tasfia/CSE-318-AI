import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static List<String> readFromFile(String fileName) throws IOException {
        List<String> data = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            data.add(line);
        }

        reader.close();
        return data;
    }

    public static void writeToFile(String fileName, List<String> data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        for (String item : data) {
            writer.write(item);
            writer.newLine();
        }

        writer.close();
    }

    public static List<String> removeLastTerm(List<String> data) {
        List<String> newData = new ArrayList<>();
        for (String line : data) {
            String[] parts = line.split(",");
            if (parts.length > 1) {
                StringBuilder newLine = new StringBuilder();
                for (int i = 0; i < parts.length - 1; i++) {
                    newLine.append(parts[i]);
                    if (i < parts.length - 2) {
                        newLine.append(",");
                    }
                }
                newData.add(newLine.toString());
            }
        }
        return newData;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter number of iterations: ");
        int iterations = scn.nextInt();
        double[] accuracies = new double[iterations];
        double average = 0;
        for(int k = 0; k < iterations; k++){
            String fullDataset = "InOut/carTest.data";
            String learningSet = "InOut/learner.data";
            String testingSet = "InOut/tester.data"; //for testing
            String testingSet1 = "InOut/tester1.data"; //for matching

            try {
                List<String> data = readFromFile(fullDataset);

                Collections.shuffle(data);
                int datasetSize = data.size();
                int learningSetSize = (int) (datasetSize * 0.8);
                List<String> learningSetData = data.subList(0, learningSetSize);
                List<String> testingSetData_1 = data.subList(learningSetSize, datasetSize);

                List<String> testingSetData = removeLastTerm(testingSetData_1);

                writeToFile(learningSet, learningSetData);
                writeToFile(testingSet, testingSetData); //without output, for testing
                writeToFile(testingSet1, testingSetData_1); //with output, for matching
            } catch (Exception e) {
                e.printStackTrace();
            }


            Node root = new Node();
            root.setParent(null);
            int a = 0;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(learningSet));
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] splitArr = line.split(",");
                    Attribute1 attr1 = new Attribute1(splitArr[0]);
                    Attribute2 attr2 = new Attribute2(splitArr[1]);
                    Attribute3 attr3 = new Attribute3(splitArr[2]);
                    Attribute4 attr4 = new Attribute4(splitArr[3]);
                    Attribute5 attr5 = new Attribute5(splitArr[4]);
                    Attribute6 attr6 = new Attribute6(splitArr[5]);
                    String classification = splitArr[6];
                    Example example = new Example(attr1, attr2, attr3, attr4, attr5, attr6, classification);
                    root.addToExamples(example);
                    a++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //System.out.println("Total learner data = " + a);
            root.setNumOfClassifications(root.getExamples().size());
        /*root.calculateClassUnacc();
        root.calculateClassAcc();
        root.calculateClassGood();
        root.calculateClassVgood();
        System.out.println("Entropy = " + root.Entropy());
        double entropy = root.Entropy();
        int maxGainAttr = root.InfoGain();
        System.out.println("Max Gain Attribute = " + maxGainAttr);
*/
            Dtree dtree = new Dtree(root);
            int[] flags = new int[6];
            for (int i = 0; i < 6; i++) {
                flags[i] = 1;
            }
            Node node = dtree.Decision_Tree_Learning(root, flags, null);

            //print tree
            //dtree.printTree(node);
            //root.printNode();


            //print children of root and their gains
        /*for(int i = 0; i < root.children.size(); i++){
            System.out.println("For child " + i);
            System.out.println("Child " + i + " max gain attr = " + root.children.get(i).InfoGain());
            //print their children and their gains
            for(int j = 0; j < root.children.get(i).children.size(); j++){
                System.out.println("For child " + i + " " + j);
                System.out.println("Child " + j + " max gain attr = " + root.children.get(i).children.get(j).InfoGain());
            }
        }*/

            Node testRoot = new Node();
            testRoot.setParent(null);
            try {
                BufferedReader reader = new BufferedReader(new FileReader(testingSet));
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] splitArr = line.split(",");
                    Attribute1 attr1 = new Attribute1(splitArr[0]);
                    Attribute2 attr2 = new Attribute2(splitArr[1]);
                    Attribute3 attr3 = new Attribute3(splitArr[2]);
                    Attribute4 attr4 = new Attribute4(splitArr[3]);
                    Attribute5 attr5 = new Attribute5(splitArr[4]);
                    Attribute6 attr6 = new Attribute6(splitArr[5]);
                    Example example = new Example(attr1, attr2, attr3, attr4, attr5, attr6, "");
                    testRoot.addToExamples(example);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Node matcher = new Node();
            matcher.setParent(null);
            try {
                BufferedReader reader = new BufferedReader(new FileReader(testingSet1));
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] splitArr = line.split(",");
                    Attribute1 attr1 = new Attribute1(splitArr[0]);
                    Attribute2 attr2 = new Attribute2(splitArr[1]);
                    Attribute3 attr3 = new Attribute3(splitArr[2]);
                    Attribute4 attr4 = new Attribute4(splitArr[3]);
                    Attribute5 attr5 = new Attribute5(splitArr[4]);
                    Attribute6 attr6 = new Attribute6(splitArr[5]);
                    String classification = splitArr[6];
                    Example example = new Example(attr1, attr2, attr3, attr4, attr5, attr6, classification);
                    matcher.addToExamples(example);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //System.out.println("Total tester data = " + testRoot.getExamples().size());
            int match = 0;
            for (int i = 0; i < testRoot.getExamples().size(); i++) {
                int result = dtree.Decision_Tree_Testing(testRoot.getExamples().get(i), node);
                testRoot.getExamples().get(i).setClassValue(result);
                if (result == matcher.getExamples().get(i).getClassValue()) {
                    match++;
                }
            }
            double accuracy = ((double) match / testRoot.getExamples().size()) * 100;
            accuracies[k] = accuracy;
            average += accuracy;
            //System.out.println("Match = " + match);
        }

        average = average/iterations;
        //get standard deviation
        double sum = 0;
        for(int i = 0; i < iterations; i++){
            sum += Math.pow((accuracies[i] - average), 2);
        }
        double standardDeviation = Math.sqrt(sum/iterations);
        System.out.println("Average accuracy = " + average + "%");
        System.out.println("Standard deviation = " + standardDeviation);
    }
}