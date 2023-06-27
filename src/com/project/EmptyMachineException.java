package com.project;

public class EmptyMachineException extends Exception{
    int leftPages;

    EmptyMachineException(int leftP){
        leftPages = leftP;
    }

    public String toString(){
        return "Empty machine. Left printing " + leftPages + " number of pages. Please load...";
    }
}
