package com.thalesgroup.it.interview.vendingmachine;

public interface VendingMachine {

	double getChange();

	String getErrorMessage();

	void insertCoin(double value);

	void onKeyInserted(VendingKey key);

	void onKeyRemoved();

	boolean select(Beverage beverage);

}
