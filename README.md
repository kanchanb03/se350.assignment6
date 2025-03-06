# se350.assignment6
[![Build Status](https://github.com/kanchanb03/se350.assignment6/actions/workflows/SE333_CI.yml/badge.svg)](https://github.com/<OWNER>/<REPO>/actions/workflows/SE333_CI.yml)


Barnes
Specification-based Test:checks if  the total price is computed correctly and no unavailable items are reported.
Structural-based Test: checks if user exceeds quantity is available in stock and checks if it is recorded correctly Examines the internal logic when the requested quantity exceeds the available stock. It checks the reduced total price and the unavailable quantity correctly recorded.
Specification-based Test : checks that a null order returns null.


Amazon
testCalculateTotalPrice: Checks that the system calculates the correct total price when real items are added.
testAddToCartIncreasesCount: Verifies that adding an item increases the shopping cart’s item count by one.
testCalculateSumOfRules: checks that Amazon correctly adds up the fixed prices provided by the mocked price rules.
testAddToCartDelegation: Confirms that calling Amazon’s addToCart method delegates the action to the shopping cart’s add method.

