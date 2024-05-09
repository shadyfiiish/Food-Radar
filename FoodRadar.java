import java.io.*;
import javax.swing.*;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.ArrayList;


public class FoodRadar {
    public static void main(String[] args) throws IOException {
        // Create a JFrame window with title "FoodFinder"
        JFrame frame = new JFrame("FoodFinder");
        String info = JOptionPane.showInputDialog(frame, "This is FoodRadar! It helps users to find their favorite restaurant by entering the style" +
                " of food and the ratings out of 5 stars. Please press enter. ");
        String istyle = JOptionPane.showInputDialog(frame, "Enter style of food:");
        double irating = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter minimum desired rating out of 5 stars:"));
        File listIn = new File("FoodList.txt");
        // Create data structures to store restaurants
        TreeMap<String, Restaurant> restaurantMap = new TreeMap<>();
        HashMap<String, Restaurant> restaurantByName = new HashMap<>();
        Stack<Restaurant> restaurantStack = new Stack<>();
        LinkedList<Restaurant> restaurantLinkedList = new LinkedList<>();
        HelpClass.about();

        if (listIn.exists()) {
            Scanner input = new Scanner(listIn);
            while (input.hasNext()) {
                String lineIn = input.nextLine();
                String[] lineSplit = lineIn.split("\\s");
                Restaurant newRestaurant = new Restaurant(lineSplit[0], lineSplit[1], Double.parseDouble(lineSplit[2]));
                restaurantMap.put(newRestaurant.getName(), newRestaurant); // Add restaurant to TreeMap for efficient searching
                restaurantByName.put(newRestaurant.getName(), newRestaurant); // Add restaurant to HashMap for efficient lookups
                restaurantStack.push(newRestaurant);
                restaurantLinkedList.add(newRestaurant);
            }
            input.close();
        } else {
            System.out.println("File Not Found!\nThe file FoodList.txt is missing.");
        }

        // Use HashMap's get method for efficient search by name (optional, can directly use HashMap)
        Restaurant match = restaurantByName.get(istyle);
        if (match != null && match.getRating() >= irating) {
            System.out.println("Perfect Match Found!");
            System.out.println(match.getName() + ", " + match.getStyle() + ", " + match.getRating());
        } else {
            // Use HashMap for efficient lookups during filtering
            ArrayList<Restaurant> qualifiedRestaurants = qualifiedRestaurants(restaurantByName.values(), istyle, irating);
            printList(qualifiedRestaurants);
        }

        JList list = new JList(listModel(qualifiedRestaurants(restaurantByName.values(), istyle, irating)));
        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane);
        JOptionPane.showMessageDialog(frame, list);
        System.exit(0);
    }

    public static ArrayList<Restaurant> qualifiedRestaurants(Iterable<Restaurant> restaurants, String istyle, double irating) {
        ArrayList<Restaurant> qualifiedRestaurants = new ArrayList<>(); // Create an ArrayList to store qualified restaurants
        for (Restaurant restaurant : restaurants) {
            if ( (restaurant.getStyle().equalsIgnoreCase(istyle)) && (restaurant.getRating() >= irating) ) {
                qualifiedRestaurants.add(restaurant);
            }
        }
        return qualifiedRestaurants;
    }

    public static void printList(ArrayList<Restaurant> inputList) {
        for (Restaurant restaurant : inputList) {
            System.out.print(restaurant.getName() + ", ");
            System.out.print(restaurant.getStyle() + ", ");
            System.out.print(restaurant.getRating() + " .");
            System.out.println();
        }
    }

    // Method to print restaurants to console (potentially for debugging)
    private static DefaultListModel listModel(ArrayList<Restaurant> inputList) {
        DefaultListModel dlm = new DefaultListModel();
        for (Restaurant restaurant : inputList) {
            dlm.addElement(restaurant.getName() + " | " + restaurant.getStyle() + " | " + restaurant.getRating());
        }
        return dlm;
    }
}
