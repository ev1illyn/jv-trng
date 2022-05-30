package br.ce.wcaquino.asserts;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTest {

	@Test
	public void test() {
		
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		Assert.assertEquals(1,1);
		Assert.assertEquals(Math.PI, 3.14, 0.01);
		
		int i = 23;
		Integer integerObject = 23;
		
		Assert.assertEquals(Integer.valueOf(i), integerObject);
		Assert.assertEquals(i, integerObject.intValue());
		
		Assert.assertEquals("juliette", "juliette");
		Assert.assertTrue("juliette".equalsIgnoreCase("Juliette"));
		Assert.assertTrue("juliette".startsWith("ju"));
		
		Usuario userOne = new Usuario("Usuario 1");
		Usuario userEqualOne = userOne;
		
		Assert.assertEquals(userOne, userEqualOne);
		Assert.assertSame(userOne, userEqualOne);
		
		Usuario userTwo = new Usuario("Usuario 2");
		
		Assert.assertNotEquals(userOne, userTwo);
		
		Usuario userNull = null;
		
		Assert.assertSame(userNull, null);
		
	}

}
