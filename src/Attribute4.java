//persons: 2, 4, more

public class Attribute4 {
    int id;
    int numOfValues;
    int value;

    Attribute4(String attrValue) {
        id = 4;
        numOfValues = 3;
        if(attrValue.equals("2")) {
            value = 0;
        } else if(attrValue.equals("4")) {
            value = 1;
        } else if(attrValue.equals("more")) {
            value = 2;
        } else {
            System.out.println("Error: Invalid attribute4 value " + attrValue);
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