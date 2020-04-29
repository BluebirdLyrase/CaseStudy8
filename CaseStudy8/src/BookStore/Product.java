package BookStore;

public class Product {
	/** book title */
	private String _title;
	
	/** book autohor */
	private String _author;
	
	/** book price */
	private double _price;
	/**
	* Constructor Product.
	*
	* @param title(Book title).
	* @param author (Book author).
	* @param price (Book price).
	*/
	public Product(String title, String author, double price) {
		_title = title;
		_author = author;
		_price = price;
	}
	/**
	* getting book title.
	*
	* @return _title (Book title).
	*/
	public String getTitle() {
		return _title;
	}
	/**
	* getting  book author.
	*
	* @return _author (Book author).
	*/
	public String getAuthor() {
		return _author;
	}
		
	/**
	* getting book price.
	* @return _price (Book price).
	*/
	public double getPrice() {
		return _price;
	}
		
	/**
	* comparing book title with other.
	* @param Product instance.
	* @return boolean (true or false).
	*/
	public boolean equals(Object obj) {
		if (obj instanceof Product) {
		Product b = (Product)obj;
		return b.getTitle().equals(_title);
		}
		return false;
		}

}
