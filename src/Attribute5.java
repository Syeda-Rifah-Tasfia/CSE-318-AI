//lug_boot: small, med, big

public class Attribute5 {
    int id;
    int numOfValues;
    int value;

    Attribute5(String attrValue) {
        id = 5;
        numOfValues = 3;
        if(attrValue.equals("small")) {
            value = 0;
        } else if(attrValue.equals("med")) {
            value = 1;
        } else if(attrValue.equals("big")) {
            value = 2;
        } else {
            System.out.println("Error: Invalid attribute5 value" + attrValue);
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