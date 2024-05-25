package Project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RestaurantMenu {
    private List<Product> menu;// LISTA QE DO TE BEJE RUAJTJEN E PRODUKTEVE
    private static final String MENU_FILE = "C:\\Users\\klear\\Desktop\\1.Data Structure Leksion\\Projekt\\Data Structure Project\\src\\Project";// File
                                                                                                                                                 // qe
                                                                                                                                                 // permban
                                                                                                                                                 // produket
    private static final String ORDER_FILE = "C:\\Users\\klear\\Desktop\\1.Data Structure Leksion\\Projekt\\Data Structure Project\\src\\Project";// File
                                                                                                                                                  // qe
                                                                                                                                                  // permban
                                                                                                                                                  // orders
                                                                                                                                                  // e
                                                                                                                                                  // kryera

    public RestaurantMenu() {
        this.menu = new ArrayList<>();
        loadMenuFromFile(); // Ngarkimi i CSV file qe permabn produktet
    }

    private void loadMenuFromFile() {
        try (Scanner scanner = new Scanner(new File(MENU_FILE))) {// lexohet file i menuse dhe shton produktet
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");// Kontrollohet cdo kolone per sintaksen ne baze te atributet tek klasa
                                                 // Product
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1].trim(); // Trim to remove extra spaces
                    String category = parts[2].trim(); // Trim to remove extra spaces
                    double price = Double.parseDouble(parts[3]);
                    int quantity = Integer.parseInt(parts[4]);
                    Product product = new Product(id, name, category, price, quantity);
                    // Pasi lexohet dhe cdo gje eshte korrekt me vlerat e vendosura ne sintakse athr
                    // shtohet me menu.add ne liste e menus
                    menu.add(product);// nqs eshte ne rregull produkti shtohet ne menu
                } else {
                    System.out.println("Skipping invalid line: " + line);// Kur ka probleme ne sintakse ne CSV e menus
                }
            }
            System.out.println("Menu loaded successfully.");// nqs gjithcka eshte ne rregull dhe te gjitha produktet jan
                                                            // shtuar printohet mesazhi
        } catch (FileNotFoundException e) {
            System.out.println("Menu file not found: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing menu file: " + e.getMessage());
        }
    }

    public List<Product> getMenu() {// kthen listen e produkteve ne menu
        return menu;
    }

    // Printing the menu in screen(ne console)
    public void displayMenu() {
        if (menu.isEmpty()) {
            System.out.println("The menu is currently empty.There is no Product!");
        } else {
            for (Product product : menu) {
                System.out.println(product);
            }
        }
    }

    public void addProduct(Product product) {
        menu.add(product);
        System.out.println("Product '" + product.getName() + "' added to the menu.");
    }

    public Product findProductById(int id) {
        for (Product product : menu) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public void saveOrderToFile(String clientName, int productId, int quantity) {
        Product product = findProductById(productId);
        if (product != null) {
            double totalCost = product.getPrice() * quantity;
            try (FileWriter fw = new FileWriter(ORDER_FILE, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)) {
                out.println(clientName + "," + product.getName() + "," + productId + "," + quantity + "," + totalCost);
                System.out.println("Order saved successfully.");
            } catch (IOException e) {
                System.out.println("Error saving order to file: " + e.getMessage());
            }
        } else {
            System.out.println("Product not found.");
        }
    }

    public void displayOrders() {
        try (Scanner scanner = new Scanner(new File(ORDER_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Orders file not found: " + e.getMessage());
        }
    }
}
