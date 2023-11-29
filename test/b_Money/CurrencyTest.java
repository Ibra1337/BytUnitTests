package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		String msg = "Returned currency name not correct";
		assertEquals(msg , "SEK" ,SEK.getName() );
		assertEquals(msg , "DKK" ,DKK.getName() );
		assertEquals(msg , "EUR" ,EUR.getName() );

	}
	
	@Test
	public void testGetRate() {
		fail("Write test case here");
	}
	
	@Test
	public void testSetRate() {

		String msg = "The rate is inncorect";
		SEK.setRate(0.20);
		assertEquals(msg , Optional.of(0.20).get(),SEK.getRate());
		assertFalse("the rate hasnt changed" , SEK.getRate() ==0.15);
	}
	
	@Test
	public void testGlobalValue() {
		String msg = "incorrect global value change";
		assertEquals(msg , Optional.of(SEK.universalValue(10)), Optional.of(1.5));
		assertEquals(msg , Optional.of(DKK.universalValue(10)), Optional.of(2.0));
		assertEquals(msg , Optional.of(EUR.universalValue(10)), Optional.of(15.0));
	}
	
	@Test
	public void testValueInThisCurrency() {
		 // 150 / 1.5 100SEK
		String msg = "incorrect Conversion";
		assertEquals(msg, Optional.of(100).get(), SEK.valueInThisCurrency(10 , EUR)   );
		// 15 / 0.2 = 75
		assertEquals(msg, Optional.of(75).get(), DKK.valueInThisCurrency(10 , EUR)   );
		//1.5 / 0.2 = 7
		assertEquals(msg, Optional.of(7).get(), DKK.valueInThisCurrency(10 , SEK)   );
	}

}
