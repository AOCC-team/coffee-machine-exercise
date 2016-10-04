/*
 * Copyright 2016 Thales Italia spa.
 *
 * This program is not yet licensed and this file may not be used under any
 * circumstance.
 */
package com.thalesgroup.it.interview.vendingmachine;

public class CoffeeMachine implements VendingMachine {

  @Override
  public double getChange() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getErrorMessage() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void insertCoin(final double value) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onKeyInserted(final VendingKey key) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onKeyRemoved() {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean select(final Beverage beverage) {
    // TODO Auto-generated method stub
    return false;
  }
}
