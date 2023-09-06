//doors: 2, 3, 4, 5more

public class Attribute3 {
    int id;
    int numOfValues;
    int value;

    Attribute3(String attrValue) {
        id = 3;
        numOfValues = 4;
        if(attrValue.equals("2")) {
            value = 0;
        } else if(attrValue.equals("3")) {
            value = 1;
        } else if(attrValue.equals("4")) {
            value = 2;
        } else if(attrValue.equals("5more")) {
            value = 3;
        } else {
            System.out.println("Error: Invalid attribute3 value" + attrValue);
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