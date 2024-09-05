import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BBazaarGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JPanel userPanel;
    private JPanel buyPanel;
    private JPanel sellPanel;
    private JPanel checkoutPanel;
    private JPanel delSellPanel;
    private JPanel cashierPanel;
    private JPanel receiptPanel;
    private JPanel onlinePayPanel;
    private JButton loginButton;
    private JButton createAccountButton;
    private JButton buyButton;
    private JButton sellButton;
    private JButton exitButton;
    private JButton placeOrder;
    private JButton addButton;
    private JButton delButton;
    private JButton backFrSellButton;
    private JPanel viewCartPanel;
    private JButton cartCheckout;
    private JButton cartButton;
    private JButton selectButton;
    private JButton delSelectButton;
    private JButton backtoBuy;
    private JButton backToSell ;
    private JButton delCartButton;
    private JButton backtoCart;
    private JButton onlinePay;
    private JButton cod;

    private JTextField nameField;
    private JTextField numberField;
    private JTextField paymentField;
    private JButton payButton;


    //MIA'S COLOR CORNER
    public Color c1 = new Color(246, 227, 197);
    public Color c2 = new Color(160, 217, 149);
    public Color c3 = new Color(108, 196, 161);
    public Color c4 = new Color(105, 201, 219);
    public Color c5 = new Color(34, 87, 122);
    public Color c6 = new Color(56, 163, 165);
    public Color c7 = new Color(87, 204, 153);
    public Color c8 = new Color(128, 237, 153);

    //backend components
    private boolean isLoggedIn = false ;
    public static String[] itemNames = new String[1];
    public static String[] itemPrices = new String[1];
    public static boolean[] itemAvailability = new boolean[1];
    public static int[] itemQuantity = new int[1];
    public static String[] ownedItems = new String[1];
    public static String userFile = "" ;
    public String[] itemCart = new String[0];
    public String sellItem = "";
    public int sellPrice = 0;
    public String sellChoice = "";
    public static String selected = "";
    public double overallTotal ;


    public BBazaarGUI() throws IOException {
        loadCatalogFromCSV("catalog.csv") ;
        frame = new JFrame("BBazaar GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 900);
        frame.setLocationRelativeTo(null);

        //MAIN
        frame = new JFrame("BBazaar GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 900);
        frame.setLocationRelativeTo(null);

        //MAIN
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(c5);
        mainPanel.setPreferredSize(new Dimension(700, 900));

        Dimension buttonSize = new Dimension(500,50);

        loginButton = new JButton("Login");
        loginButton.setPreferredSize(buttonSize);
        loginButton.setMaximumSize(buttonSize);
        loginButton.setBackground(c1);
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(new Font("Cambria", Font.BOLD, 20));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        createAccountButton = new JButton("Create Account");
        createAccountButton.setPreferredSize(buttonSize);
        createAccountButton.setMaximumSize(buttonSize);
        createAccountButton.setFont(new Font("Cambria", Font.BOLD, 20));
        createAccountButton.setBackground(c1);
        createAccountButton.setForeground(Color.BLACK);
        createAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon imageIcon = new ImageIcon("mainPanel.png"); // Replace with the path to your image
        JLabel mainLabel = new JLabel(imageIcon);
        mainLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add vertical glue to center the buttons
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(mainLabel);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(loginButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between buttons
        mainPanel.add(createAccountButton);
        mainPanel.add(Box.createVerticalGlue());

        ImageIcon imageIcon1 = new ImageIcon("mainPanel.png"); // Replace with the path to your image
        JLabel userLabel = new JLabel(imageIcon1);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //USER
        userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setBackground(c5);
        userPanel.setPreferredSize(new Dimension(700,900));

        buyButton = new JButton("Buy");
        buyButton.setPreferredSize(buttonSize);
        buyButton.setMaximumSize(buttonSize);
        buyButton.setBackground(c1);
        buyButton.setForeground(Color.BLACK);
        buyButton.setFont(new Font("Cambria", Font.BOLD, 20));
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        sellButton = new JButton("Sell");
        sellButton.setPreferredSize(buttonSize);
        sellButton.setMaximumSize(buttonSize);
        sellButton.setBackground(c1);
        sellButton.setForeground(Color.BLACK);
        sellButton.setFont(new Font("Cambria", Font.BOLD, 20));
        sellButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        exitButton = new JButton("Exit");
        exitButton.setPreferredSize(buttonSize);
        exitButton.setMaximumSize(buttonSize);
        exitButton.setBackground(c1);
        exitButton.setForeground(Color.BLACK);
        exitButton.setFont(new Font("Cambria", Font.BOLD, 20));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        userPanel.add(Box.createVerticalGlue());
        userPanel.add(userLabel);
        userPanel.add(Box.createVerticalGlue());
        userPanel.add(buyButton);
        userPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        userPanel.add(sellButton);
        userPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        userPanel.add(exitButton);
        userPanel.add(Box.createVerticalGlue());

        // BUY
        buyPanel = new JPanel();
        buyPanel.setLayout(new BoxLayout(buyPanel, BoxLayout.Y_AXIS)); // Set layout to BoxLayout with Y_AXIS alignment
        buyPanel.setBackground(c5);
        buyPanel.setPreferredSize(new Dimension(700,900));

        addItemsToBuyPanel();

        // Add buyPanel to frame
        JScrollPane scrollPane = new JScrollPane(buyPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        //VIEW CART
        viewCartPanel = new JPanel();
        viewCartPanel.setLayout(new BoxLayout(viewCartPanel, BoxLayout.Y_AXIS));
        viewCartPanel.setBackground(c5);
        viewCartPanel.setPreferredSize(new Dimension(700,900));



        //CHECKOUT
        checkoutPanel = new JPanel();
        checkoutPanel.setLayout(new BoxLayout(checkoutPanel, BoxLayout.Y_AXIS));
        checkoutPanel.setBackground(c5);
        checkoutPanel.setPreferredSize(new Dimension(700,900));
        checkoutPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkoutPanel.setAlignmentY(Component.LEFT_ALIGNMENT);



        //CASHIER
        cashierPanel = new JPanel();
        cashierPanel.setLayout(new BoxLayout(cashierPanel, BoxLayout.Y_AXIS));
        cashierPanel.setBackground(c5);
        cashierPanel.setPreferredSize(new Dimension(700,900));

        //SELL
        sellPanel = new JPanel() ;
        sellPanel.setLayout(new BoxLayout(sellPanel, BoxLayout.Y_AXIS));
        sellPanel.setBackground(c5);
        sellPanel.setPreferredSize(new Dimension(700,900));

        addButton = new JButton("Add");
        addButton.setPreferredSize(buttonSize);
        addButton.setMaximumSize(buttonSize);
        addButton.setBackground(c1);
        addButton.setForeground(Color.BLACK);
        addButton.setFont(new Font("Cambria", Font.BOLD, 20));
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        delButton = new JButton("Delete");
        delButton.setPreferredSize(buttonSize);
        delButton.setMaximumSize(buttonSize);
        delButton.setBackground(c1);
        delButton.setForeground(Color.BLACK);
        delButton.setFont(new Font("Cambria", Font.BOLD, 20));
        delButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        backFrSellButton = new JButton("Back");
        backFrSellButton.setPreferredSize(buttonSize);
        backFrSellButton.setMaximumSize(buttonSize);
        backFrSellButton.setBackground(c1);
        backFrSellButton.setForeground(Color.BLACK);
        backFrSellButton.setFont(new Font("Cambria", Font.BOLD, 20));
        backFrSellButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sellLabel = new JLabel(imageIcon1);
        sellLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        sellPanel.add(Box.createVerticalGlue());
        sellPanel.add(sellLabel);
        sellPanel.add(Box.createVerticalGlue());
        sellPanel.add(addButton);
        sellPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sellPanel.add(delButton);
        sellPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sellPanel.add(backFrSellButton);
        sellPanel.add(Box.createVerticalGlue());

        //DELETE SELLING ITEMS
        delSellPanel = new JPanel();
        delSellPanel.setLayout(new BoxLayout(delSellPanel, BoxLayout.Y_AXIS)); // Set layout to BoxLayout with Y_AXIS alignment
        delSellPanel.setBackground(c5);
        delSellPanel.setPreferredSize(new Dimension(700,900));
        JLabel delHeaderLabel = new JLabel("Delete Owned Items");
        delHeaderLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the text horizontally
        delHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        delHeaderLabel.setFont(new Font("Cambria", Font.BOLD, 20)); // Set font and size
        delHeaderLabel.setForeground(c1);
        delSellPanel.add(delHeaderLabel); // Add header label
        JLabel delNameLabel = new JLabel("Name");
        delNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addOwnedItemstoDelPanel() ;

        //RECEIPT
        receiptPanel = new JPanel();
        receiptPanel.setLayout(new BoxLayout(receiptPanel, BoxLayout.Y_AXIS));
        receiptPanel.setBackground(c5);
        receiptPanel.setPreferredSize(new Dimension(700,900));


        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER) ;

        frame.add(mainPanel);
        frame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String uName = JOptionPane.showInputDialog("Enter username: ") ;
                String pWord = JOptionPane.showInputDialog("Enter password: ") ;
                try {
                    isLoggedIn = loginUser(uName, pWord) ;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                if(!isLoggedIn){
                    JOptionPane.showMessageDialog(null, "Account not found") ;
                } else {
                    switchPanels();
                    try {
                        loadOwnedItemsCSV(userFile);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                System.out.println(isLoggedIn);
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String uName = JOptionPane.showInputDialog("Enter username: ") ;
                String pWord = JOptionPane.showInputDialog("Enter password: ") ;
                try {
                    createAccount(uName, pWord) ;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("buy is pressed");
                frame.getContentPane().removeAll();
                frame.add(buyPanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("sell is pressed");
                System.out.println(ownedItems.length);
                frame.getContentPane().removeAll();
                frame.add(sellPanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exit button clicked");
                System.exit(0); // Exit the application
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("add is pressed");
                System.out.print("Enter the name of the item: ");
                sellItem = JOptionPane.showInputDialog("Enter the name of the item: ") ;
                boolean isValidPrice = false; 
                boolean isValidQty = false ; 
                int sellQuantity  = 0; 
                boolean hasDup = false ; 

                for(int i = 0 ; i < itemNames.length ; i++){ 
                    if(sellItem.equalsIgnoreCase(itemNames[i])){ 
                        hasDup = true ; 
                        JOptionPane.showMessageDialog(null, "There is already an item for sale with the same name"); 
                        break ;
                    }
                }
                if(sellItem.length() >= 1 && !hasDup){ 
                    do {
                        try {
                            sellPrice = Integer.parseInt(JOptionPane.showInputDialog("Enter the price of the item: "));
                            isValidPrice = true;
    
                        } catch (NumberFormatException ew) {
                            JOptionPane.showMessageDialog(null, "Invalid Input, try again");
                            isValidPrice = false;
                        }
                    } while (!isValidPrice); 
                    do{ 
                        try {
                            sellQuantity = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity of the item: "));
                            isValidQty = true;
    
                        } catch (NumberFormatException ew) {
                            JOptionPane.showMessageDialog(null, "Invalid Input, try again");
                            isValidQty = false;
                        }
                    }while(!isValidQty) ; 
                } else { 
                    JOptionPane.showMessageDialog(null, "Invalid Input");
                } 
                

                if (isValidPrice && isValidQty) {
                    String sellPriceStr = "" + sellPrice;
                    itemNames = sellFunc(itemNames, sellItem);
                    itemPrices = upItemPrice(itemPrices, sellPriceStr);
                    itemAvailability = upAvailability(itemAvailability);
                    itemQuantity = upItemQuantity(itemQuantity, sellQuantity);
                    addItemsToBuyPanel();
                    addOwnedItemstoDelPanel() ;

                    ownedItems = addToOwnedItems(ownedItems, sellItem);
                    try {
                        saveOwnedItemsCSV(userFile) ;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        saveCatalogToCSV(itemNames, itemPrices, itemAvailability, itemQuantity, "catalog.csv");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("delete is pressed");

                // Debugging: Print out owned items
                for (int i = 0; i < ownedItems.length; i++) {
                    System.out.println(ownedItems[i]);
                }

                if (ownedItems.length > 0) {
                    // Ensure the panel is cleared before adding new components
                    delSellPanel.removeAll();

                    addOwnedItemstoDelPanel(); // Make sure to call this method to add items to the panel

                    // Switch to the delSellPanel
                    frame.getContentPane().removeAll();
                    frame.add(delSellPanel);
                    frame.revalidate();
                    frame.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "You have no items for sale");
                }
            }
        });

        backFrSellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("back is pressed");
                frame.getContentPane().removeAll();
                frame.add(userPanel);
                frame.revalidate();
                frame.repaint();
            }
        });

    }

    public static boolean createAccount(String usern, String pass) throws IOException {
        boolean createdAccount = false;
        String userName = usern ;
        String passWord = pass ;
        String[] uNames = new String[1] ;
        String[] pWords = new String[1] ;

        try (BufferedReader reader = new BufferedReader(new FileReader("Accounts.csv"))) {
            reader.readLine();

            // Read each line and parse the item information
            String line;
            int itemCount = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    // Resize arrays if needed
                    if (itemCount >= uNames.length) {
                        String[] tempUNameArr = new String[uNames.length+1] ;
                        String[] tempPWordArr = new String[pWords.length+1] ;
                        System.arraycopy(uNames, 0, tempUNameArr, 0, uNames.length);
                        System.arraycopy(pWords, 0, tempPWordArr, 0, pWords.length);
                        uNames = tempUNameArr ;
                        pWords = tempPWordArr ;
                    }
                    uNames[itemCount] = parts[0].trim() ;
                    pWords[itemCount] = parts[1].trim() ;
                    itemCount++;
                }
            }
        }

        boolean hasDup = true ;

        for(int i = 0 ; i < uNames.length ; i++){
            if(userName.equalsIgnoreCase(uNames[i])){
                System.out.println("Account with that username already exists");
                break ;
            } else {
                hasDup = false ;
            }
        }

        if(!hasDup){
            String[] tempUNameArr = new String[uNames.length+1] ;
            String[] tempPWordArr = new String[pWords.length+1] ;
            System.arraycopy(uNames, 0, tempUNameArr, 0, uNames.length);
            System.arraycopy(pWords, 0, tempPWordArr, 0, pWords.length);
            tempUNameArr[uNames.length] = userName ;
            tempPWordArr[pWords.length] = passWord ;
            uNames = tempUNameArr ;
            pWords = tempPWordArr ;

            try (FileWriter writer = new FileWriter("Accounts.csv")) {
                writer.write("Username,Password\n"); // Header

                for (int i = 0; i < uNames.length; i++) {
                    writer.write(uNames[i] + "," + pWords[i] + "\n");
                }
            }
            System.out.println("Account has been saved to Accounts.csv");
            System.out.println();
            createdAccount = true ;
        }


        //CREATE NEW CSV FILE FOR USER
        if(createdAccount){
            String filePath = "Users/"+ userName + ".csv" ;
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) { // Open the file in append mode
                if (new File(filePath).length() == 0) {
                    writer.println("Owned Items");
                }
            }
        }

        return createdAccount;
    }

    public static boolean loginUser(String userName, String passWord) throws IOException{
        boolean isLoggedIn = false ;
        String[] uNames = new String[1] ;
        String[] pWords = new String[1] ;

        //LOAD UP ALL ACCOUNTS FROM CSV
        try (BufferedReader reader = new BufferedReader(new FileReader("Accounts.csv"))) {
            reader.readLine();

            // Read each line and parse the item information
            String line;
            int itemCount = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    // Resize arrays if needed
                    if (itemCount >= uNames.length) {
                        String[] tempUNameArr = new String[uNames.length+1] ;
                        String[] tempPWordArr = new String[pWords.length+1] ;
                        System.arraycopy(uNames, 0, tempUNameArr, 0, uNames.length);
                        System.arraycopy(pWords, 0, tempPWordArr, 0, pWords.length);
                        uNames = tempUNameArr ;
                        pWords = tempPWordArr ;
                    }
                    uNames[itemCount] = parts[0].trim() ;
                    pWords[itemCount] = parts[1].trim() ;
                    itemCount++;
                }
            }
        }

        for(int i = 0 ; i < uNames.length ; i ++){
            if(userName.equals(uNames[i]) && passWord.equals(pWords[i])){
                isLoggedIn = true ;
                userFile = "Users/" + uNames[i] + ".csv" ;
                break ;

            } else {
                isLoggedIn = false ;
            }
        }


        return isLoggedIn ;
    }

    private void switchPanels() {
        frame.getContentPane().removeAll(); // Remove all components from the frame
        if (isLoggedIn) {
            // Add userPanel to the frame if logged in
            frame.add(userPanel);
        } else {
            // Add mainPanel to the frame if not logged in
            frame.add(mainPanel);
        }
        // Repaint the frame to reflect changes
        frame.revalidate();
        frame.repaint();
    }

    private void viewCart() {
        // Debugging: Print a message indicating this method is called
        viewCartPanel.removeAll();
        ImageIcon viewcartIcon = new ImageIcon("ViewCart.png"); // Replace with the path to your image
        JLabel veiwcartLabel = new JLabel(viewcartIcon);
        veiwcartLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        viewCartPanel.add(veiwcartLabel);
        System.out.println("viewCart method called");

        // Add header
        JLabel headerLabel = new JLabel("Cart Items");
        headerLabel.setForeground(c4);
        headerLabel.setFont(new Font("Impact", Font.BOLD, 35));
        //viewCartPanel.add(headerLabel);

        Dimension buttonSize = new Dimension(500, 50);

        // Configure and add the Delete button
        delCartButton = new JButton("Delete");
        delCartButton.setPreferredSize(buttonSize);
        delCartButton.setMaximumSize(buttonSize);
        delCartButton.setBackground(c1);
        delCartButton.setForeground(Color.BLACK);
        delCartButton.setFont(new Font("Cambria", Font.BOLD, 20));
        delCartButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        // checkout
        cartCheckout = new JButton("Checkout");
        cartCheckout.setPreferredSize(buttonSize);
        cartCheckout.setMaximumSize(buttonSize);
        cartCheckout.setBackground(c1);
        cartCheckout.setForeground(Color.BLACK);
        cartCheckout.setFont(new Font("Cambria", Font.BOLD, 20));
        cartCheckout.setAlignmentX(Component.LEFT_ALIGNMENT);


        // Configure and add the Back button
        backtoBuy = new JButton("Back");
        backtoBuy.setPreferredSize(buttonSize);
        backtoBuy.setMaximumSize(buttonSize);
        backtoBuy.setBackground(c1);
        backtoBuy.setForeground(Color.BLACK);
        backtoBuy.setFont(new Font("Cambria", Font.BOLD, 20));
        backtoBuy.setAlignmentX(Component.LEFT_ALIGNMENT);



        ButtonGroup group = new ButtonGroup();

        // Add items to the viewCartPanel
        for (int i = 0; i < itemCart.length; i += 3) {
            JRadioButton itemRadioButton = new JRadioButton(itemCart[i]);
            itemRadioButton.setActionCommand(itemCart[i]); // Set action command to the item name for identification
            itemRadioButton.setBackground(c5);
            itemRadioButton.setFont(new Font("Cambria", Font.BOLD, 23));
            itemRadioButton.setForeground(c1); // Set text color
            itemRadioButton.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left

            viewCartPanel.add(itemRadioButton);
            group.add(itemRadioButton);

            itemRadioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selected = group.getSelection().getActionCommand();
                }
            });
        }

        viewCartPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        viewCartPanel.add(delCartButton);
        viewCartPanel.add(Box.createRigidArea(new Dimension(20, 20)));// Add space between the delete button and the back button
        viewCartPanel.add(cartCheckout);
        viewCartPanel.add(Box.createRigidArea(new Dimension(20, 20))); // Add space between the delete button and the back button
        viewCartPanel.add(backtoBuy);



        // Optionally revalidate and repaint viewCartPanel if dynamically updating
        viewCartPanel.revalidate();
        viewCartPanel.repaint();

        delCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Delete from cart is pressed " + selected);
                int cartIndexToRemove = -1 ;
                for(int i = 0 ; i < itemCart.length ; i++){
                    if(selected.equalsIgnoreCase(itemCart[i])){
                        cartIndexToRemove = i ;
                        break ;
                    }
                }

                String[] tempArr = new String[itemCart.length - 3];
                System.arraycopy(itemCart, 0, tempArr, 0, cartIndexToRemove);
                int copyLength = itemCart.length - cartIndexToRemove - 3;
                if (copyLength > 0) {
                    System.arraycopy(itemCart, cartIndexToRemove + 3, tempArr, cartIndexToRemove, copyLength);
                }
                itemCart = tempArr;

                viewCart();
            }
        });

        backtoBuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Going to buy panel");
                frame.getContentPane().removeAll();
                frame.add(buyPanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        cartCheckout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Going to checkout panel");
                frame.getContentPane().removeAll();
                frame.add(checkoutPanel);
                CheckoutPlace();
                frame.revalidate();
                frame.repaint();
            }
        });
        // Show frame
        frame.setVisible(true);
    }

    private void CheckoutPlace() {
        checkoutPanel.removeAll();
        ImageIcon checkoutIcon = new ImageIcon("Checkout.png"); // Replace with the path to your image
        JLabel checkoutLabel = new JLabel(checkoutIcon);
        checkoutLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkoutPanel.add(checkoutLabel);
        System.out.println("checkout display called");

        // Header label
        JLabel headerLabel = new JLabel("Checkout");
        headerLabel.setForeground(c4);
        headerLabel.setFont(new Font("Impact", Font.BOLD, 35));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerLabel.setAlignmentY(Component.LEFT_ALIGNMENT);
        //checkoutPanel.add(headerLabel);

        Dimension buttonSize = new Dimension(500, 50);

        for (int i = 0; i + 2 < itemCart.length; i += 3) {
            String itemName = itemCart[i];
            String itemPrice = itemCart[i + 1];
            String itemQuantity = itemCart[i + 2];
            String itemDisplay = String.format("%s - Price: %s - Quantity: %s", itemName, itemPrice, itemQuantity);

            JLabel itemLabel = new JLabel(itemDisplay);
            itemLabel.setFont(new Font("Cambria", Font.BOLD, 20));
            itemLabel.setForeground(c1);
            itemLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            itemLabel.setAlignmentY(Component.LEFT_ALIGNMENT);

            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS));
            itemPanel.setBackground(c5);
            itemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            itemPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
            itemPanel.add(itemLabel);

            checkoutPanel.add(itemPanel);
        }

        placeOrder = new JButton("Place Order");
        placeOrder.setPreferredSize(buttonSize);
        placeOrder.setMaximumSize(buttonSize);
        placeOrder.setBackground(c1);
        placeOrder.setForeground(Color.BLACK);
        placeOrder.setFont(new Font("Cambria", Font.BOLD, 20));
        placeOrder.setAlignmentX(Component.LEFT_ALIGNMENT);

        backtoCart = new JButton("Back");
        backtoCart.setPreferredSize(buttonSize);
        backtoCart.setMaximumSize(buttonSize);
        backtoCart.setBackground(c1);
        backtoCart.setForeground(Color.BLACK);
        backtoCart.setFont(new Font("Cambria", Font.BOLD, 20));
        backtoCart.setAlignmentX(Component.LEFT_ALIGNMENT);

        checkoutPanel.add(Box.createRigidArea(new Dimension(20, 20))); // Add space between components
        checkoutPanel.add(placeOrder);
        checkoutPanel.add(Box.createRigidArea(new Dimension(20, 20))); // Add space between components
        checkoutPanel.add(backtoCart);

        checkoutPanel.revalidate();
        checkoutPanel.repaint();

        System.out.println("checkout display completed");

        placeOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Going to cashier panel");
                frame.getContentPane().removeAll();
                frame.add(cashierPanel);
                cashierPanelfunc();
                frame.revalidate();
                frame.repaint();
            }
        });

        backtoCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Going back to View Cart");
                frame.getContentPane().removeAll();
                frame.add(viewCartPanel);
                frame.revalidate();
                frame.repaint();
            }
        });
    }


    private void cashierPanelfunc(){
        cashierPanel.removeAll();
        ImageIcon cashierIcon = new ImageIcon("Cashier.png"); // Replace with the path to your image
        JLabel cashierLabel = new JLabel(cashierIcon);
        cashierLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        cashierPanel.add(cashierLabel);
        System.out.println("cashier display called");


        //int leftMargin = 10;
        //int topMargin = 10;
        //int labelHeight = 30;
        //int labelWidth = 400;

        //int currentY = topMargin + labelHeight + 10;

        JLabel headerLabel = new JLabel("Cashier");
        headerLabel.setForeground(c4);
        headerLabel.setFont(new Font("Impact", Font.BOLD, 35));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
       // cashierPanel.add(headerLabel);

        Dimension buttonSize = new Dimension(500, 50);
        double overallTotal = 0.0;

        for (int i = 0; i + 2 < itemCart.length; i += 3) {
            String itemName = itemCart[i];
            double itemPrice = Double.parseDouble(itemCart[i + 1]);
            int itemQuantity = Integer.parseInt(itemCart[i + 2]);
            double itemTotal = itemPrice * itemQuantity;
            overallTotal += itemTotal;

            String itemDisplay = String.format("%s - Price: %.2f - Quantity: %d - Total: %.2f", itemName, itemPrice, itemQuantity, itemTotal);

            JLabel itemLabel = new JLabel(itemDisplay);
            itemLabel.setFont(new Font("Cambria", Font.BOLD, 20));
            itemLabel.setForeground(c1);
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS));
            itemPanel.setBackground(c5);
            itemPanel.add(itemLabel);
            itemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            cashierPanel.add(itemPanel);
        }

        JLabel overallTotalLabel = new JLabel(String.format("Overall Total: %.2f", overallTotal));
        overallTotalLabel.setFont(new Font("Cambria", Font.BOLD, 20));
        overallTotalLabel.setForeground(c1);

        onlinePay = new JButton("Online Payment");
        onlinePay.setPreferredSize(buttonSize);
        onlinePay.setMaximumSize(buttonSize);
        onlinePay.setBackground(c1);
        onlinePay.setForeground(Color.BLACK);
        onlinePay.setFont(new Font("Cambria", Font.BOLD, 20));
        onlinePay.setAlignmentX(Component.LEFT_ALIGNMENT);

        cod = new JButton("Cash on Delivery");
        cod.setPreferredSize(buttonSize);
        cod.setMaximumSize(buttonSize);
        cod.setBackground(c1);
        cod.setForeground(Color.BLACK);
        cod.setFont(new Font("Cambria", Font.BOLD, 20));
        cod.setAlignmentX(Component.LEFT_ALIGNMENT);

        cashierPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        cashierPanel.add(overallTotalLabel);
        cashierPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        cashierPanel.add(onlinePay);
        cashierPanel.add(Box.createRigidArea(new Dimension(20, 20))); // Add space between components
        cashierPanel.add(cod);

        cashierPanel.revalidate();
        cashierPanel.repaint();

        System.out.println("cashier display completed");

        onlinePay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("going to receipt");
                frame.getContentPane().removeAll();
                frame.add(receiptPanel);
                receiptPanelFunc();
                frame.revalidate();
                frame.repaint();
            }
        });

        cod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orderName = JOptionPane.showInputDialog("Enter name: ") ;
                System.out.println(orderName + " placed order for: ");
                for(int i = 0 ; i < itemCart.length ; i+=3){
                    for(int j = 0 ; j < itemNames.length ; j++){
                        if(itemCart[i].equalsIgnoreCase(itemNames[j])){
                            String itemToRemove = itemCart[i];
                            int qtyToRemove = Integer.parseInt(itemCart[i + 2]) ;
                            if(itemQuantity[j] <= 0 ){
                                itemPrices = delItemPrices(itemPrices, itemNames, itemToRemove);
                                itemAvailability = delItemAvail(itemAvailability, itemNames, itemToRemove);
                                itemQuantity = delItemQuantity(itemQuantity, itemNames, itemToRemove);
                                itemNames = delItemNames(itemNames, itemToRemove);
                                try {
                                    saveCatalogToCSV(itemNames, itemPrices, itemAvailability, itemQuantity, "catalog.csv");
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            } else {
                                itemQuantity[j] = itemQuantity[j] - qtyToRemove ;
                                if(itemQuantity[j] <= 0 ){
                                    itemPrices = delItemPrices(itemPrices, itemNames, itemToRemove);
                                    itemAvailability = delItemAvail(itemAvailability, itemNames, itemToRemove);
                                    itemQuantity = delItemQuantity(itemQuantity, itemNames, itemToRemove);
                                    itemNames = delItemNames(itemNames, itemToRemove);
                                    try {
                                        saveCatalogToCSV(itemNames, itemPrices, itemAvailability, itemQuantity, "catalog.csv");
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                                try {
                                    saveCatalogToCSV(itemNames, itemPrices, itemAvailability, itemQuantity, "catalog.csv");
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "Please prepare amount upon delivery.");
                try {
                    savetoCSV(itemCart, "Orders.csv", orderName);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                itemCart = new String[0];
                addItemsToBuyPanel();
                switchPanels();
            }
        });


    }

    private void receiptPanelFunc() {
        receiptPanel.removeAll(); 
        receiptPanel.setLayout(null);
        ImageIcon receiptIcon = new ImageIcon("OnlinePayment.png"); // Replace with the path to your image
        JLabel receiptLabel = new JLabel(receiptIcon); 
        System.out.println("online payment display called"); 
        receiptLabel.setBounds(0, 10, receiptIcon.getIconWidth(), receiptIcon.getIconHeight());
        receiptPanel.add(receiptLabel);

        int leftMargin = 10;
        int topMargin = 10;
        int labelHeight = 30;
        int labelWidth = 600; // Adjust the width as needed

        // Header label
        JLabel headerLabel = new JLabel("Online Payment");
        headerLabel.setForeground(c4);
        headerLabel.setFont(new Font("Impact", Font.BOLD, 35));
        headerLabel.setBounds(leftMargin, topMargin, labelWidth, labelHeight);
        //receiptPanel.add(headerLabel);

        overallTotal = 0;
        int currentY = topMargin + receiptLabel.getHeight() + 10;

        Dimension buttonSize = new Dimension(500, 50);
        for (int i = 0; i + 2 < itemCart.length; i += 3) {
            String itemName = itemCart[i];
            double itemPrice = Double.parseDouble(itemCart[i + 1]);
            int itemQuantity = Integer.parseInt(itemCart[i + 2]);
            double itemTotal = itemPrice * itemQuantity;
            overallTotal += itemTotal;

            String itemDisplay = String.format("%s - Price: %.2f - Quantity: %d - Total: %.2f", itemName, itemPrice, itemQuantity, itemTotal);

            JLabel itemLabel = new JLabel(itemDisplay);
            itemLabel.setFont(new Font("Cambria", Font.PLAIN, 18));
            itemLabel.setForeground(c1);
            itemLabel.setBounds(leftMargin, currentY, labelWidth, labelHeight);

            receiptPanel.add(itemLabel);

            currentY += labelHeight + 10;
        }

        JLabel overallTotalLabel = new JLabel(String.format("Overall Total: %.2f", overallTotal));
        overallTotalLabel.setFont(new Font("Cambria", Font.BOLD, 20));
        overallTotalLabel.setForeground(c1);
        overallTotalLabel.setBounds(leftMargin, currentY, labelWidth, labelHeight);
        receiptPanel.add(overallTotalLabel);

        currentY += labelHeight + 10;

        onlinePayPanel = new JPanel();
        onlinePayPanel.setLayout(null);
        onlinePayPanel.setBounds(leftMargin, currentY, 700, 200);
        onlinePayPanel.setBackground(c5);

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);

        JLabel numberLabel = new JLabel("Number:");
        numberField = new JTextField(20);

        JLabel paymentLabel = new JLabel("Payment:");
        paymentField = new JTextField(20);

        payButton = new JButton("PAY");
        payButton.setPreferredSize(buttonSize);
        payButton.setMaximumSize(buttonSize);

        nameLabel.setForeground(c1);
        numberLabel.setForeground(c1);
        paymentLabel.setForeground(c1);

        nameLabel.setFont(new Font("Cambria", Font.BOLD, 20));
        numberLabel.setFont(new Font("Cambria", Font.BOLD, 20));
        paymentLabel.setFont(new Font("Cambria", Font.BOLD, 20));

        // Set bounds for labels and text fields
        nameLabel.setBounds(0, 20, 100, 25);
        numberLabel.setBounds(0, 60, 100, 25);
        paymentLabel.setBounds(0, 100, 100, 25);

        nameField.setBounds(100, 20, 165, 25);
        numberField.setBounds(100, 60, 165, 25);
        paymentField.setBounds(100, 100, 165, 25);

        // Set bounds for button
        payButton.setBounds(0, 140, 255, 25);


        onlinePayPanel.add(nameLabel);
        onlinePayPanel.add(nameField);
        onlinePayPanel.add(numberLabel);
        onlinePayPanel.add(numberField);
        onlinePayPanel.add(paymentLabel);
        onlinePayPanel.add(paymentField);
        onlinePayPanel.add(payButton);


        receiptPanel.add(onlinePayPanel);


        receiptPanel.revalidate();
        receiptPanel.repaint();

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orderName = nameField.getText();
                double customerPayment = 0.0;
                boolean isValidPayment = false;

                try {
                    customerPayment = Double.parseDouble(paymentField.getText());
                    isValidPayment = true;
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Invalid payment");
                }
                if (isValidPayment) {
                    if (overallTotal > customerPayment) {
                        JOptionPane.showMessageDialog(null, "Insufficient payment");
                    } else {
                        double change = customerPayment - overallTotal;
                        JOptionPane.showMessageDialog(null, "You've successfully paid " + customerPayment + " change is " + change);

                        for (int i = 0; i < itemCart.length; i += 3) {
                            for (int j = 0; j < itemNames.length; j++) {
                                if (itemCart[i].equalsIgnoreCase(itemNames[j])) {
                                    String itemToRemove = itemCart[i];
                                    int qtyToRemove = Integer.parseInt(itemCart[i + 2]);
                                    if (itemQuantity[j] <= 0) {
                                        itemPrices = delItemPrices(itemPrices, itemNames, itemToRemove);
                                        itemAvailability = delItemAvail(itemAvailability, itemNames, itemToRemove);
                                        itemQuantity = delItemQuantity(itemQuantity, itemNames, itemToRemove);
                                        itemNames = delItemNames(itemNames, itemToRemove);
                                        try {
                                            saveCatalogToCSV(itemNames, itemPrices, itemAvailability, itemQuantity, "catalog.csv");
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    } else {
                                        itemQuantity[j] = itemQuantity[j] - qtyToRemove;
                                        if (itemQuantity[j] <= 0) {
                                            itemPrices = delItemPrices(itemPrices, itemNames, itemToRemove);
                                            itemAvailability = delItemAvail(itemAvailability, itemNames, itemToRemove);
                                            itemQuantity = delItemQuantity(itemQuantity, itemNames, itemToRemove);
                                            itemNames = delItemNames(itemNames, itemToRemove);
                                            try {
                                                saveCatalogToCSV(itemNames, itemPrices, itemAvailability, itemQuantity, "catalog.csv");
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                        try {
                                            saveCatalogToCSV(itemNames, itemPrices, itemAvailability, itemQuantity, "catalog.csv");
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                        try {
                            savetoCSV(itemCart, "Orders.csv", orderName);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        itemCart = new String[0];
                        addItemsToBuyPanel();
                        switchPanels();
                    }
                }
            }
        });
    }



    private void addItemsToBuyPanel() {
        buyPanel.removeAll(); // Clear existing items
        ImageIcon buyIcon = new ImageIcon("BuyPanel.png"); // Replace with the path to your image
        JLabel buyLabel = new JLabel(buyIcon);
        buyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buyPanel.add(buyLabel);


        JLabel headerLabel = new JLabel("Buy");
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerLabel.setFont(new Font("Impact", Font.BOLD, 35)); // Set font and size
        headerLabel.setForeground(c4);
       // buyPanel.add(headerLabel); // Add header label

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel priceLabel = new JLabel("Price");
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Dimension buttonSize = new Dimension(500,50);
        selectButton = new JButton("Select");
        selectButton.setPreferredSize(buttonSize);
        selectButton.setMaximumSize(buttonSize);
        selectButton.setBackground(c1);
        selectButton.setForeground(Color.BLACK);
        selectButton.setFont(new Font("Cambria", Font.BOLD, 20));
        selectButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        cartButton = new JButton("View Cart");
        cartButton.setPreferredSize(buttonSize);
        cartButton.setMaximumSize(buttonSize);
        cartButton.setBackground(c1);
        cartButton.setForeground(Color.BLACK);
        cartButton.setFont(new Font("Cambria", Font.BOLD, 20));
        cartButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        backFrSellButton = new JButton("Back");
        backFrSellButton.setPreferredSize(buttonSize);
        backFrSellButton.setMaximumSize(buttonSize);
        backFrSellButton.setBackground(c1);
        backFrSellButton.setForeground(Color.BLACK);
        backFrSellButton.setFont(new Font("Cambria", Font.BOLD, 20));
        backFrSellButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        ButtonGroup group = new ButtonGroup(); // Create a button group to ensure only one item can be selected

        for (int i = 0; i < itemNames.length; i++) {
            // Create a radio button for each item
            JRadioButton radioButton = new JRadioButton(itemNames[i] + " - Price: " + itemPrices[i] + " - Quantity: " + itemQuantity[i]);
            radioButton.setActionCommand(itemNames[i]); // Set action command to the item name for identification
            radioButton.setBackground(c5);
            radioButton.setFont(new Font("Cambria", Font.BOLD, 23));
            radioButton.setForeground(c1); // Set text color
            radioButton.setAlignmentX(Component.LEFT_ALIGNMENT);


            buyPanel.add(radioButton); // Add the radio button to the buyPanel
            group.add(radioButton); // Add the radio button to the button group

            // Add an action listener to handle selection
            radioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // You can implement logic here when a radio button is selected
                    selected = group.getSelection().getActionCommand();
                }
            });
        }
        buyPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        buyPanel.add(selectButton);
        buyPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        buyPanel.add(cartButton);
        buyPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        buyPanel.add(backFrSellButton);

        // Revalidate and repaint the panel to show changes
        buyPanel.revalidate();
        buyPanel.repaint();

        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicked view cart");
                frame.getContentPane().removeAll();
                frame.add(viewCartPanel);

                // Clear viewCartPanel before adding new items
                viewCartPanel.removeAll();
                viewCart();

                frame.revalidate();
                frame.repaint();
            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("You selected: " + selected);

                int itemSelect = -1 ;

                for(int i = 0 ; i < itemNames.length ; i ++){
                    if(selected.equalsIgnoreCase(itemNames[i])){
                        itemSelect = i ;
                        break ;
                    }
                }

                itemCart = purchaseProcess(itemNames, itemPrices, itemAvailability, itemQuantity, itemSelect, itemCart);
            }
        });

        backFrSellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels();
            }
        });
    }

    private void addOwnedItemstoDelPanel() {
        delSellPanel.removeAll(); // Clear existing items
        delSellPanel.setLayout(new BoxLayout(delSellPanel, BoxLayout.Y_AXIS));
        ImageIcon delIcon = new ImageIcon("OwnItemsToDel.png"); // Replace with the path to your image
        JLabel delLabel = new JLabel(delIcon);
        delLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        delSellPanel.add(delLabel);

        JLabel headerLabel = new JLabel("Your Owned Items");
        headerLabel.setFont(new Font("Impact", Font.BOLD, 35));
        headerLabel.setForeground(c4);
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
       // delSellPanel.add(headerLabel);

        Dimension buttonSize = new Dimension(500, 50);

        // Configure and add the Delete button
        delSelectButton = new JButton("Delete");
        delSelectButton.setPreferredSize(buttonSize);
        delSelectButton.setMaximumSize(buttonSize);
        delSelectButton.setBackground(c1);
        delSelectButton.setForeground(Color.BLACK);
        delSelectButton.setFont(new Font("Cambria", Font.BOLD, 20));
        delSelectButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Configure and add the Back button
        backToSell = new JButton("Back");
        backToSell.setPreferredSize(buttonSize);
        backToSell.setMaximumSize(buttonSize);
        backToSell.setBackground(c1);
        backToSell.setForeground(Color.BLACK);
        backToSell.setFont(new Font("Cambria", Font.BOLD, 20));
        backToSell.setAlignmentX(Component.LEFT_ALIGNMENT);

        ButtonGroup group = new ButtonGroup(); // Create a button group to ensure only one item can be selected

        for (int i = 0; i < ownedItems.length; i++) {
            // Create a radio button for each item
            JRadioButton radioButton = new JRadioButton(ownedItems[i]);
            radioButton.setActionCommand(ownedItems[i]); // Set action command to the item name for identification
            radioButton.setBackground(c5);
            radioButton.setFont(new Font("Cambria", Font.BOLD, 23));
            radioButton.setForeground(c1); // Set text color
            radioButton.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left

            delSellPanel.add(radioButton); // Add the radio button to the delSellPanel
            group.add(radioButton); // Add the radio button to the button group

            // Add an action listener to handle selection
            radioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selected = group.getSelection().getActionCommand();
                }
            });
        }

        delSellPanel.add(Box.createRigidArea(new Dimension(20, 20))); // Add space between radio buttons and the delete button
        delSellPanel.add(delSelectButton);
        delSellPanel.add(Box.createRigidArea(new Dimension(20, 20))); // Add space between the delete button and the back button
        delSellPanel.add(backToSell);

        // Revalidate and repaint the panel to show changes
        delSellPanel.revalidate();
        delSellPanel.repaint();

        backToSell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Going to sellPanel");
                frame.getContentPane().removeAll();
                frame.add(sellPanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        delSelectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemToRemove = selected ;

                itemPrices = delItemPrices(itemPrices, itemNames, itemToRemove);
                itemAvailability = delItemAvail(itemAvailability, itemNames, itemToRemove);
                itemNames = delItemNames(itemNames, itemToRemove);
                itemQuantity = delItemQuantity(itemQuantity, itemNames, itemToRemove);
                ownedItems = delSellItem(ownedItems, itemToRemove);
                try {
                    saveOwnedItemsCSV(userFile) ;
                    saveCatalogToCSV(itemNames, itemPrices, itemAvailability, itemQuantity, "catalog.csv");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                addOwnedItemstoDelPanel();
                addItemsToBuyPanel();
            }
        });
    }


    public static String[] purchaseProcess(String[] itemNames, String[] itemPrices, boolean[] itemAvailability, int[] itemQuantity, int itemSelect, String[] itemCart)  {
        boolean willRestart = true;
        String selectedItemName = "";
        String selectedItemPrice = "";
        int selectedItemQty = 0;

        do {
            System.out.println();
            int toCartOrBuy = 0;

            try {
                if (itemAvailability[itemSelect]) {
                    selectedItemName = itemNames[itemSelect];
                    selectedItemPrice = itemPrices[itemSelect];
                    selectedItemQty = itemQuantity[itemSelect];

                    JOptionPane.showMessageDialog(null, "You selected: " + selectedItemName + " \tPrice: P " + selectedItemPrice + " \tAvailable: " + selectedItemQty);
                    int quantityToBuy = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity to buy: "));

                    if (quantityToBuy > selectedItemQty) {
                        JOptionPane.showMessageDialog(null, "Sorry, we only have " + selectedItemQty + " of " + selectedItemName + " available.");
                        return itemCart;
                    }

                    toCartOrBuy = JOptionPane.showConfirmDialog(null, "Do you want to add this item to the cart?", "Add to Cart", JOptionPane.YES_NO_OPTION);

                    if (toCartOrBuy == JOptionPane.YES_OPTION) {
                        itemCart = addToCart(selectedItemName, selectedItemPrice, quantityToBuy, itemCart);
                        //itemQuantity[itemSelect] = selectedItemQty - quantityToBuy ;
                        System.out.println(toCartOrBuy);
                        willRestart = false;
                    } else {
                        System.out.println(toCartOrBuy);
                        willRestart = false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Sorry, that item is sold out.");
                    return itemCart;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        } while (willRestart);

        return itemCart;
    }

    public static String[] addToCart(String selectedItemName, String selectedItemPrice, int quantity, String[] itemCart) {
        boolean foundDupe = false;

        for (int i = 0; i < itemCart.length; i++) {
            String item = itemCart[i];
            if (item.equalsIgnoreCase(selectedItemName)) {
                foundDupe = true;
                JOptionPane.showMessageDialog(null, "Item is already in cart");
                break;
            }
        }

        if (!foundDupe) {
            String[] tempArr = new String[itemCart.length + 3];
            System.arraycopy(itemCart, 0, tempArr, 0, itemCart.length);
            tempArr[tempArr.length - 3] = selectedItemName;
            tempArr[tempArr.length - 2] = selectedItemPrice;
            tempArr[tempArr.length - 1] = String.valueOf(quantity);

            JOptionPane.showMessageDialog(null,"Item added to cart");
            selected = "" ;

            itemCart = tempArr;

            System.out.println("Cart");
            for(int i = 0 ; i < itemCart.length ; i++){
                System.out.println(itemCart[i]);
            }
            System.out.println("");
        }

        return itemCart;
    }

    public static void loadCatalogFromCSV(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Skip the header line
            reader.readLine();

            // Read each line and parse the item information
            String line;
            int itemCount = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    // Resize arrays if needed
                    if (itemCount >= itemNames.length) {
                        resizeArrays(itemCount + 1); // Resize to one size bigger than current count
                    }
                    itemNames[itemCount] = parts[0].trim();
                    itemPrices[itemCount] = parts[1].trim();
                    itemAvailability[itemCount] = parts[2].trim().equalsIgnoreCase("Available");
                    itemQuantity[itemCount] = Integer.parseInt(parts[3].trim());
                    itemCount++;
                }
            }
            System.out.println("Catalog has been loaded from " + fileName);
        }
    }

    public static void resizeArrays(int newSize) {
        String[] newItemNames = new String[newSize];
        String[] newItemPrices = new String[newSize];
        boolean[] newItemAvailability = new boolean[newSize];
        int[] newItemQuantity = new int[newSize];

        // Copy existing items to the new arrays
        System.arraycopy(itemNames, 0, newItemNames, 0, itemNames.length);
        System.arraycopy(itemPrices, 0, newItemPrices, 0, itemPrices.length);
        System.arraycopy(itemAvailability, 0, newItemAvailability, 0, itemAvailability.length);
        System.arraycopy(itemQuantity, 0, newItemQuantity, 0, itemQuantity.length); // Added to store item quantity

        // Update the references
        itemNames = newItemNames;
        itemPrices = newItemPrices;
        itemAvailability = newItemAvailability;
        itemQuantity = newItemQuantity;
    }

    public static void loadOwnedItemsCSV(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Skip the header line
            reader.readLine();

            // Read each line and parse the item information
            String line;
            List<String> ownedItemList = new ArrayList<>(); // Use a list to store owned items temporarily
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 1) {
                    ownedItemList.add(parts[0].trim()); // Add owned item to the list
                }
            }

            // Create an iterator for the list
            Iterator<String> iterator = ownedItemList.iterator();
            while (iterator.hasNext()) {
                String ownedItem = iterator.next();
                boolean itemExists = false;
                for (String itemName : itemNames) {
                    if (ownedItem.equalsIgnoreCase(itemName)) {
                        itemExists = true;
                        break;
                    }
                }
                if (!itemExists) {
                    JOptionPane.showMessageDialog(null, ownedItem + " has been sold.");
                    iterator.remove(); // Remove the item safely using the iterator
                }
            }

            // Convert the list to an array
            ownedItems = ownedItemList.toArray(new String[0]);

            // Save the updated list of owned items
            saveOwnedItemsCSV(fileName);

            System.out.println("Owned Items have been loaded from " + fileName);
        }
    }

    public static String[] sellFunc(String[] itemNames, String sellItem) {
        int newItemIndex = itemNames.length;
        String[] newItemNames = new String[itemNames.length + 1];

        System.arraycopy(itemNames, 0, newItemNames, 0, itemNames.length);
        newItemNames[newItemIndex] = sellItem;

        itemNames = newItemNames;

        System.out.println(sellItem + " has been added for sale.");

        return newItemNames;
    }

    public static String[] upItemPrice(String[] itemPrices, String sellPrice) {
        int newItemIndex = itemPrices.length;
        String[] newItemPrices = new String[itemPrices.length + 1];

        System.arraycopy(itemPrices, 0, newItemPrices, 0, itemPrices.length);
        newItemPrices[newItemIndex] = sellPrice;

        itemPrices = newItemPrices;

        return newItemPrices;
    }

    public static boolean[] upAvailability(boolean[] itemAvailability) {
        int newItemIndex = itemAvailability.length;
        boolean[] newItemAvailability = new boolean[itemAvailability.length + 1];

        System.arraycopy(itemAvailability, 0, newItemAvailability, 0, itemAvailability.length);
        newItemAvailability[newItemIndex] = true;

        itemAvailability = newItemAvailability;

        return newItemAvailability;
    }

    public static int[] upItemQuantity (int[] itemQuantity, int sellQuantity) {
        int newItemIndex = itemQuantity.length;
        int[] newItemQuantity = new int[itemQuantity.length + 1];

        System.arraycopy(itemQuantity, 0, newItemQuantity, 0, itemQuantity.length);
        newItemQuantity[newItemIndex] = sellQuantity;

        itemQuantity = newItemQuantity;

        return newItemQuantity;
    }

    public static String[] addToOwnedItems(String[] ownedItems, String sellItem) {
        String[] newOwnedItems = new String[ownedItems.length + 1];
        System.arraycopy(ownedItems, 0, newOwnedItems, 0, ownedItems.length);
        newOwnedItems[ownedItems.length] = sellItem;
        ownedItems = newOwnedItems; 

        JOptionPane.showMessageDialog(null, "Item has been added for sale");

        return ownedItems;
    }

    public static void saveOwnedItemsCSV(String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Owned Items\n"); // Header

            for (int i = 0; i < ownedItems.length; i++) {
                writer.write(ownedItems[i] + "\n");
            }
        }
        System.out.println("Owned Items has been saved to " + fileName);
    }

    public static void saveCatalogToCSV(String[] itemNames, String[] itemPrices, boolean[] itemAvailability, int[] itemQuantity, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Name,Price,Availability,Quantity\n"); // Header

            for (int i = 0; i < itemNames.length; i++) {
                writer.write(itemNames[i] + "," + itemPrices[i] + "," + (itemAvailability[i] ? "Available" : "Sold") + "," + itemQuantity[i] + "\n");
            }
        }
        System.out.println("Catalog has been saved to " + fileName);
    }

    public static String[] delSellItem(String[] ownedItems, String itemToRemove) {
        int indexToRemove = -1 ;
        for(int i = 0 ; i < ownedItems.length ; i ++){
            if(itemToRemove.equalsIgnoreCase(ownedItems[i])){
                indexToRemove = i ;
                break ;
            }
        }
        if (indexToRemove >= 0 && indexToRemove < ownedItems.length) {
            String[] newOwned = new String[ownedItems.length - 1];
            System.arraycopy(ownedItems, 0, newOwned, 0, indexToRemove);
            System.arraycopy(ownedItems, indexToRemove + 1, newOwned, indexToRemove, newOwned.length - indexToRemove);
            ownedItems = newOwned;

            JOptionPane.showMessageDialog(null, "Item has been removed.");
            for (int i = 0; i < ownedItems.length; i++) {
                System.out.println((i + 1) + ". " + ownedItems[i]);
            }
        }

        return ownedItems;
    }

    public static String[] delItemNames(String[] itemNames, String itemToRemove) {
        for (int i = 0; i < itemNames.length; i++) {
            if (itemNames[i].equalsIgnoreCase(itemToRemove)) {
                String[] newItems = new String[itemNames.length - 1];
                System.arraycopy(itemNames, 0, newItems, 0, i);
                System.arraycopy(itemNames, i + 1, newItems, i, newItems.length - i);
                itemNames = newItems;
            }
        }

        return itemNames;
    }

    public static String[] delItemPrices(String[] itemPrices, String[] itemNames, String itemToRemove) {
        for (int i = 0; i < itemNames.length; i++) {
            if (itemNames[i].equalsIgnoreCase(itemToRemove)) {
                String[] newItems = new String[itemPrices.length - 1];
                System.arraycopy(itemPrices, 0, newItems, 0, i);
                System.arraycopy(itemPrices, i + 1, newItems, i, newItems.length - i);
                itemPrices = newItems;
            }
        }

        return itemPrices;
    }

    public static boolean[] delItemAvail(boolean[] itemAvailability, String[] itemNames, String itemToRemove) {
        for (int i = 0; i < itemNames.length; i++) {
            if (itemNames[i].equalsIgnoreCase(itemToRemove)) {
                boolean[] newItems = new boolean[itemAvailability.length - 1];
                System.arraycopy(itemAvailability, 0, newItems, 0, i);
                System.arraycopy(itemAvailability, i + 1, newItems, i, newItems.length - i);
                itemAvailability = newItems;
            }
        }

        return itemAvailability;
    }

    public static int[] delItemQuantity(int[] itemQuantity, String[] itemNames, String itemToRemove) {
        for (int i = 0; i < itemNames.length; i++) {
            if (itemNames[i].equalsIgnoreCase(itemToRemove)) {
                int[] newItems = new int[itemQuantity.length - 1];
                System.arraycopy(itemQuantity, 0, newItems, 0, i);
                System.arraycopy(itemQuantity, i + 1, newItems, i, newItems.length - i);
                itemQuantity = newItems;
            }
        }

        return itemQuantity;
    }

    public static void savetoCSV(String[] itemCart, String fileName, String name) throws IOException {
        String filePath = fileName;
        double total = 0;

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) { // Open the file in append mode
            if (new File(filePath).length() == 0) {
                writer.println("Date,Time,Customer Name,Item Name,Price,Qty");
            }

            // Get current date and time
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String currentDate = dateFormat.format(new Date());
            String currentTime = timeFormat.format(new Date());

            // Write each item to the CSV file along with the date and time
            for (int i = 0; i < itemCart.length; i += 3) {
                String itemName = itemCart[i];
                String itemPrice = itemCart[i + 1];
                String itemQuantity = itemCart[i + 2];
                total += Double.parseDouble(itemPrice) * Integer.parseInt(itemQuantity);
                writer.println(currentDate + "," + currentTime + "," + name + "," + itemName + "," + itemPrice + "," + itemQuantity);
            }

            // Add the total to the CSV file
            writer.println("Total," + total);
            // Add a separator line between different orders
            writer.println("--------,--------,--------,--------,----------");
            writer.println(); // Add a blank line for readability or separation
        }
    }


    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new BBazaarGUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}