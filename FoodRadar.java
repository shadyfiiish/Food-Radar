import java.io.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JList;
import java.util.Stack;
import java.util.LinkedList;

public class FoodRadar {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("FoodFinder");
        String info = JOptionPane.showInputDialog(frame, "This is FoodRadar! It helps users to find their favorite restaurant by entering the style" +
                " of food and the ratings out of 5 stars. Please press enter. ");
        String istyle = JOptionPane.showInputDialog(frame, "Enter style of food:");
        double irating = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter minimum desired rating out of 5 stars:"));
        File listIn = new File("FoodList.txt");
        ArrayList<Restaurant> fullList = new ArrayList<>();
        Stack<Restaurant> restaurantStack = new Stack<>();
        LinkedList<Restaurant> restaurantLinkedList = new LinkedList<>();
        HelpClass();
        if (listIn.exists()) {
            Scanner input = new Scanner(listIn);
            while (input.hasNext()) {
                String lineIn = input.nextLine();
                String[] lineSplit = lineIn.split("\\s");
                Restaurant newRestaurant = new Restaurant(lineSplit[0], lineSplit[1], Double.parseDouble(lineSplit[2]));
                fullList.add(newRestaurant);
                restaurantStack.push(newRestaurant);
                restaurantLinkedList.add(newRestaurant);
            }
            input.close();
        } else {
            System.out.println("File Not Found!\nThe file FoodList.txt is missing.");
        }
        printList(qualifiedRestaurants(fullList, istyle, irating));
        JList list = new JList(listModel(qualifiedRestaurants(fullList, istyle, irating)));
        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane);
        JOptionPane.showMessageDialog(frame, list);
        System.exit(0);
    }

    public static ArrayList<Restaurant> qualifiedRestaurants(ArrayList<Restaurant> inputList, String istyle, double irating) {
        ArrayList<Restaurant> qualifiedRestaurants = new ArrayList<>();
        for (Restaurant restaurant : inputList) {
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

    public static void HelpClass() {
        System.out.println("This software can help you find restaurants in Frederick based on the style of food that you want and the minimum ratings.");
    }

    private static DefaultListModel listModel(ArrayList<Restaurant> inputList) {
        DefaultListModel dlm = new DefaultListModel();
        for (Restaurant restaurant : inputList) {
            dlm.addElement(restaurant.getName() + " | " + restaurant.getStyle() + " | " + restaurant.getRating());
        }
        return dlm;
    }
}