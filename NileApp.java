/* Name: Justin Cepeda
   Course: CNT 4714 - Spring 2023
   Assignment title: Project 1 - Event-driven Enterprise Simulation
   Date: Sunday January 29, 2023
 */
package AppPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class NileApp {

    public Item item ;
    public int itemNumber = 1;
    public int totalItems = 0;
    public double subtotal = 0;
    public ArrayList <Item> cart = new ArrayList<>();
    public String id = "";
    public String quantity = "";
    public String price = "";
    public String input[] = new String [4];

    private JPanel mainPanel;

    private JPanel topPanel;
    private JPanel topPanel2;
    private JPanel topUpperSpacer;
    private JLabel itemIdLabel;
    private JLabel itemQuantityLabel;
    private JLabel itemDetailsLabel;
    private JLabel subtotalLabel;
    private JTextField idTextField;
    private JTextField quantityTextField;
    private JTextField detailsTextField;
    private JTextField subtotalTextField;
    private JPanel topLowerSpacer;

    private JPanel bottomPanel;
    private JButton exitButton;
    private JButton proceedToCheckoutButton;
    private JButton addToCartButton;
    private JButton findItemButton;
    private JButton viewCurrentCartButton;
    private JButton startANewOrderButton;
    private JPanel bottomLowerSpacer;


    public NileApp() {
    findItemButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            id = idTextField.getText();
            quantity = quantityTextField.getText();

            try{
                Scanner scanner = new Scanner(new File("C:\\Users\\stick\\Desktop\\Misc Files\\Downloads\\inventory.txt"));

                while (scanner.hasNextLine()) {

                    input = scanner.nextLine().split(",", 4);

                    if(input[0].replace(" ", "").equals(id)) {

                        if(input[2].replace(" ", "").equals("false")) {

                            JFrame jFrame = new JFrame();

                            JDialog jd = new JDialog(jFrame);

                            jd.setLayout(new FlowLayout());

                            jd.setBounds(500, 300, 400, 100);

                            JLabel jLabel = new JLabel("This item is out of stock.");

                            JButton jButton = new JButton("Close");
                            jButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    jd.setVisible(false);
                                }
                            });

                            jd.add(jLabel);
                            jd.add(jButton);
                            jd.setVisible(true);;
                            break;
                        }

                        item = new Item(id, quantity, input);
                        subtotal += (item.quantity) * (Double.parseDouble(item.price));

                        addToCartButton.setText("Add Item #" + itemNumber + " to Cart");
                        itemDetailsLabel.setText("Details for Item #" + itemNumber);

                        detailsTextField.setText(item.id + item.details + " $" + item.price + " " + item.quantity + " "
                                + item.discount + "% $" + item.finalPrice);
                        addToCartButton.setEnabled(true);
                        break;
                    }
                    else{

                        JFrame jFrame = new JFrame();

                        JDialog jd = new JDialog(jFrame);

                        jd.setLayout(new FlowLayout());

                        jd.setBounds(500, 300, 400, 100);

                        JLabel jLabel = new JLabel("Item ID " + id + " is not in the file.");

                        JButton jButton = new JButton("Close");
                        jButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                jd.setVisible(false);
                            }
                        });

                        jd.add(jLabel);
                        jd.add(jButton);
                        jd.setVisible(true);;
                        break;
                    }
                }

                scanner.close();
            }
            catch (FileNotFoundException c){
                c.printStackTrace();
            }

        }
    });
    startANewOrderButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            itemNumber = 1;
            idTextField.setText("");
            quantityTextField.setText("");
            detailsTextField.setText("");
            subtotalTextField.setText("");

            findItemButton.setText("Find Item #" + itemNumber);
            itemIdLabel.setText("Enter ID for Item #" + itemNumber);
            itemQuantityLabel.setText("Enter Quantity for Item #" + itemNumber);
            itemDetailsLabel.setText("Details for Item #" + itemNumber);
            subtotalLabel.setText("Order Subtotal for " + totalItems + " item(s)");

            addToCartButton.setEnabled(false);
            viewCurrentCartButton.setEnabled(false);
            proceedToCheckoutButton.setEnabled(false);
            ArrayList <Item> cart = new ArrayList<>();
        }
    });
    exitButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            System.exit(0);
        }
    });
    addToCartButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            itemNumber += 1;
            totalItems += 1;

            findItemButton.setText("Find Item #" + itemNumber);
            itemIdLabel.setText("Enter ID for Item #" + itemNumber);
            itemQuantityLabel.setText("Enter Quantity for Item #" + itemNumber);
            subtotalLabel.setText("Order Subtotal for " + totalItems + " item(s)");
            subtotalTextField.setText("$" + subtotal);

            cart.add(item);

            JFrame jFrame = new JFrame();

            JDialog jd = new JDialog(jFrame);

            jd.setLayout(new FlowLayout());

            jd.setBounds(500, 300, 400, 100);

            JLabel jLabel = new JLabel("Item #" + (itemNumber - 1) + " accepted. Added to your cart.");

            JButton jButton = new JButton("Close");
            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jd.setVisible(false);
                }
            });

            jd.add(jLabel);
            jd.add(jButton);
            jd.setVisible(true);;

            viewCurrentCartButton.setEnabled(true);
            proceedToCheckoutButton.setEnabled(true);
        }
    });
    viewCurrentCartButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            String temp = "";
            for( int i = 0; i < cart.size(); i ++){

                 temp += (i + 1) + ". " + cart.get(i).id + " \"" + cart.get(i).details + "\" $" + cart.get(i).price + " " +
                        cart.get(i).quantity + " " + cart.get(i).discount + "% $" + cart.get(i).finalPrice + "\n";
            }

            JOptionPane.showMessageDialog(null, temp);
        }
    });
    proceedToCheckoutButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("M/d/yy h:mm:ss a");
            String temp = "Date: " +  LocalDateTime.now().format(df) + " EST"+ "\n\nNumber of Line Items: " + cart.size() +
                    "\n\nItem# / ID / Title / Price / Qty / Disc% / Subtotal:\n\n";

            for( int i = 0; i < cart.size(); i ++){

                temp += (i + 1) + ". " + cart.get(i).id + " \"" + cart.get(i).details + "\" $" + cart.get(i).price + " " +
                        cart.get(i).quantity + " " + cart.get(i).discount + "% $" + cart.get(i).finalPrice + "\n";
            }

            temp += "\n\nOrder Subtotal: $" + subtotal + "\n\nTax Rate: 6%\n\nTax Amount: $" + (subtotal * 0.06) +
                    "\n\nORDER TOTAL: " + (subtotal * 1.06) + "\n\nThanks for shopping at Nile Dot Com!";

            JOptionPane.showMessageDialog(null, temp);

            String output ="";
            df = DateTimeFormatter.ofPattern("dMMyyyyhmm");
            for( int i = 0; i < cart.size(); i ++){

                output += LocalDateTime.now().format(df) + ", " + cart.get(i).id + ", \"" + cart.get(i).details + "\", $" + cart.get(i).price + ", " +
                        cart.get(i).quantity + ", " + cart.get(i).discount + "%, $" + cart.get(i).finalPrice + "\n";
            }

            try {
                FileWriter myWriter = new FileWriter("C:\\Users\\stick\\Desktop\\Misc Files\\Downloads\\transaction.txt");
                myWriter.write(output);
                myWriter.close();

            }
            catch (IOException c) {
                System.out.println("An error occurred.");
                c.printStackTrace();
            }
        }
    });
}

    public static void main(String[] args) {
        BufferedReader reader;
        JFrame frame = new JFrame("NileApp");
        frame.setContentPane(new NileApp().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
