package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);


		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		String msg = "incorrect amount returned";
		assertEquals(Optional.of(100.0).get(),SEK100.getAmount() );
		assertEquals(Optional.of(10.0).get(),EUR10.getAmount() );
		assertEquals(Optional.of(200.00).get(),SEK200.getAmount() );
		assertEquals(Optional.of(20.00).get(),EUR20.getAmount() );

	}

	@Test
	public void testGetCurrency() {
		assertTrue(EUR10.getCurrency().equals(EUR));
		assertTrue(SEK100.getCurrency().equals(SEK));
	}

	@Test
	public void testToString() {
		assertEquals( "10 EUR",EUR10.toString() ) ;
		assertEquals("0 EUR" , EUR0.toString());
	}

	@Test
	public void testGlobalValue() {

		assertEquals(Optional.of(1500.0).get() , EUR10.universalValue());
		assertEquals(Optional.of(0.0).get() , SEK0.universalValue() );
		assertEquals(Optional.of(-1500.0).get() , SEKn100.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		assertTrue(SEK0.equals(EUR0));
		assertFalse(SEK100.equals(SEK200));
		assertTrue(SEK100.equals(EUR10));
	}

	@Test
	public void testAdd() {
		Money m1 = SEK0.add(EUR0);
		Money m2 = SEK0.add(SEK100);
		Money m3 = SEK100.add(EUR10); // 100 * 15 + 100 * 15 = 30_000 /
		Money m4 = SEK100.add(SEK100);
		Money m5 = SEK100.add(SEKn100);

		assertTrue(m1.equals(SEK0));
		assertTrue(m2.equals(SEK100));
		assertTrue(m3.equals(SEK200));
		assertTrue(m3.equals(EUR20));
		assertTrue(m4.equals(SEK200));
		assertTrue(m5.equals(EUR0));
	}

	@Test
	public void testSub() {
		Money m1 = SEK0.sub(EUR0); //=0
		Money m2 = SEK0.sub(SEK100); //-100 SEK
		Money m3 = SEK100.sub(EUR10); //=0
		Money m4 = EUR20.sub(EUR10); // 10 EUR
		Money m5 = SEK100.sub(SEKn100); // 200SEK

		assertTrue(m1.equals(SEK0));
		assertTrue(m2.equals(SEKn100));
		assertTrue(m3.equals(SEK0));
		assertTrue(m4.equals(EUR10));
		assertTrue(m5.equals(SEK200));


	}

	@Test
	public void testIsZero() {
		assertTrue(EUR0.isZero());
		assertFalse(SEKn100.isZero());
		assertTrue(SEK0.isZero());
		assertFalse(SEKn100.isZero());

	}

	@Test
	public void testNegate() {
		assertTrue(SEK100.negate().equals(SEKn100));
		assertFalse(SEK100.negate().equals(SEK100));
	}

	@Test
	public void testCompareTo() {
		assertEquals(SEK100.compareTo(SEK0) , 1);
		assertEquals(EUR0.compareTo(SEK200) , -1);
		assertEquals(EUR20.compareTo(SEK100) , 1);
	}
}
