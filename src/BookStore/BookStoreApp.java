package BookStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.*;



class BookStoreApp
{
   private static BookStore bookStore;
   private static int numOfCart;
   private static ShoppingCart  shoppingCart;
   private static Customer customer;
   private static ArrayList _items;
   private static ArrayList customers;
   private static String[][] cusList = 
   	{ 	{"001", "Boonchock", "Phuket", "1"},
   		{"002", "Precha", "Bangkok", "2"},
   		{"003", "Pranee", "Bangkok", "2"},
   		{"004", "Tawit", "Songkhla", "1"},
   		{"005", "Wipada", "Phuket", "1"},   		
   	};
   private static final String GEN_CUS_ID = "999";
   

   /** 
    * displaying main menu.   
    */ 
   public static void mainMenu() {
    	   
    	   System.out.println("Welcome to the Book Store!\n");
   	   		System.out.println("-----------------------------------------------------------");
	       System.out.println("1. Adding an item into shopping cart");
	       System.out.println("2. Viewing a shopping cart");
	       System.out.println("3. Deleting an item in a shopping cart ");
	       System.out.println("4. Deleting a shopping cart");
	       System.out.println("5. Purchasing a shopping cart");
	       System.out.println("0. Exit\n");
		   	System.out.println("-----------------------------------------------------------");
	        
	        //Get user input
	        int userInput = stringToInt(inputOutput("Please press the number : "));
	       System.out.print("\n");
   			if((userInput >= 0) &&(userInput <=6)){
	  	        if(userInput == 1) addShoppingCart();
				if(bookStore.getNumOfCart()>0){						
						if(userInput == 2) viewShoppingCart();
				        else if(userInput == 3) deleteItem();
						else if(userInput == 4) deleteCart();
				        else if(userInput == 5) purchase();
				        else if(userInput == 0) System.exit(0);
				}else{
			  	        if(userInput > 1){
					   		System.out.println("-----------------------------------------------------------");
							System.out.println("Error: No shopping cart data !! ");
		   					System.out.println("-----------------------------------------------------------");
					    	String ch = inputOutput("[Any key to continue]");		
							System.out.println("\n");
							mainMenu();
					}
				}
			}else{
		   		System.out.println("-----------------------------------------------------------");
				System.out.println("!! Error choice !! ");
				System.out.println("-----------------------------------------------------------");
		    	String ch = inputOutput("[Any key to continue]");		
				System.out.println("\n");
				mainMenu();
			}
	    }
   
   /** 
    * Purchasing a shopping cart 
    */ 
    public static void purchase()
    {
    	double amountToPay=0.0, change=0.0;
    	int bonusPercent;
    	int discount=0;
		int shopIndex;
		double balance;
    	System.out.println("Menu 5: Purchase a shopping cart\n");
		shopIndex = getCartDetail();
		if((shopIndex >= 0) && (shopIndex < bookStore.getNumOfCart())){
	    	System.out.println("\nStep 3--> Purchase a shopping cart!");
	    	bonusPercent = stringToInt(inputOutput("Please enter the bounus coupon percent [zero for no coupon]:"));
			
			shoppingCart = bookStore.getShoppingCart(shopIndex);
			customer = SearchCusData(shoppingCart.getCusId());
	    	balance = shoppingCart.getBalance();

	    	try{
	    		discount = bookStore.getDiscount(balance, customer.getCusType(), bonusPercent);
	    	}catch(NullPointerException e){
	    		discount = bookStore.getDiscount(balance, stringToInt(GEN_CUS_ID), bonusPercent);
	    	}
	    

	    	
	    	System.out.println("\t\t\t\t\tDiscount" + discount + "%:" + bookStore.getDiscountBalance(shopIndex, discount) + "\n");

			do{
    			amountToPay = stringToDouble(inputOutput("Please enter the amount you wish to pay:"));
    			try{
    				change = bookStore.purchase(shopIndex, amountToPay, discount);
    				
    			}catch(Exception e){
    				System.out.println("Error: Incorrect shopping cart number --> " + (shopIndex+1) + "\n");
    			}
    			if(change <= 0) System.out.println("Error: Your amount must not be negative or zero value\n");         	
        		else if(change == amountToPay) System.out.println("Error: Your amount must greater than the total\n");

			}while(change == amountToPay);
			
	    	System.out.println("\t\t\t\t\tReceive:" + amountToPay);
			System.out.println("\t\t\t\t\tChange:" + change);
    	}
    	String ch = inputOutput("[Any key to continue]");		
		System.out.println("\n");
    	mainMenu();
    }
    
  
	/** 
	 *  Deleting a shopping cart 
	 */
    public static void deleteCart()
    {
		boolean canDel = true;
       System.out.println("Menu 4: Delete a shopping cart\n");

    	int shopIndex = custMenu("Please select the shopping cart number to delete:");
		if((shopIndex >= 0) && (shopIndex < bookStore.getNumOfCart())){
	    	System.out.println(" ");
		   	System.out.println("Step 2--> Delete the shopping cart!");
	   		System.out.println("-----------------------------------------------------------");
			try{
				bookStore.delShoppingCart(shopIndex);
			}catch(Exception e){
				canDel = false;
			}
			if(canDel)   	System.out.println("!! Delete Success!! ");
			else 			System.out.println("Error: Incorrect shopping cart number --> " + (shopIndex+1) + "\n");
			System.out.println("-----------------------------------------------------------");
    	}else{
				System.out.println("Error: Incorrect customer number --> " + (shopIndex+1) + "\n");
		}
		String ch = inputOutput("[Any key to continue]");
    	mainMenu();
    }
    
  
    /** 
     * Deleting an item from a shopping cart
     */
    public static void deleteItem() 
    {
	    System.out.println("Menu 3: Delete an item in a shopping cart \n");

    	int shopIndex = custMenu("Please select the shopping cart number to  display the book:");
		if((shopIndex >= 0) && (shopIndex < bookStore.getNumOfCart())){
				System.out.println(" ");
			   	System.out.println("Step 2 --> Choose the book!");
			   	System.out.println("-----------------------------------------------------------");
		    	disCartItem(shopIndex);
				 int deleteIndex = stringToInt(inputOutput("Please select the book number to delete:"));
		    	System.out.println(" ");
				 System.out.println("Step 3 --> Delete the book item!");
				 System.out.println("-----------------------------------------------------------");
				 try{
						bookStore.delCartItem(shopIndex, deleteIndex);
						 System.out.println("!! Delete Success !!");
				}catch(Exception E){
						 System.out.println("!! Error: Product not found !! ");
				 }
			   	System.out.println("-----------------------------------------------------------");
		}else{
				System.out.println("Error: Incorrect customer number --> " + (shopIndex+1) + "\n");
		}
		String ch = inputOutput("[Any key to continue]");
		System.out.print("\n");
        mainMenu();
    }
    
    /** 
     * Viewing a shopping cart. 
     */
    public static void disCartItem(int shopIndex)
    {
		try{
				shoppingCart = bookStore.getShoppingCart(shopIndex);
			    _items = new ArrayList();
				_items = shoppingCart.getShoppingCart();
		
		    	Iterator i = _items.iterator(); 
		    	System.out.println("");
		    	System.out.println("Title\t\t\tAuthor\t\tPrice");
		    	System.out.println("-------------------------------------------------------------------");
		    	int j = 1;
				while (i.hasNext()) { 
				Product p = (Product)i.next();
				 System.out.println(j + " " + p.getTitle() + "\t\t" + p.getAuthor()+ "\t\t" + p.getPrice());
				 j++;
			 }
			System.out.println("-------------------------------------------------------------------");
		}catch(Exception e){
				System.out.println("Error: Incorrect shopping cart number --> " + (shopIndex+1) + "\n");
		}
        
    	
    }
    
    /** 
     * Displaying a customer menu. 
     * @return shopping cart number
     */
    public static int custMenu(String value)
    {
    	//shoppingCartArr = bookStore.getAllShoppingCart();
		int shopIndex = 0;
        		
	   	System.out.println("Step 1 --> Choose the customer!");
	   	System.out.println("-----------------------------------------------------------");
	        for(int i = 0; i < bookStore.getNumOfCart(); i++) {
				try{
					shoppingCart = bookStore.getShoppingCart(i);
				}catch(Exception e){
					System.out.println("Error: Incorrect shopping cart number --> " + i+1 );
				}
				if(shoppingCart.getCusId().equals(GEN_CUS_ID)) System.out.println((i+1) + ". walk in " + shoppingCart.getOrderDate());
				else 
				{
					customer = SearchCusData(shoppingCart.getCusId());
					System.out.println((i+1) + ". " + customer.getCusName() + " " + shoppingCart.getOrderDate());
				}
		    }
		   	System.out.println("-----------------------------------------------------------");

		    shopIndex = stringToInt(inputOutput(value));


        return (--shopIndex);
    }
    
    /**
     * Getting a shopping cart details. 
     * @return shopping cart number
     */
	public static  int  getCartDetail()
    {
		int shopIndex = 0;
		String cusId, cusName, cusAddr, cusTypeName;
		int cusType;
    	shopIndex = custMenu("Please select customer number to  display the book:");
		if((shopIndex >= 0) && (shopIndex < bookStore.getNumOfCart())){
			try{
				shoppingCart = bookStore.getShoppingCart(shopIndex);
		    	System.out.println(" ");
			   	System.out.println("Step 2 --> Display the shopping cart details!");
		   		System.out.println("-----------------------------------------------------------");      
	    		System.out.println("");
	    		customer = SearchCusData(shoppingCart.getCusId());
	     	    		
	    		if(customer!=null){
	    			cusId = customer.getCusId();
	    			cusName = customer.getCusName();
	    			cusAddr = customer.getCusAddr();
	    			cusType = customer.getCusType();
	    			cusTypeName = customer.getCusTypeName(cusType);
	    			
	    		}else{
	    			cusId = GEN_CUS_ID;
	    			cusName = "Walk in";
	    			cusAddr = "-";
	    			cusType = 0;
	    			cusTypeName = "Walk in";
	    		}
	    		
	    		System.out.println("Customer's Id : " + cusId);
	    		System.out.println("Customer's Name : " + cusName);
    			System.out.println("Customer's Address : " + cusAddr);
    			System.out.println("Customer's Type: " + cusTypeName);
    			System.out.println("Date : " + shoppingCart.getOrderDate());
    		
	    		
    	
				disCartItem(shopIndex);
    	
				System.out.println("\t\t\t\t\tTotal:" + shoppingCart.getBalance());
			}catch(Exception e){
				 System.out.println("Error: Incorrect customer number --> " + (shopIndex+1) + "\n");
			}
		}else{
				 System.out.println("Error: Incorrect customer number --> " + (shopIndex+1) + "\n");
		}
		return shopIndex;


    }
    
	 /** 
	  *  displaying a shopping cart details. 
	  */
    public static void viewShoppingCart()
    {	       
    	System.out.println("Menu 2: Display a shopping cart\n");
		int shopIndex = getCartDetail();  	
		String ch = inputOutput("[Any key to continue]");
		System.out.println("\n");
    	mainMenu();
    }
    
   
    /** 
     * adding an item to a shopping cart. 
     */
    public static void addShoppingCartDetail()
    {
    	boolean conFlg = true;
    	System.out.println(" ");
	   	System.out.println("Step 2 --> Add the item into the shopping cart!");
	   	System.out.println("-----------------------------------------------------------"); 	
    	do{    		
    		String bkName = inputOutput("Please enter your book name:");
    		String bkAuthor = inputOutput("Please enter your book author:");   		
    		double bkPrice = stringToDouble(inputOutput("Please enter your book price:"));
        		    		
			try{
				bookStore.addCartDetail(numOfCart, bkName, bkAuthor, bkPrice);
			}catch(Exception e){
				System.out.println("Error : Can't add item into your shopping cart");
			}
    		
    		
    		String conChar = inputOutput("Do you want to add any book [Y:Yes, N: No]:");
    		if(!conChar.toUpperCase().equals("Y")){conFlg = false;}
    		System.out.println();
    	}while(conFlg == true);
    	numOfCart += 1;

    	mainMenu();
    	
    }
    
    /** 
     * creating a shopping cart. 
     */
    public static void addShoppingCart()
    {
    	String cusId, cusName, cusAddr, cusTypeName;
    	int cusType;
    	
	       System.out.println("Menu 1: Add an item into shopping cart\n");

    	   	System.out.println("Step 1 --> add the shopping cart information");
    	   	System.out.println("-----------------------------------------------------------");
    		cusId = inputOutput("Please enter customer's id:");  		
    		customer = SearchCusData(cusId);
    	    
    		Date now = new Date();
    		String orderDate =DateFormat.getDateInstance(DateFormat.SHORT).format(now) + "  " + DateFormat.getTimeInstance(DateFormat.SHORT).format(now);

    		
    		if(customer!=null){
    			cusId = customer.getCusId();
    			cusName = customer.getCusName();
    			cusAddr = customer.getCusAddr();
    			cusType = customer.getCusType();
    			cusTypeName = customer.getCusTypeName(cusType);
    			
    		}else{
    			cusId = GEN_CUS_ID;
    			cusName = "Walk in";
    			cusAddr = "-";
    			cusType = 0;
    			cusTypeName = "Walk in";
    		}
    		System.out.println("Customer Information Result");
    		System.out.println("Customer's Id : " + cusId);
    		System.out.println("Customer's Name : " + cusName);
			System.out.println("Customer's Address : " + cusAddr);
			System.out.println("Customer's Type: " + cusTypeName);
			
			
			System.out.println("Date : " + orderDate);

    		
    		try{
	    		 bookStore.addShoppingCart(numOfCart, cusId, orderDate);
    		}catch(Exception e){
    			System.out.println("Error: Full shopping cart, can't add customer\n");    			
    		}
    		
    		addShoppingCartDetail();
    		
    	
    }
    
    /** 
     * Reading an input from user. 
     * @param the prompt message
     */
    public static String inputOutput(String message) {
        System.out.println(message);
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    String returnString = "";
	    try {
	        returnString = br.readLine();
	    }
	    catch (IOException e){
	        System.out.println("Error reading in value");
	        mainMenu();
	    }
	    return returnString;
    }
    
    /** 
     * Corverting string to double. 
     *  @param the string value
     */
    private static double stringToDouble(String value) {
        double returnDouble = -1;
        
        try {
            returnDouble = Double.parseDouble(value);
        }
        catch (NumberFormatException e) {
            System.out.println("Please input an Double\n");
        }
        return returnDouble;
    }
    
    /** 
     * Corverting string to integer. 
     * @param the string value
     */
    private static int stringToInt(String value) {
        int returnInt = -1;
        try {
            returnInt = Integer.parseInt(value);
           
        }
        catch (NumberFormatException e) {
            System.out.println("Please input an integer\n");
        }
        return returnInt;
    }
   
    /** 
     * Creating a customer list. 
     */
   public static void generateCusData()
   {
	   for(int i=0; i<5; i++) bookStore.addCustomer(cusList[i][0], cusList[i][1], cusList[i][2], stringToInt(cusList[i][3]));
   }
   
   /** 
    * Searching customer data.
    * @param customer Id
    * @return Customer instance (c)
    */
   
   public static Customer SearchCusData(String cusId)
   {
	   boolean found = false;
	   Customer c = null;
	   
	   
	    customers = new ArrayList();
		customers = bookStore.getCustomer();
		
		Iterator i = customers.iterator();
		
		while ((i.hasNext())&&(found==false)) { 
			c = (Customer)i.next();
			if(c.getCusId().equals(cusId)) found = true;
			
		 }
		if(!found)  c= null;		
		return c;
   }
   
    
    
    public static void main(String[] args) {
    	
	    numOfCart = 0;	    
	    bookStore = new BookStore();
	    customer = new Customer();
	    generateCusData();
	    mainMenu();
	}
      

}

