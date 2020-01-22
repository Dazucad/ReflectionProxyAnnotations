package ru.lokyanvs;

public class TestInterfaceClass implements ProxyTestInterface {
    public static final String MONDAY = "MONDAY";
    public static final String TUESDAY = "TUESDAY";
    public static final String WEDNESDAY = "WEDNESDAY";
    public static final String THURSDAY = "what?";
    public static final String FRIDAY = "FRIDAY";
    public static final String SATURDAY = "SATURDAY";
    public static final String SUNDAY = "SUNDAY";

    private String textField;
    private int anInt;
    private boolean aBoolean;

    TestInterfaceClass(String textField, int anInt, boolean aBoolean) {
        this.textField = textField;
        this.anInt = anInt;
        this.aBoolean = aBoolean;
    }

    private String getTextField() {
        return textField;
    }

    public int getAnInt() {
        return anInt;
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    @Override
    //@Cache
    public int sum(int a, int b) {
        return a + b;
    }

    @Override
    @Cache
    public int pow(int a, int b) {
        int pow = 1;
        for (int i = 1; i <= b; i++)
            pow = pow * a;
        return pow;
    }
}
