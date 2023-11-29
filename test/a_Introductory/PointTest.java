package a_Introductory;


import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class PointTest {
	Point p1, p2, p3;
	
	@Before
	public void setUp() throws Exception {
		p1 = new Point(7, 9);
		p2 = new Point(-3, -30);
		p3 = new Point(-10, 3);
	}

	@Test
	public void testAdd() {
		Point res1 = p1.add(p2);
		Point res2 = p1.add(p3);

		assertEquals(Optional.of(4).get(), res1.x);
		assertEquals(Optional.of(-21).get(), res1.y);
		assertEquals(Optional.of(-3).get(), res2.x);
		assertEquals(Optional.of(12).get(), res2.y);
	}

	@Test
	public void testSub() {
		Point res1 = p1.sub(p2);
		Point res2 = p1.sub(p3);



		assertEquals(Optional.of(10).get(), res1.x);
		assertEquals(Optional.of(39).get(), res1.y);
		assertEquals(Optional.of(17).get(), res2.x);
		assertEquals(Optional.of(6).get(), res2.y);
	}

}
