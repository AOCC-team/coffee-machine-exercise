/*
 * Copyright 2016 Thales Italia spa.
 *
 * This program is not yet licensed and this file may not be used under any
 * circumstance.
 */
package com.thalesgroup.it.interview.vendingmachine;

public class CoffeeMachineTestUtils {

  public class Cappuccino implements Beverage {

    @Override
    public int getBrewTime() {
      return 2;
    }

    @Override
    public double getPrice() {
      return 0.4;
    }
  }

  /* Utility classes */
  public class Coffee implements Beverage {

    @Override
    public int getBrewTime() {
      return 1;
    }

    @Override
    public double getPrice() {
      return 0.3;
    }

  }

  /**
   * Employee key has 10% discount
   */
  public class EmployeeKey implements VendingKey {

    public double credit;

    @Override
    public double getCredit() {
      return credit;
    }

    @Override
    public double getPercentageDiscount() {
      return 0.1;
    }

    @Override
    public void setCredit(final double credit) {
      this.credit = credit;
    }
  }

  /**
   * Manager key has 50% discount
   */
  public class ManagerKey implements VendingKey {

    public double credit;

    @Override
    public double getCredit() {
      return credit;
    }

    @Override
    public double getPercentageDiscount() {
      return 0.5;
    }

    @Override
    public void setCredit(final double credit) {
      this.credit = credit;
    }
  }

  /* utility method */
  public VendingMachine getMachine() {
    return new CoffeeMachine();
  }

  public boolean isNullOrEmpty(final String errorMessage) {
    return errorMessage == null || errorMessage.trim().isEmpty();
  }
}
