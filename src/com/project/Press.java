package com.project;

import com.project.helpers.*;
import com.project.publication.PublicationType;

import java.util.*;

public class Press {
    public static PressData data;
    private PressOrdersProcess pressOrders;
    private ArrayList<Employee> employees;

    public Press(ArrayList<PrintMachine> pms) {

        data = ClientInput.getInstance().deserializePressData();
        employees = ClientInput.getInstance().deserializeEmployees();
        pressOrders = new PressOrdersProcess(pms);
        checkEmployeeBaseSalaries();
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(boolean isManager, String name){

        employees.add(new Employee(isManager, name , data.baseSalary));
        logFinances();
    }

    public void addMachine(int maxPages, int pagesPerMin){

        pressOrders.addMachine(maxPages, pagesPerMin);
    }

    public void makeOrder(){
        PublicationType type = ClientInput.getInstance().inputPublicationType();
        data.addOrder(pressOrders.makeOrder(type));
        calculateFinances();
        logFinances();
    }

    private void logFinances(){

        Logger.getInstance().logPressWork("[EXPENSES]: " + data.expenses);
        Logger.getInstance().logPressWork("[INCOME]: " + data.income);
        Logger.getInstance().logPressWork("[PROFIT]: " + (data.income - data.expenses));
        Logger.getInstance().logPressWork("[EMPLOYEES]: " + employees.size());
        Logger.getInstance().logPressWork("[SALARY]: " + " Salary: " + data.baseSalary + ", salary with bonus: " + (data.baseSalary + data.baseSalary*data.currBonusSalaryPercent));
    }

    private void calculateFinances(){

        getExpenses();
        getIncome();
        data.setCurrBonusSalaryPercent();
    }

    private void getExpenses(){

        float all = 0;
        for (Employee e: employees
             ) {
            all += e.getSalary();
        }

        all += data.getOrdersExpenses();
        data.expenses = all;
    }

    private void getIncome(){

        data.income = data.getOrdersIncome();
    }

    private void checkEmployeeBaseSalaries(){
        for (Employee e: employees
             ) {
            if (e.getBaseSalary() != data.baseSalary){
                e.setBaseSalary(data.baseSalary);
            }
        }
    }
}
