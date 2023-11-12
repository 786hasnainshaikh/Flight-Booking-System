package com.flightManager;
import com.connection.*;
import com.user.*;
import com.flightDetail.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class flightManager {

    user user;
    flightDetail flightDetail;

   static Scanner sc=new Scanner(System.in);
    static Connection con=connection.getConnection();

    public static void showFlights(){
             String showFlightsQuery="select * from flights;";
             try{
                 PreparedStatement flightStatement= con.prepareStatement(showFlightsQuery);
                 ResultSet resultSet = flightStatement.executeQuery();
                 while (resultSet.next()){
                     System.out.println(
                             "Flight ID :" + resultSet.getInt("id")
                             +"  Flight Number: " + resultSet.getString("flight_number") +
                             ", Origin: " + resultSet.getString("origin") +
                             ", Destination: " + resultSet.getString("destination") +
                             ", Departure Time: " + resultSet.getString("departure_time") +
                             ", Arrival Time: " + resultSet.getString("arrival_time") +
                             ", Available Seats: " + resultSet.getInt("available_seats"));
                 }
             }catch (Exception e){
                 e.printStackTrace();
             }
    }

    // bookTicket
    public static void bookTicket(int id){
             // getAvailableSeats(id) retuning the number of seats with corresponding id
          if (getAvailableSeats(id)>0){
              System.out.print("Enter your Name:");
              String p_name=sc.nextLine();
              // getFlightDetails(id) returns a object of flight details with corresponding id
              flightDetail flightDetails = getFlightDetails(id);

              try {
                  String bookingQuery="insert into bookings (flight_number, Passenger_Name, origin, destination, departure_time, arrival_time) values (?,?,?,?,?,?)";
                  String updateQuery="UPDATE flights SET available_seats = available_seats - 1 WHERE id = ?";

                  PreparedStatement bookingStatement= con.prepareStatement(bookingQuery);
                  PreparedStatement updateStatement=con.prepareStatement(updateQuery);
                  // passing values in bookingStatement arguments or query
                  bookingStatement.setString(1, flightDetails.getFlightNumber());
                  bookingStatement.setString(2, p_name);
                  bookingStatement.setString(3, flightDetails.getOrigin());
                  bookingStatement.setString(4, flightDetails.getDestination());
                  bookingStatement.setString(5, flightDetails.getDepartureTime());
                  bookingStatement.setString(6,flightDetails.getArrivalTime());

                  // passing values in updateStatement arguments or query
                  updateStatement.setInt(1, id);

                  //
                  int rowAffectedBooking = bookingStatement.executeUpdate();
                  int rowsAffectedUpdate = updateStatement.executeUpdate();

                  if(rowsAffectedUpdate>0 && rowAffectedBooking>0){
                      System.out.println("Booking successful");
                  }else {
                      System.out.println("booking failed");
                  }

              }catch (Exception e){
                  e.printStackTrace();
              }

          }
    }


    // get available seats
    public static int getAvailableSeats(int id){
        String availableSeatsQuery="select available_seats from flights where id=?";
        try{
            PreparedStatement seatsAvailableStatement=con.prepareStatement(availableSeatsQuery);

            seatsAvailableStatement.setInt(1, id);

            ResultSet resultSet = seatsAvailableStatement.executeQuery();
            if (resultSet.next()){
                int availableSeats=resultSet.getInt("available_seats");
                return availableSeats;
            }
            else{
                return 0;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }


    public static flightDetail  getFlightDetails(int id) {
        flightDetail obj=null;
        String fetchQuery = "select * from flights where id=?";
        try {
            PreparedStatement Fetchstatement = con.prepareStatement(fetchQuery);
            Fetchstatement.setInt(1, id);

            ResultSet resultSet = Fetchstatement.executeQuery();
            if (resultSet.next()) {
                 String flight_number= resultSet.getString("flight_number");
                 String origin= resultSet.getString("origin");
                 String destination= resultSet.getString("destination");
                 String departure= resultSet.getString("departure_time");
                 String arrival_time= resultSet.getString("arrival_time");
                int availabelSeats= resultSet.getInt("available_seats");

                obj=new flightDetail(flight_number,origin , destination, departure, arrival_time, availabelSeats );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static void showBookings(){
        String showBookingQuery="select * from bookings";
        try {
            PreparedStatement showBookingStatement= con.prepareStatement(showBookingQuery);
            ResultSet resultSet = showBookingStatement.executeQuery();
            while (resultSet.next()){
                String flightNumber=resultSet.getString("flight_number");
                String Passenger_Name=resultSet.getString("Passenger_Name");
                String origin=resultSet.getString("origin");
                String destination=resultSet.getString("destination");
                String departure_time=resultSet.getString("departure_time");
                String arrival_time=resultSet.getString("arrival_time");

                System.out.println("flightNumber: " + flightNumber
                                  + " Passenger_Name: " + Passenger_Name +
                                  " origin: " +origin +
                                  " destination: "+ destination+
                                  " departure_time: "+ departure_time+
                                  " arrival_time: "+arrival_time);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void deleteBooking( int Flight_number){
          try{
              // this query delete booking from bookings table
              String deleteQuery="delete from bookings where flight_number=?";

              // this query add a seat in available_seats  because user canceled the booking
              String addSeatBackQuery="UPDATE flights SET available_seats = available_seats + 1 WHERE flight_number = ?";

              PreparedStatement deleteStatement=con.prepareStatement(deleteQuery);
              PreparedStatement addseatStatement= con.prepareStatement(addSeatBackQuery);

              deleteStatement.setInt(1, Flight_number);
              addseatStatement.setInt(1, Flight_number);

               int rowsDeleteAffected=   deleteStatement.executeUpdate();
              int rowsSeatBackAffected=   addseatStatement.executeUpdate();

              if(rowsDeleteAffected>0 && rowsSeatBackAffected>0){
                  System.out.println("Booking canceled");
              }else {
                  System.out.println("Cant delete the booking enter valid Flight number");
              }
          }catch (Exception e){
              e.printStackTrace();
          }
    }
}
