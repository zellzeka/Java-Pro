package org.example;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;
import java.io.Closeable;

public class Main {
    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/apartments?serverTimezone=Europe/Kiev";
    static final String DB_USER = "userA";
    static final String DB_PASSWORD = "repsol202";

    static Connection conn;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try  {
            try {
                // create connection
                conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);

                while (true) {
                    System.out.println("1: add apartment");
                    System.out.println("2: delete apartment");
                    System.out.println("3: change apartment");
                    System.out.println("4: view apartments");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addApartment(sc);
                            break;
                        case "2":
                            deleteApartment(sc);
                            break;
                        case "3":
                            changeApartment(sc);
                            break;
                        case "4":
                            viewApartments(sc);
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                if (conn != null) conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return;
        }
    }

    private static void addApartment(Scanner sc) throws SQLException {

        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO apartments (district, address, area, rooms, price) VALUES(?, ?, ?, ?, ?)")){
            while (true) {
                System.out.print("Введіть район: ");
                String district = sc.nextLine();
                if (district != "") {
                    System.out.print("Введіть адресу: ");
                    String address = sc.nextLine();
                    System.out.print("Введіть площу: ");
                    int area = Integer.parseInt(sc.nextLine());
                    System.out.print("Введіть кількість кімнат: ");
                    int rooms = Integer.parseInt(sc.nextLine());
                    System.out.print("Введіть ціну: ");
                    int price = Integer.parseInt(sc.nextLine());


                    ps.setString(1, district);
                    ps.setString(2, address);
                    ps.setInt(3, area);
                    ps.setInt(4, rooms);
                    ps.setInt(5, price);
                    ps.executeUpdate(); // for INSERT, UPDATE & DELETE
                } else {
                    break;
                }
            }
        }
    }

    private static void deleteApartment(Scanner sc) throws SQLException {
        System.out.print("Enter apartment id: ");
        int id = Integer.parseInt(sc.nextLine());

        try (Statement ps = conn.createStatement()) {
            ps.execute("DELETE FROM apartmens WHERE id =" + id);
        }
    }

    private static void changeApartment(Scanner sc) throws SQLException {
        System.out.print("Enter apartment id: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Введіть новий район: ");
        String district = sc.nextLine();
        System.out.print("Введіть нову адресу: ");
        String address = sc.nextLine();
        System.out.print("Введіть нову площу: ");
        int area = Integer.parseInt(sc.nextLine());
        System.out.print("Введіть нову кількість кімнат: ");
        int rooms = Integer.parseInt(sc.nextLine());
        System.out.print("Введіть нову ціну: ");
        int price = Integer.parseInt(sc.nextLine());

        PreparedStatement ps = conn.prepareStatement("UPDATE apartments SET district = ?, address = ?, area = ?, rooms = ?, price = ?  WHERE id = ?");
        try {
            ps.setString(1, district);
            ps.setString(2, address);
            ps.setInt(3, area);
            ps.setInt(4, rooms);
            ps.setInt(5, price);
            ps.setInt(6, id);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }
    }

    private static void viewApartments(Scanner sc) throws SQLException {
        System.out.println("Обрати критерій для вибірки");

        while (true) {
            System.out.println("1: за id");
            System.out.println("2: за районом");
            System.out.println("3: за адресою");
            System.out.println("4: за площею");
            System.out.println("5: за кількістю кімнат");
            System.out.println("6: за ціною");
            System.out.println("7: дивитись всю таблицю");
            System.out.print("-> ");

            String s = sc.nextLine();
            switch (s) {
                case "1":
                    System.out.println("Введіть значення id");
                    String idValue = sc.nextLine();
                    int finalId = Integer.parseInt(idValue);
                    String idParam = "id";
                    getApartments(idParam, finalId);
                    break;
                case "2":
                    System.out.println("Введіть район");
                    String districtValue = sc.nextLine();
                    String districtParam = "district";
                    getApartments(districtParam, districtValue);
                    break;
                case "3":
                    System.out.println("Введіть адресу");
                    String addressValue = sc.nextLine();
                    String addressParam = "address";
                    getApartments(addressParam, addressValue);
                    break;
                case "4":
                    System.out.println("Введіть площу квартири");
                    String areaValue = sc.nextLine();
                    int finalArea= Integer.parseInt(areaValue);
                    String areaParam = "area";
                    getApartments(areaParam, finalArea);
                    break;
                case "5":
                    System.out.println("Введіть кількість кімнат");
                    String roomsValue = sc.nextLine();
                    int finalRooms= Integer.parseInt(roomsValue);
                    String roomsParam = "rooms";
                    getApartments(roomsParam, finalRooms);
                    break;
                case "6":
                    System.out.println("Введіть ціну");
                    String priceValue = sc.nextLine();
                    int finalPrice= Integer.parseInt(priceValue);
                    String priceParam = "price";
                    getApartments(priceParam, finalPrice);
                    break;
                case "7":
                    getAllApartments();
                    break;
                default:
                    return;
            }
        }
    }

    private static<T> void getApartments(String param, T value) throws SQLException{

        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM apartments WHERE " + param + " = " + value);
             ResultSet rs = ps.executeQuery()){
            ResultSetMetaData md = rs.getMetaData();
            if (!rs.next()){
                System.out.println("Нічого не знайдено");
            } else {
                for (int i = 1; i <= md.getColumnCount(); i++)
                    System.out.print(md.getColumnName(i) + "\t\t");
                System.out.println();

                do {
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        System.out.print(rs.getString(i) + "\t\t");
                    }
                    System.out.println();
                } while (rs.next());

            }
        }
    }
    private static void getAllApartments() throws SQLException{

        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM apartments");
             ResultSet rs = ps.executeQuery()){
            ResultSetMetaData md = rs.getMetaData();

            for (int i = 1; i <= md.getColumnCount(); i++)
                System.out.print(md.getColumnName(i) + "\t\t");
            System.out.println();

            while (rs.next()) {
                for (int i = 1; i <= md.getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + "\t\t");
                }
                System.out.println();
            }
        }
    }

}
