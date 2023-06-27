package com.project.helpers;

import com.project.Employee;
import com.project.PressData;
import com.project.publication.*;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientInput {
    private static ClientInput input = null;
    private final String employeesPath = "files/employees.ser";
    private final String dataPath = "files/data.ser";

    public static synchronized ClientInput getInstance(){
        if(input == null)
            input = new ClientInput();
        return input;
    }

    private ClientInput() { }

    public PublicationType inputPublicationType(){
        PublicationType type = PublicationType.Normal;
        try{
            Scanner scanner = new Scanner(System.in);
            String publicationType;

            do{
                System.out.println("Enter type of publication:");
            } while ((publicationType = scanner.nextLine()).isBlank());

           type = PublicationType.fromString(publicationType);

        }
        catch (InputMismatchException e){
            e.printStackTrace();
        }

        return type;
    }

    public Publication inputPublication(PublicationType type){
        try{
            Scanner scanner = new Scanner(System.in);

            int pCount = 0;
            String publicationName;
            String pType = "";
            String pSize = "";

            do{
                System.out.println("Enter name of publication:");
            } while ((publicationName = scanner.nextLine()).isBlank());

            if (type != PublicationType.Poster){
                do{
                    System.out.println("Enter pages count (positive number): ");
                } while((pCount = scanner.nextInt()) < 0);
            }

            if (type == PublicationType.Normal){
                do{
                    System.out.println("Enter paper type (Default/Glossy/Newspaper):");
                } while ((pType = scanner.nextLine()).isBlank());

                do{
                    System.out.println("Enter paper size (A1-A5):");
                } while ((pSize = scanner.nextLine()).isBlank());
            }

            return switch (type) {
                case Normal -> new Publication(publicationName, pCount, pType, pSize);
                case Book -> new Book(publicationName, pCount);
                case Poster -> new Poster(publicationName);
            };

        }
        catch (InputMismatchException e){
            e.printStackTrace();
        }

        return null;
    }

    public void inputPressData(){
        float publicationProfitPer;
        int discountPublicationsCount;
        float clientDiscountPer;
        float baseSalary;
        float bonusSalaryPer;
        float minProfitForBonus;

        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("State the profit percent for a publication: ");
            while ((publicationProfitPer = scanner.nextInt()) <= 0) {
                System.out.println("Enter positive number!");
            }

            System.out.println("State the minimum count for publications in order to get discount: ");
            while ((discountPublicationsCount = scanner.nextInt()) <= 0) {
                System.out.println("Enter positive number!");
            }

            System.out.println("State the discount percent for clients: ");
            while ((clientDiscountPer = scanner.nextFloat()) <= 0) {
                System.out.println("Enter positive number!");
            }

            System.out.println("State the base salary: ");
            while ((baseSalary = scanner.nextFloat()) <= 0) {
                System.out.println("Enter positive number!");
            }

            System.out.println("State the salary bonus percent: ");
            while ((bonusSalaryPer = scanner.nextFloat()) <= 0) {
                System.out.println("Enter positive number!");
            }

            System.out.println("State the minimum profit in order to get a bonus: ");
            while ((minProfitForBonus = scanner.nextFloat()) <= 0) {
                System.out.println("Enter positive number!");
            }

            serializePressData(new PressData(publicationProfitPer / 100,
                    discountPublicationsCount,
                    clientDiscountPer / 100,
                    baseSalary,
                    bonusSalaryPer / 100,
                    minProfitForBonus));

        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
    }

    public void inputEmployees(){
        Scanner scanner = new Scanner(System.in);

        String name;
        boolean isManager;
        ArrayList<Employee> list = new ArrayList<>();
        for (int i = 0; i < 6; i++){
            System.out.println("Name and is manager?: ");
            name = scanner.nextLine();
            isManager = scanner.nextBoolean();

            Employee e = new Employee(isManager, name, 1000);
            scanner.nextLine();
            list.add(e);
        }

        serializeEmployees(list);
    }

    public void serializeEmployees(ArrayList<Employee> employees) {
        try (FileOutputStream fos = new FileOutputStream(employeesPath);
             ObjectOutputStream outputStream = new ObjectOutputStream(fos)) {

            outputStream.writeObject(employees);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Employee> deserializeEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(employeesPath);
             ObjectInputStream inputStream = new ObjectInputStream(fis)) {

            employees = (ArrayList) inputStream.readObject();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public void serializePressData(PressData data){
        try (FileOutputStream fous = new FileOutputStream(dataPath);
        ObjectOutputStream outputStream = new ObjectOutputStream(fous)) {

            outputStream.writeObject(data);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PressData deserializePressData(){
        PressData data = new PressData();
        try(FileInputStream fios = new FileInputStream(dataPath);
        ObjectInputStream inputStream = new ObjectInputStream(fios)) {

            data = (PressData) inputStream.readObject();
            Logger.getInstance().logPressWork(data.toString());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}
