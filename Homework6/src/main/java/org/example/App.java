package org.example;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

public class App {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            emf = Persistence.createEntityManagerFactory("Menu");
            em = emf.createEntityManager();
            while (true) {
                System.out.println("1: add dish");
                System.out.println("2: delete dish");
                System.out.println("3: change dish");
                System.out.println("4: view dish");
                System.out.print("-> ");

                String s = sc.nextLine();
                switch (s) {
                    case "1":
                        addDish(sc);
                        break;
                    case "2":
                        deleteDish(sc);
                        break;
                    case "3":
                        changeDish(sc);
                        break;
                    case "4":
                        viewDish(sc);
                        break;
                    default:
                        return;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void addDish(Scanner sc) {
        System.out.print("Enter dish name: ");
        String name = sc.nextLine();
        System.out.print("Enter dish price: ");
        String price = sc.nextLine();
        System.out.print("Enter dish weight: ");
        String weight = sc.nextLine();
        System.out.print("Is there a discount for the dish (yes/no): ");
        String discount = sc.nextLine();
        int finalPrice = Integer.parseInt(price);
        int finalWeight = Integer.parseInt(weight);
        boolean finalDiscount = discount.equals("yes");

        em.getTransaction().begin();
        try {
            MenuDish dish = new MenuDish(name, finalPrice, finalWeight, finalDiscount);
            em.persist(dish);
            em.getTransaction().commit();

            System.out.println(dish.getId());
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void deleteDish(Scanner sc) {
        System.out.print("Enter dish id: ");
        String dId = sc.nextLine();
        long id = Long.parseLong(dId);

        MenuDish dish = em.getReference(MenuDish.class, id);
        if (dish == null) {
            System.out.println("Client not found!");
            return;
        }

        em.getTransaction().begin();
        try {
            em.remove(dish);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void changeDish(Scanner sc) {
        System.out.print("Enter dish name: ");
        String name = sc.nextLine();
        System.out.print("Enter new dish price: ");
        String price = sc.nextLine();
        System.out.print("Enter new dish weight: ");
        String weight = sc.nextLine();
        System.out.print("Is there a discount for the dish (yes/no): ");
        String discount = sc.nextLine();
        int finalPrice = Integer.parseInt(price);
        int finalWeight = Integer.parseInt(weight);
        boolean finalDiscount = discount.equals("yes");

        MenuDish d;
        try {
            Query query = em.createQuery("SELECT x FROM MenuDish x WHERE x.dishName = :name", MenuDish.class);
            query.setParameter("name", name);

            d = (MenuDish) query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Client not found!");
            return;
        } catch (NonUniqueResultException ex) {
            System.out.println("Non unique result!");
            return;
        }

        em.getTransaction().begin();
        try {
            d.setPrice(finalPrice);
            d.setWeight(finalWeight);
            d.setDiscount(finalDiscount);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }
    private static void viewDish(Scanner sc) {
            System.out.println("Select parameters for displaying dishes");
            System.out.println("1: displaying dishes by their cost");
            System.out.println("2: display of dishes by their weight");
            System.out.println("3: displaying dishes by discount");
            System.out.print("-> ");

            String s = sc.nextLine();
            switch (s) {
                case "1":
                    displayByPrice(sc);
                    break;
                case "2":
                    displayByWeight(sc);
                    break;
                case "3":
                    displayByDiscount();
                    break;
                default:
                    return;
            }
    }

    private static void displayByPrice(Scanner sc){
        System.out.println("Enter the minimum cost of the dish");
        String minP = sc.nextLine();
        System.out.println("Enter the maximum cost of the dish");
        String maxP = sc.nextLine();
        int minimumPrice = Integer.parseInt(minP);
        int maximumPrice = Integer.parseInt(maxP);
        List<MenuDish> list = em.createQuery("SELECT c FROM MenuDish c WHERE c.price BETWEEN :minPrice AND :maxPrice", MenuDish.class)
                .setParameter("minPrice", minimumPrice)
                .setParameter("maxPrice", maximumPrice)
                .getResultList();
        for (MenuDish d : list){
            System.out.println(d);
        }
    }

    private static void displayByWeight(Scanner sc){
        System.out.println("Enter the maximum weight of the dish");
        String maxW = sc.nextLine();
        int maximumWeight = Integer.parseInt(maxW);
        List<MenuDish> list = em.createQuery("SELECT c FROM MenuDish c WHERE c.weight <= :maxWeight", MenuDish.class)
                .setParameter("maxWeight", maximumWeight)
                .getResultList();
        for (MenuDish d : list){
            System.out.println(d);
        }
    }

    private static void displayByDiscount(){
        List<MenuDish> list = em.createQuery("SELECT c FROM MenuDish c WHERE c.discount = true", MenuDish.class)
                .getResultList();
        for (MenuDish d : list){
            System.out.println(d);
        }
    }

}



