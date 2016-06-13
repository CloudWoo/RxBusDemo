package com.haomiao.cloud.rxbusdemo;

/**
 * Project RxBusDemo.
 * PackageName com.haomiao.cloud.rxbusdemo.
 * Created by Cloud on 16/6/13.
 * Instruction
 */
public class OtherEvent {

    String name;
    int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public OtherEvent(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
