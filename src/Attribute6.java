//safety: low, med, high

public class Attribute6 {
    int id;
    int numOfValues;
    int value;

    Attribute6(String attrValue) {
        id = 6;
        numOfValues = 4;
        if(attrValue.equals("low")) {
            value = 0;
        } else if(attrValue.equals("med")) {
            value = 1;
        } else if(attrValue.equals("high")) {
            value = 2;
        } else {
            System.out.println("Error: Invalid attribute6 value" + attrValue);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumOfValues() {
        return numOfValues;
    }

    public void setNumOfValues(int numOfValues) {
        this.numOfValues = numOfValues;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}