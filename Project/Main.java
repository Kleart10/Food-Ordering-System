package Project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RestaurantMenu menu = new RestaurantMenu();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to our Restaurant!😉");
            System.out.println("1. Display Menu");
            System.out.println("2. Make an Order");
            System.out.println("3. Display Orders");
            System.out.println("4. Exit");

            System.out.print("Choose one option!");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    menu.displayMenu();
                    break;
                case 2:
                    makeOrder(menu, scanner);
                    break;
                case 3:
                    menu.displayOrders();
                    break;
                case 4:
                    closeProgram();
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void makeOrder(RestaurantMenu menu, Scanner scanner) {
        System.out.println("Enter your name: ");
        String clientName = scanner.nextLine();

        List<Product> menuList = menu.getMenu();
        if (menuList.isEmpty()) {
            System.out.println("Menu is empty. Cannot place order.");
            return;
        }

        List<Product> orderList = new ArrayList<>(); // List for ordered products
        double totalCost = 0; // Initialize total cost

        while (true) {
            System.out.println("Enter product NR to order (0 to finish your order, -1 to remove product from order):");
            int productId = scanner.nextInt();
            if (productId == 0) break;
            if (productId == -1) {
                // Remove product 
            	
            	
            	
            	
            	//RASTI KUR KLIENTI VENDOS -1
                if (!orderList.isEmpty()) {
                    System.out.println("Enter product NR to remove:");
                    int removeProductId = scanner.nextInt();
                    for (Iterator<Product> iterator = orderList.iterator(); iterator.hasNext();) {
                        Product product = iterator.next();
                        if (product.getId() == removeProductId) {
                            totalCost -= product.getPrice();
                            iterator.remove();
                            System.out.println("Product removed from order.");
                        }
                    }
                } else {
                    System.out.println("Order is empty. Nothing to remove.");
                }
                continue;
                
                
                
                
                //Rasti kur Klienti vendos vleren e id pra NR e produktit bazuar ne liste
            }
            //Vendoset sasia e produktit te kerkuar ne baze te nr
            System.out.println("Enter quantity:");
            int quantity = scanner.nextInt();//dhe merret me ane te scanner.nextInt()

            Product product = menu.findProductById(productId);
            if (product != null) {
                if (quantity <= product.getQuantity()) {
                    double orderCost = product.getPrice() * quantity; // Calculate cost of product order
                    totalCost += orderCost; // total cost
                    orderList.add(product); // Add product to order list for printing
                    product.updateQuantity(product.getQuantity() - quantity); // Update quantity
                    System.out.println("Ordered: " + product.getName() + " - $" + product.getPrice() + " x" + quantity);
                    System.out.println("Order Cost: $" + orderCost); // Print order cost
                    System.out.println("Total Cost so far: $" + totalCost); // Print total cost so far
                } else {
                    System.out.println("Insufficient quantity for product: " + product.getName());
                }
            } else {
                System.out.println("Product not found.");
            }
        }

        // Print final order
        System.out.println("\nFinal Order:");
        for (Product product : orderList) {
            System.out.println(product.getName() + " - $" + product.getPrice());
        }
        System.out.println("Total Cost of the order: $" + totalCost);
        System.out.println("BON APETIT,SEE YOU NEXT TIME!😋");

        // Save the order to file
        for (Product product : orderList) {
            menu.saveOrderToFile(clientName, product.getId(), product.getQuantity());
        }
    }

    private static void closeProgram() {
        System.out.println("FINISH");
    }
}
