//maint: vhigh, high, med, low

public class Attribute2 {
    int id;
    int numOfValues;
    int value;

    Attribute2(String attrValue) {
        id = 2;
        numOfValues = 4;
        if(attrValue.equals("vhigh")) {
            value = 0;
        } else if(attrValue.equals("high")) {
            value = 1;
        } else if(attrValue.equals("med")) {
            value = 2;
        } else if(attrValue.equals("low")) {
            value = 3;
        } else {
            System.out.println("Error: Invalid attribute2 value " + attrValue);
        }
    }

    public int getId() {
        return id;
    }

    public int getNumOfValues() {
        return numOfValues;
    }

    public int getValue() {
        return value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumOfValues(int numOfValues) {
        this.numOfValues = numOfValues;
    }

    public void setValue(int value) {
        this.value = value;
    }
}