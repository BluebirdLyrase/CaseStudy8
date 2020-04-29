package BookStore;

import java.util.*;

public class ShoppingCart {
	
	/** Array of items */ 
	private ArrayList _items;
	
	/** Customer id */
	private String cusId;
	
	/** Order date*/
	private String orderDate;
	 
	 /** 
	  *  Constructs a ShoppingCart instance. 
	  */ 
	 public ShoppingCart(String cusId, String orderDate) 
	 {   
		 this.cusId= cusId;
		 this.orderDate = orderDate;
		 _items = new ArrayList(); 
	 
	 } 
	 
 
	 /**
	  * getting customer id.
	  * @return cusId (Customer id).
	  */
	public String getCusId(){
		 return cusId;
	 }
	
	
	/**
	 * getting order date.
	 * @return orderDate (Order date).
	 */
	public String getOrderDate(){
		 return orderDate;
	 }
	 
	 
	 /** 
	  * getting the shopoing cart. 
	  * @return ShoppingCart (Shopping cart). 
	  */
	 public ArrayList getShoppingCart(){
		 return _items;
	 }
	 
	 /** 
	  * getting the balance. 
	  * @return Balance. 
	  */ 
	 public double getBalance() { 
		 Iterator i = _items.iterator(); 
		 double balance = 0.00; 
		 while (i.hasNext()) { 
			 Product p = (Product)i.next(); 
		     balance = balance + p.getPrice(); 
		 } 
		 return balance; 
	} 
	 
	 /** Adding the specified product. 
	  * @param p Product. 
	  */ 
	 public void addItem(Product p) 
	 { _items.add(p); } 
	 
	 /** Deleteing the specified product. 
	  * @param p Product. 
	  * @throws ProductNotFoundException If the product does not exist. 
	  */ 
	 public void removeItem(Product p) throws ProductNotFoundException 
	 { if (!_items.remove(p)) { throw new ProductNotFoundException(); } } 
	 
	 /** 
	  * getting the number of items in the cart. 
	  * @return Item count. 
	  */ 
	 public int getItemCount() 
	 { return _items.size(); } 
	 
	 /** 
	  * Empties the cart. 
	  */ 
	 public void empty() 
	 { _items = new ArrayList(); } 
	 
	 /** Indicating whether the cart is empty. 
	  * @return true if the cart is empty; 
	  * false otherwise. 
	  */ 
	 public boolean isEmpty() 
	 { return (_items.size() == 0); }
}
