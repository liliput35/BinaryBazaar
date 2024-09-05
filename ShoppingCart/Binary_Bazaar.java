import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Binary_Bazaar {
    public static String[] itemNames = new String[1];
    public static String[] itemPrices = new String[1];
    public static boolean[] itemAvailability = new boolean[1];
    public static int[] itemQuantity = new int[1];
    public static String[] ownedItems = new String[1];
    public static String userFile = "" ; 

    public static void main(String[] args) throws IOException {
        BufferedReader buffy = new BufferedReader(new InputStreamReader(System.in));
        String choice = "";
        String[] itemCart = new String[0];
        String sellItem = "";
        int sellPrice = 0;

        String sellChoice = "";



        loadCatalogFromCSV("catalog.csv") ;

        boolean loggedIn = false ;
        do{
            loggedIn = login() ;
        } while (!loggedIn) ;

        loadOwnedItemsCSV(userFile);

        do {
            System.out.println(ConsoleColors.CYAN_BOLD + "\tWELCOME TO BINARY BAZAAR !" + ConsoleColors.RESET);
            System.out.println(" ");
            System.out.println("-----------------------------------------------------------");
            System.out.println(ConsoleColors.YELLOW_BOLD + "\n[B]uy \n[S]ell \n[E]xit" + ConsoleColors.RESET);

            System.out.println(ConsoleColors.GREEN_BOLD + "Cart: " + ConsoleColors.RESET);
            for (int i = 0; i < itemCart.length; i++) {
                //all even indices in cart are the names. all odd are the prices
                if (i % 2 == 0) {
                    System.out.println("  " + itemCart[i]);
                }
            }

            System.out.println("-----------------------------------------------------------");
            System.out.print("Select a choice: ");
            choice = buffy.readLine().toUpperCase();

            switch (choice) {
                case "B":
                    itemCart = buyFunc(buffy, itemNames, itemPrices, itemAvailability, itemQuantity, itemCart);
                    String[] cartClone = deepCopy(itemCart);
                    String willCheckOut = "";
                    do {
                        System.out.print(ConsoleColors.YELLOW_BOLD + "Checkout?" + ConsoleColors.RESET + "(y/n): ");
                        willCheckOut = buffy.readLine().toUpperCase();

                        if (willCheckOut.equals("Y")) {
                            if (itemCart.length > 0) {
                                itemCart = checkOutCart(itemCart, itemQuantity);
                                if (itemCart.length < cartClone.length && itemCart.length > 0) {
                                    cartClone = deepCopy(itemCart);
                                }

                                if (itemCart.length == 0) {

                                    System.out.print(ConsoleColors.CYAN_BOLD + "Enter name: " + ConsoleColors.RESET);
                                    String customerName = buffy.readLine() ;
                                    //REMOVE BOUGHT STUFF FROM ITEMS
                                    String itemToRemove = ""; 
                                    for(int i = 0 ; i < cartClone.length ; i++){ 
                                        System.out.println("Cartclone item" + i); 
                                        System.out.println(cartClone[i]);
                                    }
                                    for (int i = 0; i < cartClone.length; i++) {
                                        for (int j = 0; j < itemNames.length; j++) {
                                            if (cartClone[i].equalsIgnoreCase(itemNames[j])) {
                                                itemToRemove = cartClone[i]; 
                                                int qtyToRemove = Integer.parseInt(cartClone[i + 2]) ;
                                                if(itemQuantity[j] <= 0 ){ 
                                                    itemPrices = delItemPrices(itemPrices, itemNames, itemToRemove);
                                                    itemAvailability = delItemAvail(itemAvailability, itemNames, itemToRemove);
                                                    itemQuantity = delItemQuantity(itemQuantity, itemNames, itemToRemove);
                                                    itemNames = delItemNames(itemNames, itemToRemove);
                                                    saveCatalogToCSV(itemNames, itemPrices, itemAvailability, itemQuantity, "catalog.csv");
                                                } else { 
                                                    itemQuantity[j] = itemQuantity[j] - qtyToRemove ; 
                                                    if(itemQuantity[j] <= 0 ){ 
                                                        itemPrices = delItemPrices(itemPrices, itemNames, itemToRemove);
                                                        itemAvailability = delItemAvail(itemAvailability, itemNames, itemToRemove);
                                                        itemQuantity = delItemQuantity(itemQuantity, itemNames, itemToRemove);
                                                        itemNames = delItemNames(itemNames, itemToRemove);
                                                        saveCatalogToCSV(itemNames, itemPrices, itemAvailability, itemQuantity, "catalog.csv");
                                                    }
                                                    saveCatalogToCSV(itemNames, itemPrices, itemAvailability, itemQuantity, "catalog.csv");
                                                }
                                            }
                                        }
                                    }
                                    savetoCSV(cartClone, "Orders.csv", customerName);
                                }
                            } else {
                                System.out.println("No items to checkout");
                            }
                            break;
                        } else if (willCheckOut.equals("N")) {
                            System.out.println("No checkout. Continuing shopping...");
                            break;
                        } else {
                            System.out.println(ConsoleColors.RED_BOLD + "Invalid input. Please enter 'y' or 'n'." + ConsoleColors.RESET);
                        }
                    } while (true);


                    break;

                case "S":
                    System.out.println(ConsoleColors.CYAN_BOLD + "You selected [S]ell" + ConsoleColors.RESET);
                    System.out.println("-----------------------------------------------------------");
                    System.out.println("");

                    System.out.println("Would you like to: " + ConsoleColors.YELLOW + "\n[A]dd Item \n[V]iew Items \n[D]elete Item" + ConsoleColors.RESET);
                    System.out.println("-----------------------------------------------------------");
                    System.out.print("Select a choice: ");
                    sellChoice = buffy.readLine().toUpperCase();

                    switch (sellChoice) {

                        case "A":
                            System.out.println("-----------------------------------------------------------");
                            System.out.println(ConsoleColors.YELLOW_BOLD + "You chose to add an item" + ConsoleColors.RESET);
                            System.out.print("Enter the name of the item: ");
                            sellItem = buffy.readLine();
                            boolean isValidPrice = false;
                            do {
                                try {
                                    System.out.print("Enter the price of the item: ");
                                    sellPrice = Integer.parseInt(buffy.readLine());
                                    isValidPrice = true;

                                } catch (NumberFormatException ew) {
                                    System.out.println(ConsoleColors.RED_BOLD + "Invalid Input, try again" + ConsoleColors.RESET);
                                    isValidPrice = false;
                                }
                            } while (!isValidPrice);

                            System.out.print("Enter the quantity of the item: ");
                            int sellQuantity = Integer.parseInt(buffy.readLine());

                            if (isValidPrice) {
                                String sellPriceStr = "" + sellPrice;
                                itemNames = sellFunc(itemNames, sellItem);
                                itemPrices = upItemPrice(itemPrices, sellPriceStr);
                                itemAvailability = upAvailability(itemAvailability);
                                itemQuantity = upItemQuantity(itemQuantity, sellQuantity);

                                ownedItems = addToOwnedItems(ownedItems, sellItem);
                                saveOwnedItemsCSV(userFile) ;
                                saveCatalogToCSV(itemNames, itemPrices, itemAvailability, itemQuantity, "catalog.csv");
                            }
                            break;

                        case "V":
                            System.out.println("-----------------------------------------------------------");
                            System.out.println(ConsoleColors.YELLOW_BOLD + "You chose to view your items" + ConsoleColors.RESET);
                            printOwnedItems(ownedItems);

                            break;

                        case "D":
                            System.out.println("-----------------------------------------------------------");
                            System.out.println(ConsoleColors.YELLOW_BOLD + "You chose to delete an item" + ConsoleColors.RESET);
                            printOwnedItems(ownedItems);

                            boolean isValidInput = false;
                            if (ownedItems.length > 0) {
                                do {
                                    try {
                                        System.out.print("Enter the number of the item to delete: ");
                                        int userInput = Integer.parseInt(buffy.readLine());


                                        if (userInput < 1 || userInput > ownedItems.length) {
                                            System.out.println(ConsoleColors.RED_BOLD + "Invalid item number. Please enter a valid number." + ConsoleColors.RESET);
                                        } else {
                                            String itemToRemove = ownedItems[userInput - 1];


                                            itemPrices = delItemPrices(itemPrices, itemNames, itemToRemove);
                                            itemAvailability = delItemAvail(itemAvailability, itemNames, itemToRemove);
                                            itemNames = delItemNames(itemNames, itemToRemove);
                                            itemQuantity = delItemQuantity(itemQuantity, itemNames, itemToRemove);
                                            ownedItems = delSellItem(ownedItems, userInput);
                                            saveOwnedItemsCSV(userFile) ;
                                            saveCatalogToCSV(itemNames, itemPrices, itemAvailability, itemQuantity, "catalog.csv");

                                            isValidInput = true;
                                        }
                                    } catch (NumberFormatException | IOException e) {
                                        System.out.println(ConsoleColors.RED_BOLD + "Invalid input. Please enter a valid number." + ConsoleColors.RESET);
                                    }
                                } while (!isValidInput);
                            } else {
                                System.out.println("You have no owned items for sale");
                            }
                            break;

                        default:
                            System.out.println(ConsoleColors.RED_BOLD + "Invalid Input, try again" + ConsoleColors.RESET);

                    }
                    break;

                case "E":
                    System.out.println(ConsoleColors.RED_BOLD + "Exiting the system. Farewell!" + ConsoleColors.RESET);
                    System.exit(0);
                    break;

                default:
                    System.out.println(ConsoleColors.RED_BOLD + "Invalid Input, try again" + ConsoleColors.RESET);

            }
        } while (!choice.equals("E"));
    }



    public static boolean login () throws IOException {
        BufferedReader buffy = new BufferedReader(new InputStreamReader(System.in));
        boolean isLoggedIn = false;
        boolean isValidInput = false;
        String choice = "" ;

        do {
            System.out.println("-----------------------------------------------------------");
            System.out.println(ConsoleColors.CYAN_BOLD + "     █████║  ████║ ██║  ██║  ████║  ██████║ ██║ ██║        ");
            System.out.println("    ██║  █║   ██║  ███║ ██║ ██║ ██║ ██║ ██║ ██║ ██║        ");
            System.out.println("    ███████║  ██║  ██ █║██║ ██████║ █████║   ████║         ");
            System.out.println("    ██║   █║  ██║  ██  ███║ ██║ ██║ ██║███║   ██║          ");
            System.out.println("     █████║  ████║ ██   ██║ ██║ ██║ ██║  ██║  ██║          " + ConsoleColors.RESET);
            System.out.println("                                                           ");
            System.out.println(ConsoleColors.GREEN + "    █████║   ████║  ██████║  ████║   ████║  ██████║  ██║   ");
            System.out.println("   ██║  █║  ██║ ██║     ██║ ██║ ██║ ██║ ██║ ██║ ██║  ██║   ");
            System.out.println("   ███████║ ██████║   ██║   ██████║ ██████║ █████║   ██║   ");
            System.out.println("   ██║   █║ ██║ ██║ ██║     ██║ ██║ ██║ ██║ ██║███║        ");
            System.out.println("    █████║  ██║ ██║ ██████║ ██║ ██║ ██║ ██║ ██║  ██║ ██║   " + ConsoleColors.RESET);
            System.out.println("                                                           ");
            System.out.println(ConsoleColors.YELLOW + "[C]reate Account \n[L]ogin" + ConsoleColors.RESET);
            System.out.print("Enter choice: ");
            choice = buffy.readLine().toUpperCase();
            if(choice.equals("L") || choice.equals("C")){
                isValidInput = true ;
            } else {
                System.out.println(ConsoleColors.RED_BOLD + "Invalid Input, try again" + ConsoleColors.RESET);
            }

        } while (!isValidInput);

        switch (choice) {
            case "C":
                System.out.println("-----------------------------------------------------------");
                System.out.println("Creating account...");
                System.out.println(" ");
                System.out.print("Enter username: ");
                String usern = buffy.readLine();
                System.out.print("Enter password: ");
                String pass = buffy.readLine();

                createAccount(usern, pass);

                break;

            case "L":
                System.out.println("-----------------------------------------------------------");
                System.out.println("Logging in...");
                System.out.println(" ");
                do {
                    System.out.print("Enter username: ");
                    usern = buffy.readLine();
                    System.out.print("Enter password: ");
                    pass = buffy.readLine();

                    isLoggedIn = loginUser(usern, pass);
                    if(!isLoggedIn){
                        System.out.println(ConsoleColors.RED_BOLD + "Account not found" + ConsoleColors.RESET);
                        System.out.println();
                        break;
                    }
                } while(!isLoggedIn) ;
                break;

            default:
                System.out.println(ConsoleColors.RED_BOLD + "Invalid Input, try again" + ConsoleColors.RESET);

                break;
        }
        return isLoggedIn;
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



    public static String[] buyFunc(BufferedReader buffy, String[] itemNames, String[] itemPrices, boolean[] itemAvailability, int[] itemQuantity, String[] itemCart) throws IOException {
        String itemSelect = "";
        boolean isValidInput = false;

        System.out.println("You selected [B]uy");
        System.out.println("-----------------------------------------------------------");
        System.out.println("");

        System.out.println("Available Items: ");
        for (int i = 0; i < itemNames.length; i++) {
            String isAvail = itemAvailability[i] ? "Available" : "Sold";
            System.out.println("  " + (i + 1) + ". " + itemNames[i] + "\t\tP " + itemPrices[i] + "\t\t" + isAvail + "\t\t" + itemQuantity[i]);
        }

        do {
            System.out.println("-----------------------------------------------------------");
            System.out.print("What do you want to buy: ");
            itemSelect = buffy.readLine().trim();

            int itemToInt = -1;

            for (int i = 0; i < itemNames.length; i++) {
                String currentIndex = (i + 1) + "";
                if (itemSelect.equalsIgnoreCase(currentIndex) || itemSelect.equalsIgnoreCase(itemNames[i])) {
                    itemToInt = i;
                    isValidInput = true;
                    break;
                }
            }

            if (!isValidInput) {
                System.out.println("Invalid selection. Please select a valid item.");
            }

            if (isValidInput) {
                itemCart = purchaseProcess(buffy, itemNames, itemPrices, itemAvailability, itemQuantity, itemToInt, itemCart);
            }

        } while (!isValidInput);

        return itemCart;
    }

    public static String[] purchaseProcess(BufferedReader buffy, String[] itemNames, String[] itemPrices, boolean[] itemAvailability, int[] itemQuantity, int itemSelect, String[] itemCart) throws IOException {
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

                    System.out.println("-----------------------------------------------------------");
                    System.out.println("You selected: " + selectedItemName + "\tP " + selectedItemPrice + "\tAvailable: " + selectedItemQty);
                    System.out.print("Enter quantity to buy: ");
                    int quantityToBuy = Integer.parseInt(buffy.readLine()); 

                    if (quantityToBuy > selectedItemQty) {
                        System.out.println("Sorry, we only have " + selectedItemQty + " of " + selectedItemName + " available.");
                        return itemCart;
                    }

                    System.out.print("Add to cart[1] or Back[2] : ");
                    toCartOrBuy = Integer.parseInt(buffy.readLine());

                    if (toCartOrBuy == 1) {
                        itemCart = addToCart(selectedItemName, selectedItemPrice, quantityToBuy, itemCart);
                        //itemQuantity[itemSelect] = selectedItemQty - quantityToBuy ;
                        willRestart = false;
                    } else if (toCartOrBuy == 2) {
                        System.out.println("");
                        willRestart = false;
                    } else {
                        System.out.println("Invalid input");
                    }
                } else {
                    System.out.println("Sorry, that item is sold out.");
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
                System.out.println("Item is already in cart");
                break;
            }
        }

        if (!foundDupe) {
            String[] tempArr = new String[itemCart.length + 3];
            System.arraycopy(itemCart, 0, tempArr, 0, itemCart.length);
            tempArr[tempArr.length - 3] = selectedItemName;
            tempArr[tempArr.length - 2] = selectedItemPrice;
            tempArr[tempArr.length - 1] = String.valueOf(quantity);

            System.out.println("-----------------------------------------------------------");
            System.out.println("Item added to cart");

            itemCart = tempArr;
        }

        return itemCart;
    }

    public static String[] checkOutCart(String[] itemCart, int[] itemQuantity) throws IOException {
        BufferedReader buffy = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("-------------------------------------");
        System.out.println("Check out cart");

        System.out.println("-------------------------------------");
        System.out.println("CART");
        for (int i = 0; i < itemCart.length; i += 3) {
            System.out.println(itemCart[i] + "\t\tP " + itemCart[i + 1] + "\tQty: " + itemCart[i + 2]);
        }
        int total = 0;
        int[] cartPrices = new int[itemCart.length / 3];
        int[] cartQuantities = new int[itemCart.length / 3];
        String[] cartNames = new String[itemCart.length / 3];

        for (int i = 1, j = 0; i < itemCart.length; i += 3, j++) {
            cartPrices[j] = Integer.parseInt(itemCart[i]);
            cartQuantities[j] = Integer.parseInt(itemCart[i + 1]);
            cartNames[j] = itemCart[i - 1];
        }

        System.out.println("-------------------------------------");
        System.out.print("TOTAL: ");
        for (int i = 0; i < cartPrices.length; i++) {
            total += cartPrices[i] * cartQuantities[i];
        }
        System.out.println(total);
        boolean isValidInput = false;
        int orderOrDel = 0;

        do {
            try {
                System.out.println("-------------------------------------");
                System.out.println("1. Place order \n2. Delete item from cart");
                System.out.print("Enter choice: ");
                orderOrDel = Integer.parseInt(buffy.readLine());

                if (orderOrDel < 1 || orderOrDel > 2) {
                    System.out.println("Invalid input");
                    System.out.println("");
                } else {
                    isValidInput = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
                System.out.println("");
            }
        } while (!isValidInput);

        boolean willDel = false;

        if (orderOrDel == 1) {
            boolean isValInput = false;
            String willOrder = "";

            do {
                System.out.print("Place order? (y/n): ");
                willOrder = buffy.readLine().toUpperCase();

                if (!willOrder.equals("Y") && !willOrder.equals("N")) {
                    System.out.println("Invalid input");
                    System.out.println("");
                } else {
                    isValInput = true;
                }

            } while (!isValInput);

            if (willOrder.equals("Y")) {
                System.out.println("Order placed. Total is P" + total);
                itemCart = new String[0];
                total = Cashiering(total);
            } else if (willOrder.equals("N")) {
                System.out.println("Continuing shopping...\n");
            }
        } else if (orderOrDel == 2) {
            willDel = true;
        }

        if (willDel) {
            boolean isValInput = false;
            int indexToRemove = -1;
            do {
                System.out.println("-------------------------------------");
                System.out.println("CART");
                for (int i = 0; i < cartNames.length; i++) {
                    System.out.println((i + 1) + ". " + cartNames[i] + "\t\t\t" + cartPrices[i] + "\tQty: " + cartQuantities[i]);
                }
                try {
                    System.out.print("Enter index to remove: ");
                    indexToRemove = Integer.parseInt(buffy.readLine()) - 1;

                    if (indexToRemove >= cartNames.length || indexToRemove < 0) {
                        System.out.println("Invalid input\n");
                    } else {
                        isValInput = true;
                    }
                } catch (NumberFormatException ew) {
                    System.out.println("Invalid input\n");
                }
            } while (!isValInput);

            int cartIndexToRemove = indexToRemove * 3;
            String[] tempArr = new String[itemCart.length - 3];
            System.arraycopy(itemCart, 0, tempArr, 0, cartIndexToRemove);
            int copyLength = itemCart.length - cartIndexToRemove - 3;
            if (copyLength > 0) {
                System.arraycopy(itemCart, cartIndexToRemove + 3, tempArr, cartIndexToRemove, copyLength);
            }
            itemCart = tempArr;

            System.out.println("-------------------------------------");
            System.out.println("UPDATED CART");
            for (int i = 0; i < itemCart.length; i += 3) {
                System.out.println(itemCart[i] + "\t\tP " + itemCart[i + 1] + "\tQty: " + itemCart[i + 2]);
            }

        }

        return itemCart;
    }


    public static int Cashiering (int total) throws IOException {
        BufferedReader buffy = new BufferedReader(new InputStreamReader(System.in));
        int payment;
        int change;
        String mode;

        System.out.println("-------------------------------------");
        System.out.println("Payment Process");
        System.out.println("[1]Online payment \n[2]Cash on Delivery");
        System.out.print("Choose mode of payment: ");
        mode = buffy.readLine();

        switch (mode) {
            case "1":
                System.out.print(ConsoleColors.GREEN_BOLD + "Enter payment: " + ConsoleColors.RESET);
                payment = Integer.parseInt(buffy.readLine());
                System.out.println(ConsoleColors.GREEN_BOLD + "You paid: " + ConsoleColors.RESET + payment);

                change = payment - total;

                System.out.println("-------------------------------------");
                System.out.println(ConsoleColors.GREEN_BOLD + "Your change: " + ConsoleColors.RED_BOLD + change + ConsoleColors.RESET);
                break;

            case "2":
                System.out.println("Please pay " + total + " upon delivery. Thank you");
                break;
        }


        return total;
    }


    public static String[] sellFunc(String[] itemNames, String sellItem) throws IOException {
        int newItemIndex = itemNames.length;
        String[] newItemNames = new String[itemNames.length + 1];

        System.arraycopy(itemNames, 0, newItemNames, 0, itemNames.length);
        newItemNames[newItemIndex] = sellItem;

        itemNames = newItemNames;

        System.out.println(ConsoleColors.GREEN + sellItem + " has been added for sale." + ConsoleColors.RESET);

        return newItemNames;
    }

    public static String[] upItemPrice(String[] itemPrices, String sellPrice) throws IOException {
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

        return ownedItems;
    }

    public static void printOwnedItems(String[] ownedItems) {
        System.out.println(ConsoleColors.CYAN + "Your owned items: " + ConsoleColors.RESET);
        for (int i = 0; i < ownedItems.length; i++) {
            System.out.println((i + 1) + ". " + ownedItems[i]);
        }
    }

    public static String[] delSellItem(String[] ownedItems, int userInput) throws IOException {
        int indexToRemove = userInput - 1;
        if (indexToRemove >= 0 && indexToRemove < ownedItems.length) {
            String[] newOwned = new String[ownedItems.length - 1];
            System.arraycopy(ownedItems, 0, newOwned, 0, indexToRemove);
            System.arraycopy(ownedItems, indexToRemove + 1, newOwned, indexToRemove, newOwned.length - indexToRemove);
            ownedItems = newOwned;

            System.out.println(ConsoleColors.GREEN + "Item has been removed." + ConsoleColors.RESET);
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

    public static String[] deepCopy(String[] original) {
        String[] copy = new String[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
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
            writer.println("Total,,,," + total);
            // Add a separator line between different orders
            writer.println("--------,--------,--------,--------,----------");
            writer.println(); // Add a blank line for readability or separation
        }
    }

    // FOR DESIGN PURPOSES AKA MIA'S RAT CORNER
    public static class ConsoleColors {
        // Reset
        public static final String RESET = "\033[0m";  // Text Reset

        // Regular Colors
        public static final String GREEN = "\033[0;32m";
        public static final String YELLOW = "\033[0;33m";
        public static final String CYAN = "\033[0;36m";

        // Bold
        public static final String RED_BOLD = "\033[1;31m";
        public static final String GREEN_BOLD = "\033[1;32m";
        public static final String YELLOW_BOLD = "\033[1;33m";
        public static final String CYAN_BOLD = "\033[1;36m";
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

            // Convert the list to an array
            ownedItems = ownedItemList.toArray(new String[0]);

            System.out.println("Owned Items has been loaded from " + fileName);
        }
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
}