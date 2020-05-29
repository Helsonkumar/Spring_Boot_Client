package com.helson.spring_boot.springboot_client_app;

import org.springframework.stereotype.Component;

@Component
public class Profile {

    private String name;
    private int age;
    private String city;

    public Profile(String name, int age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public Profile(){

    }


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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }
}
