package kz.alseco.calculators;

import kz.alseco.annotations.Log;
import kz.alseco.calculators.Calculator;

public class Addition implements Calculator {

	@Override
	@Log
	public double calculate(final double firstOperand, final double secondOperand) {
		return firstOperand + secondOperand;
	}

	@Override
	@Log
	public double abs(double firstOperand) {
		return Math.abs(firstOperand);
	}

	@Override
	public double tan(double firstOperand, double secondOperand, double thriftOperand) {
		return Math.tan(firstOperand) +  Math.tan(secondOperand) + Math.tan(thriftOperand);
	}
}
