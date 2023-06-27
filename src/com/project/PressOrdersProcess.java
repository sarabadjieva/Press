package com.project;

import com.project.helpers.*;
import com.project.publication.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PressOrdersProcess {
    private final ArrayList<PrintMachine> machines;
    private LinkedHashMap<Publication, Integer> waitingOrders = new LinkedHashMap<>();
    private Thread ordersThread = new Thread();
    private int machinesCount;

    public PressOrdersProcess(ArrayList<PrintMachine> pms){
        machines = new ArrayList<>(pms);
        machinesCount = machines.size();
    }

    public void addMachine(int maxPages, int pagesPerMin){
        machines.add(new PrintMachine(maxPages, pagesPerMin));
        machinesCount = machines.size();
    }
    
    public Order makeOrder(PublicationType pType){
        try{
            Scanner scanner = new Scanner(System.in);

            int orderCount;
            do{
                System.out.println("Enter order count(positive number): ");
            } while((orderCount = scanner.nextInt()) < 0);

            Publication publication = ClientInput.getInstance().inputPublication(pType);

            processOrder(publication, orderCount);

            return new Order(publication, orderCount);
        }
        catch (InputMismatchException e){
            e.printStackTrace();
        }
        return null;
    }

    private void processOrder(Publication publication, int orderCount){
        float basePrice = publication.getSingleManufacturingPrice() + publication.getSingleManufacturingPrice() * Press.data.publicationProfitPercent;
        publication.setClientPrice(
                orderCount < Press.data.discountPublicationsCount
                        ? basePrice
                        : basePrice - basePrice * Press.data.discountPercent);

        waitingOrders.put(publication, orderCount);

        CheckOrdersThread();
    }

    private void CheckOrdersThread() {
        if(ordersThread.isAlive())
            return;

        //run until empty or working machines
        ordersThread = new Thread(() -> {
            try {
                while (!waitingOrders.isEmpty() || machines.stream().anyMatch(PrintMachine::isWorking)) {
                    for (int i = 0; i < machinesCount; i++) {
                        try {
                            setMachine(i); //start or continue work
                        } catch (EmptyMachineException e) {
                            Logger.getInstance().logMachineWork(machines.get(i).getMachineNum(), e.toString());
                            machines.get(i).loadMachine();
                        }
                    }
                }
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        });

        ordersThread.start();
    }

    private void setMachine(int index) throws EmptyMachineException, InterruptedException {
        PrintMachine m = machines.get(index);

        if (m.isPublicationNull() && !waitingOrders.isEmpty()) {
            m.startPrinting(getFromFirstOrder());
        } else if (!m.isPublicationNull()) {
            m.continuePrinting();
        }

        Thread.sleep(TimeUnit.SECONDS.toMillis(1));
    }

    private Publication getFromFirstOrder(){
        Publication first = waitingOrders.keySet().stream().findFirst().get();

        Integer count = waitingOrders.get(first);
        waitingOrders.replace(first, --count);
        if (count == 0) waitingOrders.remove(first);

        Logger.getInstance().logPressWork("Publications left from " + first.getName() + " to load: " + count);

        return first;
    }
}
