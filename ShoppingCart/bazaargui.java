import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class bazaargui extends JFrame {
    private JButton loginButton;
    private JButton createAccountButton;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private List<String[]> cart; // Corrected cart declaration
    private JTable catalogTable;
    private List<String[]> catalogData; // Store catalog data 

    // Colors
    public Color c1 = new Color(246, 227, 197);
    public Color c2 = new Color(160, 217, 149);
    public Color c3 = new Color(108, 196, 161);
    public Color c4 = new Color(76, 172, 188);
    public Color c5 = new Color(34, 87, 122);
    public Color c6 = new Color(56, 163, 165);
    public Color c7 = new Color(87, 204, 153);
    public Color c8 = new Color(128, 237, 153);

    public bazaargui() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 900); // Increased size of the frame
        setLocationRelativeTo(null); // Center the window

        cart = new ArrayList<>(); // Initialize the cart

        // Initialize CardLayout and card panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);


        // Create the login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        loginPanel.setBackground(c1);

        loginButton = new JButton("Login");
        loginButton.setBackground(c5);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Cambria", Font.BOLD, 20));
        createAccountButton = new JButton("Create Account");
        createAccountButton.setFont(new Font("Cambria", Font.BOLD, 20));
        createAccountButton.setBackground(c5);
        createAccountButton.setForeground(Color.WHITE);
        loginPanel.add(loginButton);
        loginPanel.add(createAccountButton);

        JTextField mainTitle = new JTextField("BINARY BAZAAR");
        mainTitle.setEditable(false);
        mainTitle.setHorizontalAlignment(JTextField.CENTER);
        mainTitle.setFont(new Font("Cambria", Font.BOLD, 30));
        mainTitle.setBackground(c1);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(mainTitle, BorderLayout.NORTH);
        titlePanel.add(loginPanel, BorderLayout.CENTER);

        // Create user panel with three buttons
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        userPanel.setBackground(c1);

        JButton buyButton = new JButton("Buy");
        JButton sellButton = new JButton("Sell");
        JButton exitButton = new JButton("Exit");

        userPanel.add(buyButton);
        userPanel.add(sellButton);
        userPanel.add(exitButton);

        // Create the buy panel
        JPanel buyPanel = new JPanel();
        buyPanel.setLayout(new BorderLayout());
        buyPanel.setBackground(c1);

        JLabel buyLabel = new JLabel("Buy Panel", SwingConstants.CENTER);
        buyLabel.setFont(new Font("Cambria", Font.BOLD, 30));
        buyPanel.add(buyLabel, BorderLayout.NORTH);

        // Create a JTable for the catalog
        catalogTable = new JTable();
        catalogTable.setPreferredScrollableViewportSize(new Dimension(600, 400)); // Set preferred size
        catalogTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(catalogTable);
        buyPanel.add(scrollPane, BorderLayout.CENTER);

        // input panel in the buy panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));
        inputPanel.setBackground(c1);

        JLabel itemNameLabel = new JLabel("Item Name:");
        JTextField itemNameField = new JTextField();
        JLabel itemQuantityLabel = new JLabel("Quantity:");
        JTextField itemQuantityField = new JTextField();
        JButton selectButton = new JButton("Select");

        inputPanel.add(itemNameLabel);
        inputPanel.add(itemNameField);
        inputPanel.add(itemQuantityLabel);
        inputPanel.add(itemQuantityField);
        inputPanel.add(new JLabel()); // empty cell
        inputPanel.add(selectButton);

        // Create and position the back button at the bottom right
        JButton backButton = new JButton("Back");
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backPanel.add(backButton);

        // Combine inputPanel and backPanel into a single container with vertical layout
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(inputPanel);
        bottomPanel.add(backPanel);

        buyPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Create the checkout panel
        JPanel checkoutPanel = new JPanel();
        checkoutPanel.setLayout(new BorderLayout());
        checkoutPanel.setBackground(c1);

        JLabel checkoutLabel = new JLabel("Checkout Panel", SwingConstants.CENTER);
        checkoutLabel.setFont(new Font("Cambria", Font.BOLD, 30));
        checkoutPanel.add(checkoutLabel, BorderLayout.NORTH);

        // Add a panel for the "Place Order" and "Delete from Cart" buttons
        JPanel checkoutButtonPanel = new JPanel(new BorderLayout());
        JButton placeOrderButton = new JButton("Place Order");
        JButton deleteFromCartButton = new JButton("Delete from Cart");

        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftButtonPanel.add(placeOrderButton);
        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightButtonPanel.add(deleteFromCartButton);

        checkoutButtonPanel.add(leftButtonPanel, BorderLayout.WEST);
        checkoutButtonPanel.add(rightButtonPanel, BorderLayout.EAST);

        checkoutPanel.add(checkoutButtonPanel, BorderLayout.SOUTH);

        // Add action listener to back button to return to user panel
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "userPanel");
            }
        });

        buyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Load catalog data from CSV file and display in JTable
                String[] columnNames = {"Item Name", "Price", "Availability", "Quantity"};
                loadCatalogData("catalog.csv");
                Object[][] data = new Object[catalogData.size()][4];
                for (int i = 0; i < catalogData.size(); i++) {
                    data[i] = catalogData.get(i);
                }
                catalogTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));

                cardLayout.show(cardPanel, "buyPanel"); // Switch to buy panel
            }
        });

        // Add action listener to select button
        selectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String itemName = itemNameField.getText();
                String itemQuantity = itemQuantityField.getText();

                // Find the price of the item from the catalog
                String itemPrice = getItemPrice(itemName);

                if (itemPrice == null) {
                    JOptionPane.showMessageDialog(null, "Item not found in the catalog.");
                    return;
                }

                int quantity;
                try {
                    quantity = Integer.parseInt(itemQuantity);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid quantity.");
                    return;
                }

                double price = Double.parseDouble(itemPrice);
                double totalPrice = price * quantity;

                // Display the selected item details
                JOptionPane.showMessageDialog(null, "Item: " + itemName + ", Quantity: " + itemQuantity + ", Total Price: P" + totalPrice);

                // Ask if the user wants to add the item to the cart
                int addToCart = JOptionPane.showConfirmDialog(null, "Do you want to add this item to the cart?", "Add to Cart", JOptionPane.YES_NO_OPTION);
                if (addToCart == JOptionPane.YES_OPTION) {
                    // Add the item to the cart
                    cart.add(new String[]{itemName, itemQuantity, String.valueOf(totalPrice)});

                    // Ask if the user wants to proceed to checkout
                    int checkout = JOptionPane.showConfirmDialog(null, "Do you want to proceed to checkout?", "Checkout", JOptionPane.YES_NO_OPTION);
                    if (checkout == JOptionPane.YES_OPTION) {
                        updateCheckoutPanel(checkoutPanel, checkoutButtonPanel);
                        cardLayout.show(cardPanel, "checkoutPanel");
                    }
                }
            }
        });

        // Add action listeners for the new buttons
        placeOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Order placed successfully!");
                cart.clear(); // Clear the cart
                cardLayout.show(cardPanel, "userPanel"); // Go back to the user panel
            }
        });

        deleteFromCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String itemName = JOptionPane.showInputDialog(null, "Enter the item name to delete:");
                if (itemName != null && !itemName.isEmpty()) {
                    cart.removeIf(item -> item[0].equals(itemName)); // Remove the item from the cart
                    JOptionPane.showMessageDialog(null, "Item removed from the cart.");
                }
                // Refresh the checkout panel
                updateCheckoutPanel(checkoutPanel, checkoutButtonPanel);
            }
        });

        // Add panels to card panel
        cardPanel.add(titlePanel, "loginPanel");
        cardPanel.add(userPanel, "userPanel");
        cardPanel.add(buyPanel, "buyPanel");
        cardPanel.add(checkoutPanel, "checkoutPanel");

        // Add card panel to the frame
        add(cardPanel);

        // Event handling for login and create account buttons
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(null, "Enter username:");
                String password = JOptionPane.showInputDialog(null, "Enter password:");
                boolean isLoggedIn = loginUser(username, password);
                if (isLoggedIn) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    cardLayout.show(cardPanel, "userPanel"); // Switch to user panel
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(null, "Enter username:");
                String password = JOptionPane.showInputDialog(null, "Enter password:");
                boolean createdAccount = createAccount(username, password);
                if (createdAccount) {
                    JOptionPane.showMessageDialog(null, "Account created successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to create account. Please try again.");
                }
            }
        });

        // Event handling for user panel buttons
        buyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Load catalog data from CSV file
                loadCatalogData("catalog.csv");

                // Check if catalogData is null or empty
                if (catalogData == null || catalogData.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Catalog data is empty or could not be loaded.");
                    return;
                }

                // Display catalog data in JTable
                String[] columnNames = {"Item Name", "Price", "Availability", "Quantity"};
                Object[][] data = new Object[catalogData.size()][4];
                for (int i = 0; i < catalogData.size(); i++) {
                    data[i] = catalogData.get(i);
                }
                catalogTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));

                cardLayout.show(cardPanel, "buyPanel"); // Switch to buy panel
            }
        });

        sellButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Load catalog data from CSV file
                loadCatalogData("catalog.csv");
        
                // Check if catalogData is null or empty
                if (catalogData == null || catalogData.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Catalog data is empty or could not be loaded.");
                    return;
                }
        
                // Display initial Sell options using JOptionPane
                String[] options = {"Add", "View", "Delete"};
                int choice = JOptionPane.showOptionDialog(null, "Select an action", "Sell Options", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
                // Handle the choice made by the user
                switch (choice) {
                    case 0: // Add
                        JOptionPane.showMessageDialog(null, "You clicked Add");
        
                        String itemName = JOptionPane.showInputDialog(null, "Enter the item name:");
                        String itemPriceInput = JOptionPane.showInputDialog(null, "Enter the item price:");
                        String itemQuantityInput = JOptionPane.showInputDialog(null, "Enter the item quantity:");
        
                        // Validate inputs
                        if (itemName != null && !itemName.isEmpty() && itemPriceInput != null && !itemPriceInput.isEmpty() && itemQuantityInput != null && !itemQuantityInput.isEmpty()) {
                            try {
                                double itemPrice = Double.parseDouble(itemPriceInput);
                                int itemQuantity = Integer.parseInt(itemQuantityInput);
                                
                                // Check if the item already exists in the catalog
                                boolean itemExists = false;
                                for (String[] item : catalogData) {
                                    if (item[0].equalsIgnoreCase(itemName)) {
                                        itemExists = true;
                                        break;
                                    }
                                }
        
                                if (!itemExists) {
                                    // Add the item to the catalog data
                                    String[] newItem = {itemName, String.valueOf(itemPrice), "Available", String.valueOf(itemQuantity)};
                                    catalogData.add(newItem);
        
                                    // Inform the user that the item has been added
                                    JOptionPane.showMessageDialog(null, "Item added successfully to the catalog.");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Item already exists in the catalog.");
                                }
        
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Invalid price or quantity. Please enter valid numbers.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Please enter valid input for item name, price, and quantity.");
                        } 
                        loadCatalogData("catalog.csv");
                        updateCatalogTableModel();
                        break;
                    case 1: // View
                        JOptionPane.showMessageDialog(null, "You clicked View");
                        // View functionality here
                        break;
                    case 2: // Delete
                        JOptionPane.showMessageDialog(null, "You clicked Delete");
                        // Delete functionality here
                        break;
                    default:
                        // Handle if no option is chosen
                        break;
                }
            }
        });
        
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exit button clicked");
                System.exit(0); // Exit the application
            }
        });
    }

    private boolean loginUser(String userName, String passWord) {
        boolean isLoggedIn = false;
        String[] uNames = new String[0];
        String[] pWords = new String[0];

        try (BufferedReader reader = new BufferedReader(new FileReader("Accounts.csv"))) {
            reader.readLine(); // Skip header

            // Read usernames and passwords from the CSV file
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    uNames = Arrays.copyOf(uNames, uNames.length + 1);
                    pWords = Arrays.copyOf(pWords, pWords.length + 1);
                    uNames[uNames.length - 1] = parts[0].trim();
                    pWords[pWords.length - 1] = parts[1].trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < uNames.length; i++) {
            if (userName.equals(uNames[i]) && passWord.equals(pWords[i])) {
                isLoggedIn = true;
                break;
            }
        }

        return isLoggedIn;
    }

    private boolean createAccount(String userName, String passWord) {
        boolean createdAccount = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Accounts.csv", true))) {
            writer.write(userName + "," + passWord + "\n");
            createdAccount = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return createdAccount;
    }

    private void loadCatalogData(String filePath) {
        catalogData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    catalogData.add(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getItemPrice(String itemName) {
        for (String[] item : catalogData) {
            if (item[0].equalsIgnoreCase(itemName)) {
                return item[1]; // Assuming the price is in the second column of the catalog
            }
        }
        return null;
    }

    private void updateCheckoutPanel(JPanel checkoutPanel, JPanel checkoutButtonPanel) {
        checkoutPanel.removeAll(); // Clear previous contents

        JLabel checkoutLabelInner = new JLabel("Checkout Panel", SwingConstants.CENTER);
        checkoutLabelInner.setFont(new Font("Cambria", Font.BOLD, 30));
        checkoutPanel.add(checkoutLabelInner, BorderLayout.NORTH);

        JTextArea cartTextArea = new JTextArea(10, 30);
        for (String[] item : cart) {
            cartTextArea.append("Item: " + item[0] + ", Quantity: " + item[1] + ", Price: " + item[2] + "\n");
        }
        checkoutPanel.add(new JScrollPane(cartTextArea), BorderLayout.CENTER);

        checkoutPanel.add(checkoutButtonPanel, BorderLayout.SOUTH); // Add the button panel

        checkoutPanel.revalidate(); // Refresh the panel
        checkoutPanel.repaint(); // Repaint the panel
    } 

    // Method to update the catalog table model
    private void updateCatalogTableModel() {
        if (catalogData != null && catalogData.size() > 0) {
            String[] columnNames = {"Item Name", "Price", "Availability", "Quantity"};
            Object[][] data = new Object[catalogData.size()][4];
            for (int i = 0; i < catalogData.size(); i++) {
                data[i] = catalogData.get(i);
            }
            catalogTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new bazaargui().setVisible(true);
            }
        });
    }
}