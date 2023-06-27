import com.project.*;
import com.project.helpers.ClientInput;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Set and save press data
        //ClientInput.getInstance().inputPressData();

        //Set employees
        //ClientInput.getInstance().inputEmployees();

        //Add print machines
        ArrayList<PrintMachine> pms = new ArrayList<>();
        pms.add(new PrintMachine(5, 30));
        pms.add(new PrintMachine(3, 60));
        pms.add(new PrintMachine(10, 60));
        pms.add(new PrintMachine(100, 20));

        Press press = new Press(pms);

        //Press functionalities
        //press.makeOrder();
        press.addMachine(15, 20);
        //press.addEmployee(false, "Test Dragan");

        ClientInput.getInstance().serializePressData(Press.data);
        ClientInput.getInstance().serializeEmployees(press.getEmployees());
    }
}