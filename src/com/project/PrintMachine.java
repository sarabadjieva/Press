package com.project;

import com.project.helpers.Logger;
import com.project.publication.Publication;

import java.util.concurrent.TimeUnit;

public class PrintMachine implements Runnable {
    private static int machinesCount = 0;

    private final int machineNum;
    private final int maxPagesCount;
    private final long millisPerPage;

    private Publication publication;
    private int pagesLeftToPrint;
    private int currPagesCount;
    private boolean isBlackAndWhite;
    private Thread printThread = new Thread();

    public int getMachineNum() {
        return machineNum;
    }

    public PrintMachine(int maxP, int pPerMin){
        machineNum = ++machinesCount;
        maxPagesCount = maxP;
        millisPerPage = TimeUnit.SECONDS.toMillis(60) / pPerMin;
        currPagesCount = maxPagesCount;
    }

    public boolean arePapersEmpty() { return currPagesCount == 0; }
    public boolean isPublicationNull() { return publication == null; }
    public boolean isWorking() { return pagesLeftToPrint > 0 || printThread.isAlive(); }

    public void startPrinting(Publication p) throws EmptyMachineException{
        if(printThread.isAlive() || !isPublicationNull()) return;

        if(arePapersEmpty()){
            throw new EmptyMachineException(pagesLeftToPrint);
        }

        Logger.getInstance().logMachineWork(machineNum, "Setting machine to print... ");
        publication = p;
        pagesLeftToPrint = publication.getPagesCount();
        if(printThreadStart()){
            Logger.getInstance().logMachineWork(machineNum, "Starting work...");
        }
    }

    public void continuePrinting() throws EmptyMachineException{
        if(arePapersEmpty()){
            throw new EmptyMachineException(pagesLeftToPrint);
        }

        if(isPublicationNull()) return;

        if(printThreadStart()){
            Logger.getInstance().logMachineWork(machineNum, "Continuing work...");
        }
    }

    public void loadMachine(){
        Logger.getInstance().logMachineWork(machineNum, "Loading machine...");
        currPagesCount = maxPagesCount;
    }

    private boolean printThreadStart(){
        if(publication != null && !printThread.isAlive())
        {
            printThread = new Thread(this, "Printing on " + machineNum);
            printThread.start();
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        try{
            while(!arePapersEmpty() && pagesLeftToPrint > 0){
                --currPagesCount;
                --pagesLeftToPrint;
                Thread.sleep(millisPerPage);
            }

            if(pagesLeftToPrint == 0){
                Logger.getInstance().logMachineWork(machineNum, "All pages printed for " + publication.getName() + ". Add another...");
                publication = null;
            } else if(arePapersEmpty()){
                throw new EmptyMachineException(pagesLeftToPrint);
            }
        }
        catch (InterruptedException e) {
            Logger.getInstance().logMachineWork(machineNum, e.getMessage());
        } catch (EmptyMachineException e) {
            Logger.getInstance().logMachineWork(machineNum, e.toString());
            loadMachine();}
    }

}
