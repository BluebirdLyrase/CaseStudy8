package BookStore;

import java.util.ArrayList;

public class Customer {
	/** customer id */
	private String cusId;
	
	/** customer name */
	private String cusName;
	
	/** customer address */
	private String cusAddr;
	
	/** customer type id */
	private int cusType;	
	
	/** customer type value */
	private String [] cusTypeName = {"Walk in", "Bronze member", "Silver member", "Gold member"};

	/**
	 * setting a customer id
	 * @param cusId (customer id)
	 */
	public void setCusId(String cusId)
	{
		this.cusId = cusId;
	}
	
	/**
	 * getting a customer id
	 * @return cusId (Customer id)
	 */
	public String getCusId()
	{
		return cusId;
	}
	
	/**
	 * setting a customer name
	 * @param cusName (Customer name)
	 */
	public void setCusName(String cusName)
	{
		this.cusName = cusName;
	}
	
	/**
	 * getting a customer name
	 * @return cusName (Customer name)
	 */
	public String getCusName()
	{
		return cusName;
	}
	
	/**
	 * setting a customer address
	 * @param cusAddr (Customer address)
	 */
	public void setCusAddr(String cusAddr)
	{
		this.cusAddr = cusAddr;
	}

	/**
	 * getting a customer address
	 * @return cusAddr (Customer address)
	 */
	public String getCusAddr()
	{
		return cusAddr;
	}
	
	/**
	 * setting a customer type
	 * @param cusType (Customer type)
	 */
	public void setCusType(int cusType)
	{
		this.cusType = cusType;
	}
	
	/**
	 * getting a customer type id
	 * @return cusType (Customer type id)
	 */
	public int getCusType()
	{
		return cusType;
	}
	
	/**
	 * getting a customer type value
	 * @param cusType (Customer type id)
	 * @return customer type value
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public String getCusTypeName(int cusType) throws ArrayIndexOutOfBoundsException
	{
		return cusTypeName[cusType];
	}
	
	

}

