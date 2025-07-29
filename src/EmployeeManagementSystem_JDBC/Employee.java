package EmployeeManagementSystem_JDBC;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Employee {
    static  Scanner sc = new Scanner(System.in);
    static Connection con;
    static String sql;

    public static void main(String[] args) throws Exception {
       try{
        // Establish the connection
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ your database name", "your root", "your password");
        System.out.println("✅ Connection Established !");
       }catch (SQLException e){
           System.out.println("❌ Database Connection Failed !"+e.getMessage());
           return;
       }

       System.out.println("**************** Employee Management System - JDBC ****************");

        while (true) {
            int choice;
            System.out.println(" Choose Your Operations :");
            System.out.println(" 1. Add an new Employee \n 2. update an Employee \n 3. Delete an Employee \n 4. Display all Employees \n 5. Exit");
            System.out.print(" Choice : ");
            try {
                choice = sc.nextInt();
                sc.nextLine();

            } catch (InputMismatchException e) {
                System.out.println("❌ Enter in Digits !");
                sc.nextLine();
                continue;
            }

            try {
                switch (choice) {
                    case 1 -> addEmployee();
                    case 2 -> updateEmployee();
                    case 3 -> deleteEmployee();
                    case 4 -> displayEmployees();
                    case 5 -> {
                        System.out.println("Exiting......");
                        return;
                    }
                    default -> System.out.println("❌ Invalid Operations !");
                }
            }catch (InputMismatchException e){
                System.out.println("❌ Error Occurred: "+e.getMessage());
            }
        }
    }

    private static void addEmployee() throws SQLException {
        try {
            System.out.println("Enter New Employee Details !");
            System.out.print("Employee ID: ");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline

            // Step 1: Check if ID already exists
            String checkQuery = "SELECT empId FROM Employee WHERE empId = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkQuery);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("⚠️ This ID already exists. Please enter a unique ID!");
                return;
            }

            // Step 2: Input and validate name
            System.out.print("Employee Name : ");
            String name = sc.nextLine();
            if (!name.matches("[a-zA-Z .]+")) {
                System.out.println(" ⚠️ Name must contain only letters and spaces.");
                return;
            }

            // Step 3: Input and validate department
            System.out.print("Employee Dept : ");
            String dept = sc.nextLine();
            if (!dept.matches("[a-zA-Z &.]+")) {
                System.out.println(" ⚠️ Department must contain only letters.");
                return;
            }

            // Step 4: Input salary
            System.out.print("Employee Salary : ");
            double salary = sc.nextDouble();

            // Step 5: Insert into database
            String sql = "INSERT INTO Employee (empId, empName, empDepartment, empSalary) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, dept);
            pstmt.setDouble(4, salary);

            int rows = pstmt.executeUpdate();
            System.out.println("✅ " + rows + " row(s) inserted successfully.\n");

        } catch (SQLException e) {
            System.out.println("❌ Database Error: " + e.getMessage());
        } catch (InputMismatchException ime) {
            System.out.println("❌ Invalid input type. Please enter correct values.");
            sc.nextLine(); // clear the scanner buffer
        }
    }



    private static void updateEmployee() throws SQLException {


        while(true) {
            System.out.println(" 1. Update Name :  \n 2. Update Dept :  \n 3. Update Salary :  \n 4. Exit");
            System.out.print("Choice :");

            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input ! Please enter a Number !");
                sc.nextLine();
                continue;
            }

            int id;
            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Employee ID :");
                        id = sc.nextInt();
                        sc.nextLine();

                        // Check if ID exists
                        String checkQuery = "SELECT empId FROM Employee WHERE empId = ?";
                        PreparedStatement checkStmt = con.prepareStatement(checkQuery);
                        checkStmt.setInt(1, id);
                        ResultSet rs = checkStmt.executeQuery();

                        if (!rs.next()) {
                            System.out.println("❌ This ID is not in the database!");
                            continue;
                        }

                        System.out.print("Employee Name :");
                        String updateName = sc.nextLine();
                        if (!updateName.matches("[a-zA-Z .]+")) {
                            System.out.println("⚠️ Name must contain only letters and spaces.");
                            break;
                        }

                        sql = "update employee set empName=? where empId=?;";
                        PreparedStatement pstmt = con.prepareStatement(sql);
                        pstmt.setString(1, updateName);
                        pstmt.setInt(2, id);

                        int rows = pstmt.executeUpdate();
                        System.out.println("✅ " + rows + " row(s) updated successfully.\n");
                    }

                    case 2 -> {
                        System.out.print("Employee ID :");
                        id = sc.nextInt();
                        sc.nextLine();

                        // Check if ID exists
                        String checkQuery = "SELECT empId FROM Employee WHERE empId = ?";
                        PreparedStatement checkStmt = con.prepareStatement(checkQuery);
                        checkStmt.setInt(1, id);
                        ResultSet rs = checkStmt.executeQuery();

                        if (!rs.next()) {
                            System.out.println("❌ This ID is not in the database!");
                            continue;
                        }

                        System.out.print("Employee Dept :");
                        String updateDept = sc.nextLine();
                        if (!updateDept.matches("[a-zA-Z &.]+")) {
                            System.out.println("⚠️ Department  must contain only letters and spaces.");
                            break;
                        }

                        sql = "update employee set empDepartment=? where empId=?;";
                        PreparedStatement pstmt = con.prepareStatement(sql);
                        pstmt.setString(1, updateDept);
                        pstmt.setInt(2, id);

                        int row = pstmt.executeUpdate();
                        System.out.println("✅ " + row + " row(s) updated successfully.\n");
                    }

                    case 3 -> {
                        System.out.print("Employee ID :");
                        id = sc.nextInt();
                        sc.nextLine();

                        // Check if ID exists
                        String checkQuery = "SELECT empId FROM Employee WHERE empId = ?";
                        PreparedStatement checkStmt = con.prepareStatement(checkQuery);
                        checkStmt.setInt(1, id);
                        ResultSet rs = checkStmt.executeQuery();

                        if (!rs.next()) {
                            System.out.println("❌ This ID is not in the database!");
                            continue;
                        }


                        double updateSalary;
                        try {
                            System.out.print("Employee salary :");
                            updateSalary = sc.nextDouble();
                            sc.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("❌ Invalid salary input.");
                            sc.nextLine();
                            break;
                        }

                        sql = "update employee set empSalary=? where empId=?;";
                        PreparedStatement pstmt = con.prepareStatement(sql);
                        pstmt.setDouble(1, updateSalary);
                        pstmt.setInt(2, id);

                        int rows = pstmt.executeUpdate();
                        System.out.println("✅ " + rows + " row(s) updated successfully.\n");
                    }
                    case 4 -> {
                        System.out.println("Exiting.....");
                        return;
                    }
                    default -> System.out.println("❌ Invalid Operation !");
                }

            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input type !");
            }
        }
    }

    private static void deleteEmployee() throws SQLException {
        try {
            System.out.print("Employee ID :");
            int Id = sc.nextInt();
            sc.nextLine();

            // Check if ID exists
            String checkQuery = "SELECT empId FROM Employee WHERE empId = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkQuery);
            checkStmt.setInt(1, Id);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("❌ This ID is not in the database!");
                return;
            }

            sql = "delete from Employee where empId=?;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, Id);

            int val = pstmt.executeUpdate();
            System.out.println("✅ " + val + " row(s) deleted successfully.\n");
        }catch (InputMismatchException e){
            System.out.println("❌ Invalid input !");
        }
    }

    private static void displayEmployees() throws SQLException {
       try{
        sql ="select * from Employee;";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + "  " + rs.getDouble(4));
            }
            System.out.println();

    }catch (SQLException e){
           System.out.println("❌ Error occurred ! "+e.getMessage());
       }
    }
}