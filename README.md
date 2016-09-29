# coffee-machine-exercise

This is a simple exercise to test basic java programming skills.

The provided code include
- an interface for a generic vending machine (VendingMachine.java)
- an interface for a beverage, to be brewed by the vending machine (Beverage.java)
- an interface for a vending key, everyone can have a key, with its credit and a discount (VendingKey.java)
- a stub for a coffee machine to be implemented (CoffeeMachine.java)
- a test to check that the implemented machine works properly (CoffeeMachineTest.java)

Your task is to correctly implement the coffee machine so it can brew a beverage when one is selected.
The machine should check that there is enough credit to brew the selected beverage, and warn the user if the credit is insufficient.

If I use a key, the machine should check the credit inside the key and use that (with the proper discount) to pay the beverage.

If correctly implemented, all the tests should run. 