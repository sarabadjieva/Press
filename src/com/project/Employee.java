package com.project;

import java.io.Serializable;

public class Employee implements Serializable {
    public static final long serialVersionUID = 1234L;
    private final boolean isManager;
    private final String name;
    private float baseSalary;

    public Employee(boolean isMng, String n, float salary) {
        isManager = isMng;
        name = n;
        baseSalary = salary;
    }

    public float getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(float baseSalary) {
        this.baseSalary = baseSalary;
    }

    public float getSalary() {
        if(isManager){
            return baseSalary + baseSalary*Press.data.currBonusSalaryPercent;
        }
        return baseSalary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "isManager=" + isManager +
                ", name='" + name + '\'' +
                ", baseSalary=" + baseSalary +
                '}';
    }
}
