import com.connection.*;
import com.flightDetail.*;
import com.flightManager.*;
import com.loginDetails.*;

import java.security.spec.ECField;
import java.sql.Connection;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;


public class FlightBookingSystem {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);

        while (true){
            main_menu();
            System.out.print("Enter choice: ");
            int menu_choice= sc.nextInt();
            switch (menu_choice){
                case (1):
                    System.out.print("Enter the email:");
                    String register_email= sc.nextLine();
                    loginDetails.register(register_email);
                    break;

                case (2):
                    String login = loginDetails.login();
                    if (login!=null){
                        System.out.println("user Login  Successfully");

                        boolean flag=true;
                        while (flag){
                            System.out.println("1. Show Available Flights");
                            System.out.println("2. Book a Ticket");
                            System.out.println("3. Show Bookings");
                            System.out.println("4. Cancel Ticket");
                            System.out.println("5. Exit");
                            System.out.print("Enter the choice:");
                            int flight_choice= sc.nextInt();

                            switch (flight_choice){
                                case (1):
                                    flightManager.showFlights();
                                    break;

                                case (2):
                                    System.out.println("Enter the Flight Id");
                                    int flight_id=sc.nextInt();
                                    flightManager.bookTicket(flight_id);
                                    break;

                                case (3):
                                    // show all bookings
                                    flightManager.showBookings();
                                    break;

                                case (4):
                                    // delete booking
                                    System.out.print("Enter the Flight Number:");
                                    int Flight_number=sc.nextInt();
                                    flightManager.deleteBooking(Flight_number);
                                    break;

                                case (5):
                                    flag=false;
                                    break;


                                default:
                                    System.out.println("Invalid choice");
                            }
                        }

                    }else {
                        System.out.println("invalid email/password");
                    }
                    break;


                case (3):

                    System.out.print("Exiting System");
                    try {
                        for (int i = 0; i < 5; i++) {
                            System.out.print(".");
                            Thread.sleep(450);
                        }
                        System.out.println();
                        System.out.println("Thank you for Using FlightBookingSystem");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;

            }

        }

    }

    public static void main_menu(){
        System.out.println("-------Flight Booking System---------");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
    }
}
