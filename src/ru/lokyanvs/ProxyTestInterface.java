package ru.lokyanvs;

public interface ProxyTestInterface {
    //@Cache
    int sum(int a, int b);

    //если не указывать аннотацию в интерфейсе и указать в классе, то она не будет работать
    //@Cache
    int pow(int a, int b);
}
