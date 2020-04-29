package BookStore;

import java.util.ArrayList;
import java.util.Iterator;
import java.lang.*;

public class BookStore
{
	/**
	 * Array of shopping Cart
	 * @uml.property  name="shoppingCartArr"
	 * @uml.associationEnd  multiplicity="(0 -1)"
	 */
	private ShoppingCart [] shoppingCartArr;
    
	/**
	 * @uml.property  name="_items"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="th.ac.psu.te.se375.BookStore.Product"
	 */
    private ArrayList _items; 

    private ArrayList customers;
    
	/**
	 * @uml.property  name="book"
	 * @uml.associationEnd  
	 */
	private Product book;
	/**
	 * Number of shopping cart in book store
	 * @uml.property  name="NUM_CART"
	 */
	private final int NUM_CART = 100;
	
	/**
	 * Constructor BookStore
	 *
	 */
	public BookStore(){
		shoppingCartArr = new ShoppingCart[NUM_CART];	
		customers = new ArrayList();
		for(int i = 0; i < NUM_CART; i++) {
			   shoppingCartArr[i] = new ShoppingCart("", "");
		}
		
	}

	/**
	 * getting the number of shopping cart in the book store
	 * @return numOfCart (the number of shopping cart)
	 */
	public int getNumOfCart(){
		int numOfCart=0;
		for(int i = 0; i < NUM_CART; i++) {
			   if(shoppingCartArr[i].getCusId() != "") numOfCart++;
		}
		return numOfCart;
	}
	
	/**
	 * getting all of shopping cart in the book store
	 * @return array of shopping cart
	 */
	
	public ShoppingCart[] getAllShoppingCart(){
		return shoppingCartArr;
	}
	
	/**
	 * adding a shopping cart into the book store
	 * @param index (Index of shopping cart)
	 * @param cusId (Customer id)
	 * @param orderDate (Order date)
	 * @throws ArrayIndexOutOfBoundsException
	 */
	
	public void addShoppingCart(int index, String cusId, String orderDate) throws ArrayIndexOutOfBoundsException
	{
		shoppingCartArr[index] = new ShoppingCart(cusId, orderDate);
		
	}
	
	/**
	 * getting a specific shopping cart
	 * @param shopIndex (The shopping cart number)
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */

	public ShoppingCart getShoppingCart(int shopIndex)  throws ArrayIndexOutOfBoundsException
	{
		return  shoppingCartArr[shopIndex];
	}

	/**
	 * deleting a shopping cart
	 * @param index (The shopping cart number)
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public void delShoppingCart(int index)  throws ArrayIndexOutOfBoundsException
	{
		int i=0;
		for(i = index; i < shoppingCartArr.length-1; i++) {
			   shoppingCartArr[i] = shoppingCartArr[i+1];	
			  
		}
		shoppingCartArr[i].empty();
		shoppingCartArr[i]= new ShoppingCart("", "");
		
		
	}
	
	/**
	 * deleting all items in a specific shopping cart
	 * @param shopIndex (The shopping cart number)
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public void delAllItems(int shopIndex)  throws ArrayIndexOutOfBoundsException
	{
		_items = new ArrayList();
	    _items = shoppingCartArr[shopIndex].getShoppingCart();
	    	
	    _items.clear();
	}

	
	/**
	 * deleting an item from a specific shopping cart
	 * @param shopIndex (The shopping cart number)
	 * @param deleteIndex (Item number)
	 * @throws Exception
	 */
	public void delCartItem(int shopIndex, int deleteIndex) throws Exception
	{
		 _items = new ArrayList();
	     _items = shoppingCartArr[shopIndex].getShoppingCart();
	    	
	     Iterator r = _items.iterator();
	     Product pr = null;
	     int k = 1;
		 while (k<= deleteIndex) { 
			 pr = (Product)r.next();
			 k++;
		 }
		 shoppingCartArr[shopIndex].removeItem(pr);
	}
	
	/**
	 * adding an item into a specific shopping cart
	 * @param index (The shopping cart number)
	 * @param title (Book title)
	 * @param author (Book author)
	 * @param price (Book price)
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public void addCartDetail(int index, String title, String author, double price)   throws ArrayIndexOutOfBoundsException
	{
		book = new Product(title, author,  price);
		shoppingCartArr[index].addItem(book);
	}
	
	/**
	 * purchasing a specific shopping cart
	 * @param shopIndex (the Shopping number)
	 * @param paid (Money paid)
	 * @param discount (Total discount)
	 * @return change
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public double purchase(int shopIndex , double paid, int discount)  throws ArrayIndexOutOfBoundsException
	{	
		boolean canPaid = true;
		
		double balance = shoppingCartArr[shopIndex].getBalance();
		balance = balance - (balance * discount / 100);
		if(paid < 0.0) canPaid = false;
		else if(paid < balance)canPaid = false;
		
		if(canPaid) return paid - balance;
		else return paid;

	}
	
	/**
	 * getting discounted balance of a specific shopping cart
	 * @param shopIndex (The shopping number)
	 * @param discount (Total discount)
	 * @return discounted balance 
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public double getDiscountBalance(int shopIndex ,  int discount)  throws ArrayIndexOutOfBoundsException
	{	
		
		double balance = shoppingCartArr[shopIndex].getBalance();
		balance = balance - (balance * discount / 100);
		return balance;

	}

	/**
	 * getting a discount of a specific shopping cart 
	 * calculate: discount member discount + order discount
	 * @param balance (Total price)
	 * @param cusType (Customer type)
	 * @return discount 
	 */
	public static int getDiscount(double balance, int cusType, int coupon)
    {
		int discount = 0;
		if((cusType>=1)&&(cusType<=3)){			
			if((balance>=0)&&(balance<1000)) discount=3;
			else if((balance>=1000)&&(balance<2000)) discount=5;
			else if((balance>=2000)&&(balance<3000)) discount=7;
			else if((balance>=3000)&&(balance<4000)) discount=10;
			else if((balance>=4000)&&(balance<5000)) discount=15;
			else if((balance>=5000)) discount = 20;	
    	
			//1. bronze member card --> additional 3% discount
			//2. silver member card --> additional 5% discount
			//3. gold member card --> additional 10% discount
			//999. not member
			if(cusType==1)discount += 3;
			else if(cusType==2)discount += 20;
			else if(cusType==3)discount += 10;		
			
			discount += coupon;
		}else if(cusType==999)discount = coupon;
    			
		return discount;
    }
	 
	/**
	 * adding customer list to the book store
	 * @param cusId (Customer id)
	 * @param cusName (Customer name)
	 * @param cusAddr (Customer address)
	 * @param cusType (Customer type)
	 */
	public void addCustomer(String cusId, String cusName, String cusAddr, int cusType)
	{
		Customer customer = new Customer();
		customer.setCusId(cusId);
		customer.setCusName(cusName);
		customer.setCusAddr(cusAddr);
		customer.setCusType(cusType);
		
				
		customers.add(customer);
	}
	
	/**
	 * getting customer from a book store
	 * @return customers (Array of customer) 
	 */
	public ArrayList getCustomer()
	{		
		return customers;
	}

	
}
