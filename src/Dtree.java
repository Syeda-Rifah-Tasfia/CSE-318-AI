import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;

public class Dtree {
    Node root;

    int flag; //for 4 cases of DT


    int callCount;
    int depth;
    public Dtree(Node node) {
        root = node;
        root.setParent(null);
        this.flag = -1;
        callCount = 0;
        depth = 0;
    }

    public Node Decision_Tree_Learning(Node root, int[] flags, Node parent){
        callCount++;
        //System.out.println("Call " + callCount);
        if(root.getParent() == null){
            root.chosenAttributeValue = -1;
            root.calculateClassUnacc();
            root.calculateClassAcc();
            root.calculateClassGood();
            root.calculateClassVgood();
        }
        int x = root.getNumOfClassifications();
        int numOfAttrs = 0;
        for(int i = 0; i < flags.length; i++){
            if(flags[i] == 1){
                numOfAttrs++;
            }
        }
        if(x == 0){
            flag = 0;
            parent.caseFlag = 0;
            parent.children.removeAllElements();
            parent.isLeaf = true;
            return parent;
        }
        else if((x == root.classUnacc) || (x == root.classAcc) || (x == root.classGood) || (x == root.classVgood)){
            flag = 1;
            root.caseFlag = 1;
            root.isLeaf = true;
            if(!root.children.isEmpty()){
                root.children.removeAllElements();
            }
            return root;
        }
        else if(numOfAttrs == 0){
            root.caseFlag = 2;
            root.isLeaf = true;
            flag = 2;
            if(!root.children.isEmpty()){
                root.children.removeAllElements();
            }
            return root;
        }
        else{
            flag = 3;
            root.caseFlag = 3;
            int maxGainAttr = root.InfoGain();
            depth++;
            //System.out.println("Max Gain Attribute at call " + callCount + " = " + maxGainAttr);
            for(int i = 0; i < 6; i++){
                flags[i] = root.attrFlags[i];
            }
            for(int i = 0; i < root.children.size(); i++){
                Decision_Tree_Learning(root.children.get(i), root.children.get(i).attrFlags, root);
            }
            return root;
            //0 1 2 3 4 5 - buying maint doors persons lug_boot safety
        }
    }

    int Decision_Tree_Testing(Example example, Node rootDT){
        int result = -1;
        Node currNode = rootDT;
        while(!currNode.isLeaf){
            int attrId = currNode.chosenAttribute;
            int attrValue = -1;
            for(int i = 0; i < currNode.children.size(); i++){
                if(attrId == 0){
                    attrValue = example.getAttr1().getValue();
                }
                else if(attrId == 1){
                    attrValue = example.getAttr2().getValue();
                }
                else if(attrId == 2){
                    attrValue = example.getAttr3().getValue();
                }
                else if(attrId == 3){
                    attrValue = example.getAttr4().getValue();
                }
                else if(attrId == 4){
                    attrValue = example.getAttr5().getValue();
                }
                else if(attrId == 5){
                    attrValue = example.getAttr6().getValue();
                }
                if(currNode.children.get(i).chosenAttributeValue == attrValue){
                    currNode = currNode.children.get(i);
                }
            }
            if(currNode.isLeaf){
                if(currNode.caseFlag == 0 || currNode.caseFlag == 1 || currNode.caseFlag == 2){
                    result = currNode.plurality();
                }
                break;
            }
        }
        return result;
    }

    int nodeCount = 1;
    void printTree(Node node){
        if(node.getParent() == null){
            System.out.println("Depth is " + depth);
            System.out.println("Root");
            node.printNode();
        }

        for(int i = 0; i < node.children.size(); i++){
            nodeCount++;
            System.out.println("Child " + nodeCount);
            node.children.get(i).printNode();
            if(node.children.isEmpty()){
                return;
            }
            else{
                printTree(node.children.get(i));
            }
        }
    }
}