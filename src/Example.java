//classifications: unacc, acc, good, vgood
public class Example {
    private Attribute1 attr1;
    private Attribute2 attr2;
    private Attribute3 attr3;
    private Attribute4 attr4;
    private Attribute5 attr5;
    private Attribute6 attr6;

    private int classValue;

    Example(Attribute1 attr1, Attribute2 attr2, Attribute3 attr3, Attribute4 attr4, Attribute5 attr5, Attribute6 attr6, String classification){
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.attr3 = attr3;
        this.attr4 = attr4;
        this.attr5 = attr5;
        this.attr6 = attr6;
        if(classification.equals("unacc")){
            classValue = 0;
        }
        else if(classification.equals("acc")){
            classValue = 1;
        }
        else if(classification.equals("good")){
            classValue = 2;
        }
        else if(classification.equals("vgood")){
            classValue = 3;
        }
        else if(classification.equals("")){
            classValue = -1;
        }
        else{
            System.out.println("Error: Invalid classification value");
        }
    }

    public int getClassValue() {
        return classValue;
    }

    public void setClassValue(int classValue) {
        this.classValue = classValue;
    }

    public Attribute1 getAttr1() {
        return attr1;
    }

    public void setAttr1(Attribute1 attr1) {
        this.attr1 = attr1;
    }

    public Attribute2 getAttr2() {
        return attr2;
    }

    public void setAttr2(Attribute2 attr2) {
        this.attr2 = attr2;
    }

    public Attribute3 getAttr3() {
        return attr3;
    }

    public void setAttr3(Attribute3 attr3) {
        this.attr3 = attr3;
    }

    public Attribute4 getAttr4() {
        return attr4;
    }

    public void setAttr4(Attribute4 attr4) {
        this.attr4 = attr4;
    }

    public Attribute5 getAttr5() {
        return attr5;
    }

    public void setAttr5(Attribute5 attr5) {
        this.attr5 = attr5;
    }

    public Attribute6 getAttr6() {
        return attr6;
    }

    public void setAttr6(Attribute6 attr6) {
        this.attr6 = attr6;
    }

    void printExample(){
        System.out.println("Attribute 1: " + attr1.getValue());
        System.out.println("Attribute 2: " + attr2.getValue());
        System.out.println("Attribute 3: " + attr3.getValue());
        System.out.println("Attribute 4: " + attr4.getValue());
        System.out.println("Attribute 5: " + attr5.getValue());
        System.out.println("Attribute 6: " + attr6.getValue());
        System.out.println("Class: " + classValue);
    }
}