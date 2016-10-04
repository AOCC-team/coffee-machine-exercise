/*
 * Copyright 2016 Thales Italia spa.
 *
 * This program is not yet licensed and this file may not be used under any
 * circumstance.
 */
package com.thalesgroup.it.interview.vendingmachine;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class AdditionalEvaluationTest {

  private class Tea implements Beverage {

    @Override
    public void brew() throws InterruptedException {
      Beverage.super.brew();
    }

    @Override
    public int getBrewTime() {
      return 1;
    }

    @Override
    public double getPrice() {
      return 0.3;
    }

  }

  private final CoffeeMachineTestUtils u = new CoffeeMachineTestUtils();

  @Test
  public void should_be_able_to_brew_more_than_one_beverage() {
    final VendingMachine machine = u.getMachine();
    machine.insertCoin(0.5);
    final Beverage beverage1 = u.new Coffee();
    final Beverage beverage2 = u.new Cappuccino();

    final boolean coffeeDone = machine.select(beverage1);
    machine.insertCoin(0.3);
    final boolean cappuccinoDone = machine.select(beverage2);

    Assert.assertTrue("The machine didn't gave me my coffee!", coffeeDone);
    Assert.assertTrue("The machine didn't gave me my cappuccino!", cappuccinoDone);
    Assert.assertEquals("There should be no change inside the machine!", 0.1,
        machine.getChange(), 0);
  }

  @Test
  public void should_check_discounted_cost_if_there_is_a_key() {
    final VendingMachine machine = u.getMachine();
    machine.onKeyInserted(u.new ManagerKey());
    machine.insertCoin(0.2);
    final Beverage beverage = u.new Coffee();

    final long start = System.currentTimeMillis();
    final boolean done = machine.select(beverage);
    final long elapsedTime = System.currentTimeMillis() - start;

    Assert.assertTrue("The machine didn't gave me my coffee!", done);
    Assert.assertEquals("There is the wrong change inside the machine!", 0.05,
        machine.getChange(), 0);
    Assert.assertTrue("That was fast, did it brew?",
        elapsedTime >= beverage.getBrewTime() * 1000);
  }

  @Test
  public void should_check_greater_equal_credit() {
    final VendingMachine machine = u.getMachine();
    machine.insertCoin(0.3);
    final Beverage beverage = u.new Coffee();

    final boolean coffeeDone = machine.select(beverage);

    Assert.assertTrue("The machine didn't gave me my coffee!", coffeeDone);
    Assert.assertEquals("There should be no change inside the machine!", 0.0,
        machine.getChange(), 0);
  }

  @Test
  public void should_have_correct_credit() {
    final VendingMachine machine = u.getMachine();
    final VendingKey key = u.new EmployeeKey();

    machine.insertCoin(0.3);
    machine.onKeyInserted(key);
    machine.insertCoin(0.4);
    Assert.assertEquals("Total change on the key is incorrect", 0.7, machine.getChange(), 0.0);
    machine.select(u.new Coffee());
    Assert.assertEquals("Total change on the key is incorrect", 0.43, machine.getChange(),
        0.0);
    machine.select(u.new Coffee());
    Assert.assertEquals("Total change on the key is incorrect", 0.16, machine.getChange(),
        0.0);
    machine.onKeyRemoved();

    Assert.assertNull("There is an error: " + machine.getErrorMessage(),
        machine.getErrorMessage());
    Assert.assertEquals("There should be no change inside the machine!", 0.0,
        machine.getChange(), 0);
    Assert.assertEquals("Change on the key is incorrect", 0.16, key.getCredit(), 0.0);
  }

  @Test
  public void should_have_no_message_if_all_goes_well() {
    final VendingMachine machine = u.getMachine();
    machine.insertCoin(0.5);
    final Beverage beverage = u.new Coffee();

    final long start = System.currentTimeMillis();
    final boolean done = machine.select(beverage);
    final long elapsedTime = System.currentTimeMillis() - start;

    Assert.assertTrue("The machine didn't gave me my coffee!", done);
    Assert.assertEquals("There is the wrong change inside the machine!", 0.2,
        machine.getChange(), 0);
    Assert.assertTrue("That was fast, did it brew?",
        elapsedTime >= beverage.getBrewTime() * 1000);
    Assert.assertTrue("There is an error message", u.isNullOrEmpty(machine.getErrorMessage()));
  }

  @Test
  public void should_manage_exceptions() throws InterruptedException {
    final VendingMachine machine = u.getMachine();
    machine.insertCoin(0.4);
    final Beverage beverage = Mockito.mock(Tea.class, Mockito.CALLS_REAL_METHODS);
    Mockito.doThrow(new InterruptedException()).when(beverage).brew();

    final boolean done = machine.select(beverage);

    Assert.assertFalse("The machine gave me a coffee!", done);
    Assert.assertNotNull("There is no error message", machine.getErrorMessage());
  }

  @Test
  public void sohuld_use_java8_method_brew() throws InterruptedException {
    final VendingMachine machine = u.getMachine();
    machine.insertCoin(0.4);
    final Beverage beverage = Mockito.mock(Tea.class, Mockito.CALLS_REAL_METHODS);

    final boolean done = machine.select(beverage);

    Assert.assertTrue("The machine didn't gave me my coffee!", done);
    Mockito.verify(beverage, Mockito.times(1)).brew();
  }
}
