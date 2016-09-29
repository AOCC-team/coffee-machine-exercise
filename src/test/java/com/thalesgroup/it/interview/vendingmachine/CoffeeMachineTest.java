package com.thalesgroup.it.interview.vendingmachine;

import org.junit.Assert;
import org.junit.Test;

public class CoffeeMachineTest {

	@Test
	public void should_give_a_warning_if_there_is_no_credit() {
		VendingMachine machine = getMachine();

		boolean done = machine.select(new Coffee());

		Assert.assertFalse("The machine gave me a coffee!", done);
		Assert.assertEquals("There is credit inside the machine?", 0.0, machine.getChange(), 0);
		Assert.assertNotNull("No message has been given to the user!", machine.getErrorMessage());
	}

	@Test
	public void should_brew_beverage_and_give_change_if_there_is_credit() {
		VendingMachine machine = getMachine();
		machine.insertCoin(0.5);
		Beverage beverage = new Cappuccino();

		long start = System.currentTimeMillis();
		boolean done = machine.select(beverage);
		long elapsedTime = System.currentTimeMillis() - start;

		Assert.assertTrue("The machine didn't gave me my cappuccino!", done);
		Assert.assertEquals("There is the wrong change inside the machine!", 0.1, machine.getChange(), 0);
		Assert.assertTrue("That was fast, did it brew?", elapsedTime >= beverage.getBrewTime() * 1000);
	}

	@Test
	public void should_cost_less_if_there_is_a_key() {
		VendingMachine machine = getMachine();
		machine.onKeyInserted(new EmployeeKey());
		machine.insertCoin(0.4);
		Beverage beverage = new Cappuccino();

		long start = System.currentTimeMillis();
		boolean done = machine.select(beverage);
		long elapsedTime = System.currentTimeMillis() - start;

		Assert.assertTrue("The machine didn't gave me my cappuccino!", done);
		Assert.assertEquals("There is the wrong change inside the machine!", 0.04, machine.getChange(), 0);
		Assert.assertTrue("That was fast, did it brew?", elapsedTime >= beverage.getBrewTime() * 1000);
	}

	@Test
	public void should_cost_half_if_there_is_a_manager_key() {
		VendingMachine machine = getMachine();
		machine.onKeyInserted(new ManagerKey());
		machine.insertCoin(0.5);
		Beverage beverage = new Coffee();

		long start = System.currentTimeMillis();
		boolean done = machine.select(beverage);
		long elapsedTime = System.currentTimeMillis() - start;

		Assert.assertTrue("The machine didn't gave me my coffee!", done);
		Assert.assertEquals("There is the wrong change inside the machine!", 0.35, machine.getChange(), 0);
		Assert.assertTrue("That was fast, did it brew?", elapsedTime >= beverage.getBrewTime() * 1000);
	}

	@Test
	public void should_store_the_credit_in_the_key_when_present() {
		VendingMachine machine = getMachine();
		VendingKey key = new ManagerKey();
		machine.onKeyInserted(key);
		machine.insertCoin(0.5);
		Beverage beverage = new Coffee();

		boolean done = machine.select(beverage);
		machine.onKeyRemoved();

		Assert.assertTrue("The machine didn't gave me my coffee!", done);
		Assert.assertEquals("There should be no change inside the machine!", 0.0, machine.getChange(), 0);
		Assert.assertEquals("There is the wrong credit on my key!", 0.35, key.getCredit(), 0);
	}

	/* utility method */
	private VendingMachine getMachine() {
		return new CoffeeMachine();
	}

	/* Utility classes */
	private class Coffee implements Beverage {

		@Override
		public double getPrice() {
			return 0.3;
		}

		@Override
		public int getBrewTime() {
			return 1;
		}

	}

	private class Cappuccino implements Beverage {

		@Override
		public double getPrice() {
			return 0.4;
		}

		@Override
		public int getBrewTime() {
			return 2;
		}
	}

	/**
	 * Employee key has 10% discount
	 */
	private class EmployeeKey implements VendingKey {

		private double credit;

		@Override
		public double getCredit() {
			return credit;
		}

		@Override
		public void setCredit(double credit) {
			this.credit = credit;
		}

		@Override
		public double getPercentageDiscount() {
			return 0.1;
		}
	}

	/**
	 * Manager key has 50% discount
	 */
	private class ManagerKey implements VendingKey {

		private double credit;

		@Override
		public double getCredit() {
			return credit;
		}

		@Override
		public void setCredit(double credit) {
			this.credit = credit;
		}

		@Override
		public double getPercentageDiscount() {
			return 0.5;
		}
	}
}
