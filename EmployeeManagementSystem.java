import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Employee {
    String id;
    String name;
    double salary;

    public Employee(String name, String id, double salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return id + "\t" + name + "\t" + String.format("%.2f", salary);
    }
}

class EmployeeInterface {
    Scanner sc = new Scanner(System.in);
    private final String filePath = "EmployeeDetails.txt";
    private List<Employee> employees = new ArrayList<>();

    public void createEmployee() {
        boolean cont = true;

        while (cont) {
            Employee employee = collectData();
            if (isIdUnique(employee.id)) {
                employees.add(employee);
                System.out.println("\nEmployee added successfully.\n");
            } else {
                System.out.println("\nAn employee with the same ID already exists. Please choose a unique ID.");
            }

            System.out.print("Do you wish to continue? (Y/N)  ");
            String wish = sc.next();
            if (wish.equalsIgnoreCase("N")) {
                cont = false;
            }
        }

        writeToFile();
        menuInterface();
    }

    public Employee collectData() {
        System.out.print("Enter Employee ID: ");
        String empId = sc.next();
        System.out.println();
        System.out.print("Enter Employee Name: ");
        String empName = sc.next();
        System.out.println();
        System.out.print("Enter Employee Salary: ");
        double empSalary = sc.nextDouble();

        return new Employee(empName, empId, empSalary);
    }

    private boolean isIdUnique(String empId) {
        for (Employee employee : employees) {
            if (employee.id.equals(empId)) {
                return false;
            }
        }
        return true;
    }

    private void writeToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (Employee employee : employees) {
                writer.write(employee.toString());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("\nAn error occurred while writing to the file.");
        }
    }

    private void readFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            employees.clear();
            while ((line = reader.readLine()) != null) {
                String[] employeeData = line.split("\t");
                String empId = employeeData[0];
                String empName = employeeData[1];
                double empSalary = Double.parseDouble(employeeData[2]);
                Employee employee = new Employee(empName, empId, empSalary);
                employees.add(employee);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("\nAn error occurred while reading from the file.");
        }
    }

    public void updateEmployee() {
         readFromFile();

        System.out.print("\n\nEnter the Employee ID of the employee you want to update: ");
        String empId = sc.next();

        Employee employee = findEmployee(empId);

        if (employee != null) {
            Employee newData = collectData();
            employee.name = newData.name;
            employee.salary = newData.salary;
            writeToFile();
            System.out.println("\nEmployee record updated successfully.");
        } else {
            System.out.println("\nNo matching employee found in the database.");
        }

        menuInterface();
    }

    public void deleteEmployee() {
        readFromFile();

        System.out.print("Enter the Employee ID of the employee you want to delete: ");
        String empId = sc.next();

        Employee employee = findEmployee(empId);

        if (employee != null) {
            employees.remove(employee);
            writeToFile();
            System.out.println("Employee record deleted successfully.");
        } else {
            System.out.println("No matching employee found in the database.");
        }

        menuInterface();
    }

    private Employee findEmployee(String empId) {
        for (Employee employee : employees) {
            if (employee.id.equals(empId)) {
                return employee;
            }
        }
        return null;
    }

    public void readEmployees() {
        readFromFile();

        if (employees.isEmpty()) {
            System.out.println("No employees found in the database.");
        } else {
            System.out.println("\n ID  \t Name  \t Salary");
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }

        menuInterface();
    }

    public void searchEmployee() {
        readFromFile();

        System.out.print("\nEnter the Employee ID of the employee you want to search: ");
        String empId = sc.next();

        Employee employee = findEmployee(empId);

        if (employee != null) {
            System.out.println("\n ID  \t Name  \t Salary");
            System.out.println(employee);
            System.out.println("\nEmployee record fetched successfully.");
        } else {
            System.out.println("\nNo matching employee found in the database.");
        }

        menuInterface();
    }

    public void exitApp() {
        System.out.println("\nThank you for using the application.");
    }

    public void menuInterface() {
       
        System.out.println("\n\t\t\t\t       Please select an operation");
        System.out.println("1. Create Employee");
        System.out.println("2. Update Employee");
        System.out.println("3. Read Employees");
        System.out.println("4. Delete Employee");
        System.out.println("5. Search for Employee");
        System.out.println("6. Exit");

        System.out.print("Enter your choice: ");
        int optionSelected = sc.nextInt();

        switch (optionSelected) {
            case 1:
                createEmployee();
                break;
            case 2:
                updateEmployee();
                break;
            case 3:
                readEmployees();
                break;
            case 4:
                deleteEmployee();
                break;
            case 5:
                searchEmployee();
                break;
            case 6:
                exitApp();
                break;
            default:
                System.out.println("Invalid option selected.");
                menuInterface();
                break;
        }
    }

    public static void main(String[] args) {
         System.out.println("\n\n\t\t\t\t------ EMPLOYEE MANAGEMENT SYSTEM ------");
        EmployeeInterface ei = new EmployeeInterface();
        ei.menuInterface();
    }
}
