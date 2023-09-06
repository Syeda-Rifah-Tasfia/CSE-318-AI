import java.util.Arrays;
import java.util.Vector;

public class Node {
    private Node parent;

    private int numOfClassifications;

    int classUnacc;
    int classAcc;
    int classGood;
    int classVgood;

    private double entropy;
    private Vector<Example> examples;
    Vector<Node> children;

    int[] attrFlags;
    double[] gains;

    int chosenAttribute;
    int chosenAttributeValue;

    int caseFlag;

    boolean isLeaf;

    Node(){
        examples = new Vector<>();
        classUnacc = 0;
        classAcc = 0;
        classGood = 0;
        classVgood = 0;
        numOfClassifications = 0;
        entropy = 0;
        attrFlags = new int[6];
        for(int i = 0; i < 6; i++){
            attrFlags[i] = 1;
        }
        gains = new double[6];
        children = new Vector<>();
        chosenAttribute = -1;
        chosenAttributeValue = -1;
        caseFlag = -1;
        isLeaf = false;
    }

    public void setExamples(Vector<Example> examples){
        this.examples = examples;
    }

    public Vector<Example> getExamples(){
        return examples;
    }

    public void addToExamples(Example example){
        examples.add(example);
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getNumOfClassifications() {
        numOfClassifications = examples.size();
        return numOfClassifications;
    }

    public void setNumOfClassifications(int numOfClassifications) {
        this.numOfClassifications = numOfClassifications;
    }

    public int calculateClassUnacc() {
        for(int i = 0; i < this.examples.size(); i++){
            if(this.examples.get(i).getClassValue() == 0){
                classUnacc++;
            }
        }
        return classUnacc;
    }

    public void setClassUnacc(int classUnacc) {
        this.classUnacc = classUnacc;
    }

    public int calculateClassAcc() {
        for(int i = 0; i < this.examples.size(); i++){
            if(this.examples.get(i).getClassValue() == 1){
                classAcc++;
            }
        }
        return classAcc;
    }

    public void setClassAcc(int classAcc) {
        this.classAcc = classAcc;
    }

    public int calculateClassGood() {
        for(int i = 0; i < this.examples.size(); i++){
            if(this.examples.get(i).getClassValue() == 2){
                classGood++;
            }
        }
        return classGood;
    }

    public void setClassGood(int classGood) {
        this.classGood = classGood;
    }

    public int calculateClassVgood() {
        for(int i = 0; i < this.examples.size(); i++){
            if(this.examples.get(i).getClassValue() == 3){
                classVgood++;
            }
        }
        return classVgood;
    }

    public void setClassVgood(int classVgood) {
        this.classVgood = classVgood;
    }

    int plurality(){
        //get max among unacc, acc, good, vgood
        int max = Integer.MIN_VALUE;
        int maxIndex = 0;
        int[] classifications = new int[4];
        classifications[0] = classUnacc;
        classifications[1] = classAcc;
        classifications[2] = classGood;
        classifications[3] = classVgood;
        for(int i = 0; i < 4; i++){
            if(classifications[i] > max){
                max = classifications[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
    double Entropy(){
        if(this.getNumOfClassifications() == 0){
            entropy = 0;
            return 0;
        }
        entropy = 0;
        double pUnacc = 1.0 * this.classUnacc/getNumOfClassifications();
        double pAcc = 1.0 * this.classAcc/getNumOfClassifications();
        double pGood = 1.0 * this.classGood/getNumOfClassifications();
        double pVgood = 1.0 * this.classVgood/getNumOfClassifications();
        if(pUnacc != 0){
            entropy += pUnacc * (Math.log(pUnacc)/Math.log(4));
        }
        if(pAcc != 0){
            entropy += pAcc * (Math.log(pAcc)/Math.log(4));
        }
        if(pGood != 0){
            entropy += pGood * (Math.log(pGood)/Math.log(4));
        }
        if(pVgood != 0){
            entropy += pVgood * (Math.log(pVgood)/Math.log(4));
        }
        if(entropy != 0){
            entropy = -entropy;
        }
        return entropy;
    }

    int InfoGain(){
        Vector<Node[]> array = new Vector<>();
        Node[] forBuying = new Node[4];
        Node[] forMaint = new Node[4];
        Node[] forDoors = new Node[4];
        Node[] forPersons = new Node[3];
        Node[] forLug_boot = new Node[3];
        Node[] forSafety = new Node[3];
        array.add(forBuying);
        array.add(forMaint);
        array.add(forDoors);
        array.add(forPersons);
        array.add(forLug_boot);
        array.add(forSafety);
        if(attrFlags[0] == 1){
            double remainder = 0;
            double entropyTemp = 0;
            for(int i = 0; i < 4; i++){
                forBuying[i] = new Node();
                forBuying[i].attrFlags = Arrays.copyOf(this.attrFlags, this.attrFlags.length);
                forBuying[i].gains = Arrays.copyOf(this.gains, this.gains.length);
            }
            for(int i = 0; i < 4; i++){
                for(int j = 0; j < examples.size(); j++){
                    Example ex = examples.get(j);
                    if(ex.getAttr1().getValue() == i){
                        forBuying[i].addToExamples(ex);
                        forBuying[i].setNumOfClassifications(forBuying[i].getExamples().size());
                        if(ex.getClassValue() == 0){
                            forBuying[i].setClassUnacc(forBuying[i].classUnacc + 1);
                        } else if(ex.getClassValue() == 1){
                            forBuying[i].setClassAcc(forBuying[i].classAcc + 1);
                        } else if(ex.getClassValue() == 2){
                            forBuying[i].setClassGood(forBuying[i].classGood + 1);
                        } else if(ex.getClassValue() == 3){
                            forBuying[i].setClassVgood(forBuying[i].classVgood + 1);
                        }
                    }
                }
                //print number of each class
                entropyTemp = forBuying[i].Entropy();

                /*System.out.println("Number of unacc classifications for buying " + i + ": " + forBuying[i].classUnacc);
                System.out.println("Number of acc classifications for buying " + i + ": " + forBuying[i].classAcc);
                System.out.println("Number of good classifications for buying " + i + ": " + forBuying[i].classGood);
                System.out.println("Number of vgood classifications for buying " + i + ": " + forBuying[i].classVgood);
                System.out.println("Entropy for buying " + i + ": " + entropyTemp);*/

                if(entropyTemp != 0.0){
                    remainder += (1.0 * forBuying[i].getNumOfClassifications() / this.getNumOfClassifications()) * entropyTemp;
                }
                //System.out.println("Remainder for buying " + i + ": " + remainder);
            }
            gains[0] = entropy - remainder;
        }
        if(attrFlags[1] == 1){
            double remainder = 0;
            double entropyTemp = 0;
            for(int i = 0; i < 4; i++){
                forMaint[i] = new Node();
                forMaint[i].attrFlags = Arrays.copyOf(this.attrFlags, this.attrFlags.length);
                forMaint[i].gains = Arrays.copyOf(this.gains, this.gains.length);
            }
            for(int i = 0; i < 4; i++){
                for(int j = 0; j < examples.size(); j++){
                    Example ex = examples.get(j);
                    if(ex.getAttr2().getValue() == i){
                        forMaint[i].addToExamples(ex);
                        forMaint[i].setNumOfClassifications(forMaint[i].getNumOfClassifications() + 1);
                        if(ex.getClassValue() == 0){
                            forMaint[i].setClassUnacc(forMaint[i].classUnacc + 1);
                        } else if(ex.getClassValue() == 1){
                            forMaint[i].setClassAcc(forMaint[i].classAcc + 1);
                        } else if(ex.getClassValue() == 2){
                            forMaint[i].setClassGood(forMaint[i].classGood + 1);
                        } else if(ex.getClassValue() == 3){
                            forMaint[i].setClassVgood(forMaint[i].classVgood + 1);
                        }
                    }
                }
                entropyTemp = forMaint[i].Entropy();
                remainder += (1.0 * forMaint[i].getNumOfClassifications()/this.getNumOfClassifications()) * entropyTemp;
            }
            gains[1] = entropy - remainder;
        }
        if(attrFlags[2] == 1){
            double remainder = 0;
            double entropyTemp = 0;
            for(int i = 0; i < 4; i++){
                forDoors[i] = new Node();
                forDoors[i].attrFlags = Arrays.copyOf(this.attrFlags, this.attrFlags.length);
                forDoors[i].gains = Arrays.copyOf(this.gains, this.gains.length);
            }
            for(int i = 0; i < 4; i++){
                for(int j = 0; j < examples.size(); j++){
                    Example ex = examples.get(j);
                    if(ex.getAttr3().getValue() == i){
                        forDoors[i].addToExamples(ex);
                        forDoors[i].setNumOfClassifications(forDoors[i].getNumOfClassifications() + 1);
                        if(ex.getClassValue() == 0){
                            forDoors[i].setClassUnacc(forDoors[i].classUnacc + 1);
                        } else if(ex.getClassValue() == 1){
                            forDoors[i].setClassAcc(forDoors[i].classAcc + 1);
                        } else if(ex.getClassValue() == 2){
                            forDoors[i].setClassGood(forDoors[i].classGood + 1);
                        } else if(ex.getClassValue() == 3){
                            forDoors[i].setClassVgood(forDoors[i].classVgood + 1);
                        }
                    }
                }
                entropyTemp = forDoors[i].Entropy();
                remainder += (1.0 * forDoors[i].getNumOfClassifications()/this.getNumOfClassifications()) * entropyTemp;
            }
            gains[2] = entropy - remainder;
        }
        if(attrFlags[3] == 1){
            double remainder = 0;
            double entropyTemp = 0;
            for(int i = 0; i < 3; i++){
                forPersons[i] = new Node();
                forPersons[i].attrFlags = Arrays.copyOf(this.attrFlags, this.attrFlags.length);
                forPersons[i].gains = Arrays.copyOf(this.gains, this.gains.length);
            }
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < examples.size(); j++){
                    Example ex = examples.get(j);
                    if(ex.getAttr4().getValue() == i){
                        forPersons[i].addToExamples(ex);
                        forPersons[i].setNumOfClassifications(forPersons[i].getNumOfClassifications() + 1);
                        if(ex.getClassValue() == 0){
                            forPersons[i].setClassUnacc(forPersons[i].classUnacc + 1);
                        } else if(ex.getClassValue() == 1){
                            forPersons[i].setClassAcc(forPersons[i].classAcc + 1);
                        } else if(ex.getClassValue() == 2){
                            forPersons[i].setClassGood(forPersons[i].classGood + 1);
                        } else if(ex.getClassValue() == 3){
                            forPersons[i].setClassVgood(forPersons[i].classVgood + 1);
                        }
                    }
                }
                entropyTemp = forPersons[i].Entropy();
                remainder += (1.0 * forPersons[i].getNumOfClassifications()/this.getNumOfClassifications()) * entropyTemp;
            }
            gains[3] = entropy - remainder;
        }
        if(attrFlags[4] == 1){
            double remainder = 0;
            double entropyTemp = 0;
            for(int i = 0; i < 3; i++){
                forLug_boot[i] = new Node();
                forLug_boot[i].attrFlags = Arrays.copyOf(this.attrFlags, this.attrFlags.length);
                forLug_boot[i].gains = Arrays.copyOf(this.gains, this.gains.length);
            }
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < examples.size(); j++){
                    Example ex = examples.get(j);
                    if(ex.getAttr5().getValue() == i){
                        forLug_boot[i].addToExamples(ex);
                        forLug_boot[i].setNumOfClassifications(forLug_boot[i].getNumOfClassifications() + 1);
                        if(ex.getClassValue() == 0){
                            forLug_boot[i].setClassUnacc(forLug_boot[i].classUnacc + 1);
                        } else if(ex.getClassValue() == 1){
                            forLug_boot[i].setClassAcc(forLug_boot[i].classAcc + 1);
                        } else if(ex.getClassValue() == 2){
                            forLug_boot[i].setClassGood(forLug_boot[i].classGood + 1);
                        } else if(ex.getClassValue() == 3){
                            forLug_boot[i].setClassVgood(forLug_boot[i].classVgood + 1);
                        }
                    }
                }
                entropyTemp = forLug_boot[i].Entropy();
                remainder += (1.0 * forLug_boot[i].getNumOfClassifications()/this.getNumOfClassifications()) * entropyTemp;
            }
            gains[4] = entropy - remainder;
        }
        if(attrFlags[5] == 1){
            double remainder = 0;
            double entropyTemp = 0;
            for(int i = 0; i < 3; i++){
                forSafety[i] = new Node();
                //copy attr
                forSafety[i].attrFlags = Arrays.copyOf(this.attrFlags, this.attrFlags.length);
                //copy gains
                forSafety[i].gains = Arrays.copyOf(this.gains, this.gains.length);
            }
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < examples.size(); j++){
                    Example ex = examples.get(j);
                    if(ex.getAttr6().getValue() == i){
                        forSafety[i].addToExamples(ex);
                        forSafety[i].setNumOfClassifications(forSafety[i].getNumOfClassifications() + 1);
                        if(ex.getClassValue() == 0){
                            forSafety[i].setClassUnacc(forSafety[i].classUnacc + 1);
                        } else if(ex.getClassValue() == 1){
                            forSafety[i].setClassAcc(forSafety[i].classAcc + 1);
                        } else if(ex.getClassValue() == 2){
                            forSafety[i].setClassGood(forSafety[i].classGood + 1);
                        } else if(ex.getClassValue() == 3){
                            forSafety[i].setClassVgood(forSafety[i].classVgood + 1);
                        }
                    }
                }
                entropyTemp = forSafety[i].Entropy();
                remainder += (1.0 * forSafety[i].getNumOfClassifications()/this.getNumOfClassifications()) * entropyTemp;
            }
            gains[5] = entropy - remainder;
        }



        //print gains to debug
        /*for(int i = 0; i < 6; i++){
            System.out.println("Gain for attr " + i + ": " + gains[i]);
        }*/

        //get index for max value in gains:
        double maxGain = Integer.MIN_VALUE;
        int maxAttr = 0;
        for(int i = 0; i < 6; i++){
            //flags[i] = attrFlags[i];
            if(gains[i] >= maxGain){
                maxGain = gains[i];
                maxAttr = i;
            }
        }

        gains[maxAttr] = -1;
        attrFlags[maxAttr] = 0;
        chosenAttribute = maxAttr;
        int x = array.get(maxAttr).length;
        for(int i = 0; i < x; i++){
            if(array.get(maxAttr)[i] != null){
                this.children.add(array.get(maxAttr)[i]);
                array.get(maxAttr)[i].setParent(this);
                array.get(maxAttr)[i].attrFlags[i] = this.attrFlags[i];
                array.get(maxAttr)[i].gains[i] = this.gains[i];
                array.get(maxAttr)[i].chosenAttributeValue = i; //which value of chosen attribute creates this child
            }
        }

        array.removeAllElements();
        return maxAttr;
    }

    void printNode(){
        System.out.println("Node: ");
        System.out.println("Number of classifications: " + getNumOfClassifications());
        System.out.println("Number of unacc classifications: " + classUnacc);
        System.out.println("Number of acc classifications: " + classAcc);
        System.out.println("Number of good classifications: " + classGood);
        System.out.println("Number of vgood classifications: " + classVgood);
        System.out.println("Number of examples: " + examples.size());
        /*System.out.println("Examples: ");
        for(int i = 0; i < examples.size(); i++){
            System.out.println("Example " + i + ": ");
            System.out.println("Attribute 1: " + examples.get(i).getAttr1().getValue());
            System.out.println("Attribute 2: " + examples.get(i).getAttr2().getValue());
            System.out.println("Attribute 3: " + examples.get(i).getAttr3().getValue());
            System.out.println("Attribute 4: " + examples.get(i).getAttr4().getValue());
            System.out.println("Attribute 5: " + examples.get(i).getAttr5().getValue());
            System.out.println("Attribute 6: " + examples.get(i).getAttr6().getValue());
            System.out.println("Classification: " + examples.get(i).getClassValue());
        }*/
    }


    public int getChosenAttribute() {
        return chosenAttribute;
    }

    public void setChosenAttribute(int chosenAttribute) {
        this.chosenAttribute = chosenAttribute;
    }
}

