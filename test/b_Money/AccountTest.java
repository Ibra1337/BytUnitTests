package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(100_000_00, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		// testing if adding works correctly
		testAccount.addTimedPayment("5" ,5,10 , new Money(1000,SEK) ,SweBank , "Alice"  );
		assertTrue(testAccount.timedPaymentExists("5"));

		//testing if removing works correctly
		testAccount.removeTimedPayment("5");
		assertFalse(testAccount.timedPaymentExists("5"));
	}

	@Test
	public void testGetBalance() {
		assertTrue(testAccount.getBalance().equals(new Money(100_000_00, SEK)));
	}



	@Test
	public void testAddWithdraw() {
		testAccount.withdraw(new Money(100_000_0 , SEK));
		assertTrue(testAccount.getBalance().equals(new Money(9000000,SEK)));
	}


	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		Money transferred = new Money(100_000_0  , SEK);
		String id = "1";
		testAccount.addTimedPayment(id , 3 , 1 ,transferred , SweBank , "Alice");
		assertTrue(testAccount.getBalance().equals(new Money(100_000_00, SEK)));
		testAccount.tick(); //next =0
		testAccount.tick(); // next =3 , make transfer
		//3 frist loops to tick the interval the 4th one to make actual effect on acounts
		for (int i =0 ; i<4 ;i++) {
			assertTrue(testAccount.getBalance().equals(new Money(9000000, SEK)));
			assertEquals( (Double) 20000.0 ,SweBank.getBalance("Alice")) ;
			testAccount.tick();
		}
		assertEquals( (Double) 30000.0 ,SweBank.getBalance("Alice")) ;
		assertTrue(testAccount.getBalance().equals(new Money(8000000, SEK)));
	}


}
