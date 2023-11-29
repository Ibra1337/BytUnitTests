package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class BankTest {
	Currency SEK, DKK ,EUR;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		EUR = new Currency("EUR" , 1.5);
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		String msg = "incorrect name returned";
		assertEquals(msg , "SweBank" , SweBank.getName());
		assertEquals(msg , "Nordea" , Nordea.getName());
		assertEquals(msg , "DanskeBank" , DanskeBank.getName());

	}

	@Test
	public void testGetCurrency() {
		String msg = "incorrect Currency Returned";
		assertTrue(SEK.equals(SweBank.getCurrency()));
		assertTrue(SEK.equals(Nordea.getCurrency()));
		assertTrue(DKK.equals(DanskeBank.getCurrency()));

	}



	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		//checking if accounts are addedpropoerly
		String nUserId = "newUser";
		try {
			SweBank.openAccount(nUserId);
			Nordea.openAccount(nUserId);
		} catch (AccountExistsException e) {
			fail("account with given id exists");
		}
		//checking if exception is being thrown when account with given id is created
		assertThrows(AccountExistsException.class , () ->{SweBank.openAccount(nUserId);} );
		assertThrows(AccountExistsException.class , () ->{Nordea.openAccount(nUserId);} );
	}

	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		String nUserId = "newUser";

		assertThrows(AccountDoesNotExistException.class , () ->SweBank.getBalance(nUserId));
		Money testMoney = new Money(1000 , SEK);
		assertEquals((Double) 0.0 , SweBank.getBalance("Bob") );
		SweBank.deposit("Bob" , testMoney);
		assertEquals( new Money(1000 , SweBank.getCurrency()).getAmount() , SweBank.getBalance("Bob"));

	}


	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		String nUserId = "newUser";
		//if exception is being thrown when given user does not exists
		String bob = "Bob";
		Money testMoney = new Money(1000 , SEK);
		assertThrows(AccountDoesNotExistException.class , () ->{SweBank.deposit(nUserId, new Money(1000, SEK));} );
		SweBank.deposit(bob , testMoney);
		//checking if the ammount of money is correct (starting bob zero inserting 10.00 SEK
		assertEquals(SweBank.getBalance(bob) , new Money(1000 , SweBank.getCurrency()).getAmount());
		SweBank.deposit(bob , testMoney);
		assertEquals(SweBank.getBalance(bob) , new Money(2000 , SweBank.getCurrency()).getAmount());
	}


	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		String nUserId = "newUser";
		Money testMoney = new Money(10000 , SEK);
		assertThrows(AccountDoesNotExistException.class , ()->SweBank.withdraw(nUserId , testMoney));
		SweBank.deposit("Bob" , testMoney); // dep 100.00
		SweBank.withdraw("Bob" , new Money(1000 , SEK));//100.00 - 10.00 = 90.00
		assertEquals((Double) 90.0 , SweBank.getBalance("Bob") );
		SweBank.withdraw("Bob" , new Money(100, EUR));
		assertEquals((Double) 80.0 , SweBank.getBalance("Bob") );
	}
	

	@Test
	public void testTransfer() throws AccountDoesNotExistException {

		Money testMoney = new Money(10000 , SEK);

		assertThrows(AccountDoesNotExistException.class , ()->SweBank.transfer("dsas" , "asd" , testMoney ));
		SweBank.deposit("Bob" , testMoney);
		SweBank.transfer("Bob" , Nordea , "Bob" ,new Money(1000,SEK) );
		assertEquals((Double) 90.0 , SweBank.getBalance("Bob") );
		assertEquals((Double) 10.0 , Nordea.getBalance("Bob") );
	}

	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		Money transferred = new Money(100  , SEK);
		String id = "1";
		Money testMoney = new Money(1000 , SEK);
		SweBank.addTimedPayment("Bob" , "1" ,3 , 1,transferred ,Nordea , "Bob");
		SweBank.deposit("Bob" , testMoney);
		SweBank.tick(); // next = 0
		SweBank.tick(); // do transfers
		for (int i =0 ; i <3 ; i++) {
			assertEquals((Double) 9.0, SweBank.getBalance("Bob"));
			SweBank.tick();
		}
		SweBank.tick();
		assertEquals((Double) 8.0, SweBank.getBalance("Bob"));
		assertEquals((Double) 2.0 , Nordea.getBalance("Bob"));

	}
}
