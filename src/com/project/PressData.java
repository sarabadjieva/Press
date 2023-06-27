package com.project;

import java.io.Serializable;
import java.util.ArrayList;

public class PressData implements Serializable {
    public static final long serialVersionUID = 1235L;
    public final float publicationProfitPercent;
    public final int discountPublicationsCount;
    public final float discountPercent;
    public final float baseSalary;
    public final float bonusSalaryPercent;
    public final float minIncomeForBonus;
    public float expenses;
    public float income;
    private ArrayList<Order> orders = new ArrayList<>();
    public transient float currBonusSalaryPercent;

    public PressData(float profitPer,
                     int discountCount,
                     float discountPer,
                     float salary,
                     float bonusSalaryPer,
                     float minBonusIncome){
        publicationProfitPercent = profitPer;
        discountPublicationsCount = discountCount;
        discountPercent = discountPer;
        baseSalary = salary;
        bonusSalaryPercent = bonusSalaryPer;
        minIncomeForBonus = minBonusIncome;
    }

    public PressData(){
        this(0,0,0,0,0,0);
    }

    public void setCurrBonusSalaryPercent(){
        float profit = income - expenses;

        if (profit > minIncomeForBonus) {
            currBonusSalaryPercent = bonusSalaryPercent;
        } else {
            currBonusSalaryPercent = 0;
        }
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public float getOrdersExpenses(){
        float all = 0;
        for (Order order: orders
             ) {
            all += order.getExpenses();
        }
        return all;
    }

    public float getOrdersIncome(){
        float all = 0;
        for (Order order: orders
        ) {
            all += order.getIncome();
        }
        return all;
    }

    @Override
    public String toString() {
        return "PressData{" +
                "publicationProfitPercent=" + publicationProfitPercent +
                ", discountPublicationsCount=" + discountPublicationsCount +
                ", discountPercent=" + discountPercent +
                ", baseSalary=" + baseSalary +
                ", bonusSalaryPercent=" + bonusSalaryPercent +
                ", minIncomeForBonus=" + minIncomeForBonus +
                ", expenses=" + expenses +
                ", income=" + income +
                ", orders=" + orders +
                ", currBonusSalaryPercent=" + currBonusSalaryPercent +
                '}';
    }
}
