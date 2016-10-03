package com.thalesgroup.it.interview.vendingmachine;

import org.junit.Assert;
import org.junit.Test;

public class CoffeeMachineTest {

	private CoffeeMachineTestUtils u = new CoffeeMachineTestUtils();

	@Test
	public void should_give_a_warning_if_there_is_no_credit() {
		VendingMachine machine = u.getMachine();

		boolean done = machine.select(u.new Coffee());

		Assert.assertFalse("The machine gave me a coffee!", done);
		Assert.assertEquals("There is credit inside the machine?", 0.0, machine.getChange(), 0);
		Assert.assertNotNull("No message has been given to the user!", machine.getErrorMessage());
	}

	@Test
	public void should_brew_beverage_and_give_change_if_there_is_credit() {
		VendingMachine machine = u.getMachine();
		machine.insertCoin(0.5);
		Beverage beverage = u.new Cappuccino();

		long start = System.currentTimeMillis();
		boolean done = machine.select(beverage);
		long elapsedTime = System.currentTimeMillis() - start;

		Assert.assertTrue("The machine didn't gave me my cappuccino!", done);
		Assert.assertEquals("There is the wrong change inside the machine!", 0.1, machine.getChange(), 0);
		Assert.assertTrue("That was fast, did it brew?", elapsedTime >= beverage.getBrewTime() * 1000);
	}

	@Test
	public void should_cost_less_if_there_is_a_key() {
		VendingMachine machine = u.getMachine();
		machine.onKeyInserted(u.new EmployeeKey());
		machine.insertCoin(0.4);
		Beverage beverage = u.new Cappuccino();

		long start = System.currentTimeMillis();
		boolean done = machine.select(beverage);
		long elapsedTime = System.currentTimeMillis() - start;

		Assert.assertTrue("The machine didn't gave me my cappuccino!", done);
		Assert.assertEquals("There is the wrong change inside the machine!", 0.04, machine.getChange(), 0);
		Assert.assertTrue("That was fast, did it brew?", elapsedTime >= beverage.getBrewTime() * 1000);
	}

	@Test
	public void should_cost_half_if_there_is_a_manager_key() {
		VendingMachine machine = u.getMachine();
		machine.onKeyInserted(u.new ManagerKey());
		machine.insertCoin(0.5);
		Beverage beverage = u.new Coffee();

		long start = System.currentTimeMillis();
		boolean done = machine.select(beverage);
		long elapsedTime = System.currentTimeMillis() - start;

		Assert.assertTrue("The machine didn't gave me my coffee!", done);
		Assert.assertEquals("There is the wrong change inside the machine!", 0.35, machine.getChange(), 0);
		Assert.assertTrue("That was fast, did it brew?", elapsedTime >= beverage.getBrewTime() * 1000);
	}

	@Test
	public void should_store_the_credit_in_the_key_when_present() {
		VendingMachine machine = u.getMachine();
		VendingKey key = u.new ManagerKey();
		machine.onKeyInserted(key);
		machine.insertCoin(0.5);
		Beverage beverage = u.new Coffee();

		boolean done = machine.select(beverage);
		machine.onKeyRemoved();

		Assert.assertTrue("The machine didn't gave me my coffee!", done);
		Assert.assertEquals("There should be no change inside the machine!", 0.0, machine.getChange(), 0);
		Assert.assertEquals("There is the wrong credit on my key!", 0.35, key.getCredit(), 0);
	}
}
