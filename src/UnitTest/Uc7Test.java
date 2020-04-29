package UnitTest;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import BookStore.*;


public class Uc7Test {

	BookStore bookStore;
	ArrayList<Integer> customer_type = new ArrayList<Integer>();
	ArrayList<Integer> order_price = new ArrayList<Integer>();
	ArrayList<Integer> coupon_discount = new ArrayList<Integer>();
	ArrayList<Integer> total_discount = new ArrayList<Integer>();
	ArrayList<Double> grand_price = new ArrayList<Double>();
	int actualDiscount;

	@Before
	public void setUp() throws Exception {
		bookStore = new BookStore();
		String csvFile = "NewTestCase.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			boolean isfirst = true;
			while ((line = br.readLine()) != null) {
				if (isfirst) {
					isfirst = false;
				} else {
					String[] testcase = line.split(cvsSplitBy);
					switch (testcase[2]) {
					case "Bronze":
						customer_type.add(1);
						break;
					case "Silver":
						customer_type.add(2);
						break;
					case "Gold":
						customer_type.add(3);
						break;
					default:
						customer_type.add(999);
						break;
					}
					order_price.add(Integer.parseInt(testcase[5]));
					coupon_discount.add(Integer.parseInt(testcase[7]));
					total_discount.add(Integer.parseInt(testcase[8]));
					grand_price.add(Double.parseDouble(testcase[9]));
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		bookStore = null;
	}

	@Test
	public void testGetDiscount() {
		System.out.print(order_price.size()+"|"+customer_type.size());
		for (int i = 0; i < order_price.size(); i++) {
			int expectedDiscount = total_discount.get(i);
			actualDiscount = BookStore.getDiscount(order_price.get(i), customer_type.get(i), coupon_discount.get(i));
			System.out.println(i+"Customer:"+customer_type.get(i)+" order_price:"+order_price.get(i)+"  coupon_discount:"+coupon_discount.get(i));
			System.out.println(i+"total_discount:"+total_discount.get(i)+" actual_dis:"+actualDiscount);
			assertEquals("Error: Expected is not equal to Actual", expectedDiscount, actualDiscount);
		}

	}

	@Test
	public void testGetDiscountBalance() {
//		for (int i = 0; i < order_price.size(); i++) {
//			double actualNetBal = bookStore.getDiscountBalance(order_price.get(i), total_discount.get(i));
//			assertEquals("Error: Expected is not equal to Actual", grand_price.get(i), actualNetBal, 0.0);
//		}
	}

}
