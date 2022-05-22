package kz.alseco;

import kz.alseco.calculators.Calculator;
import kz.alseco.calculators.Addition;
import kz.alseco.proxy.IOC;

public class Main {
	public static void main(String[] args) {
		final Calculator addition = IOC.getInstance(new Addition());

		addition.calculate(1, 1);
		addition.abs(-8);
		addition.tan(5, 6, 9);
	}
}
