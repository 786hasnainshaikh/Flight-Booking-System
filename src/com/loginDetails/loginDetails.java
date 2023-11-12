package com.loginDetails;

import java.security.spec.ECField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import com.connection.connection;

public class loginDetails {
    static Scanner sc=new Scanner(System.in);

    static Connection con=connection.getConnection();

    public static void register(String email){

        if (!isUserExist(email)){
            System.out.print("Enter Passenger Name:");
            String p_name=sc.nextLine();
            System.out.print("Enter password:");
            String password=sc.nextLine();
            System.out.print("Enter contact Number:");
            String contact_number=sc.nextLine();

            try {
                String registerQuery= "insert into logindetails (Email, Password, Passenger_Name, Contact_Number) values (?,?,?,?)";
                PreparedStatement registerStatement= con.prepareStatement(registerQuery);

                registerStatement.setString(1, email);
                registerStatement.setString(2,password);
                registerStatement.setString(3, p_name);
                registerStatement.setString(4, contact_number);

                int i = registerStatement.executeUpdate();
                if(i>0){
                    System.out.println("Passenger Registered Successfully");
                } else {
                    System.out.println("something got error!!!!");
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            System.out.println("User Registered with this email ");
        }

    }

    public static String login(){

        System.out.print("Enter email:");
        String email=sc.nextLine();
        System.out.print("Enter password:");
        String password=sc.nextLine();


        if(isUserExist(email)){
            String loginQuery="select Email from logindetails where Email=? and Password= ?";
            try{
                PreparedStatement loginStatement=con.prepareStatement(loginQuery);

                loginStatement.setString(1, email);
                loginStatement.setString(2, password);

                ResultSet resultSet = loginStatement.executeQuery();
                if(resultSet.next()){
                    String getEmail=resultSet.getString("Email");
                    return getEmail;
                }
                else {
                    return null;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            System.out.println("Please Register First");
        }



        return  null;
    }

    public static boolean isUserExist(String email){
        String query="select * from logindetails where Email=?";
        try {
            PreparedStatement pstem= con.prepareStatement(query);
            pstem.setString(1, email);

            ResultSet resultSet = pstem.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
