package com.thalesgroup.it.interview.vendingmachine;

public interface Beverage {

	double getPrice();

	int getBrewTime();

	default void brew() throws InterruptedException {
		Thread.sleep(getBrewTime() * 1000);
	}

}
