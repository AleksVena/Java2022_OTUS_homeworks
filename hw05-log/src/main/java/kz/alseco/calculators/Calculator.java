package kz.alseco.calculators;

public interface Calculator {
	double calculate(final double firstOperand, final double secondOperand);
	double abs(final double firstOperand);
	double tan(final double firstOperand, final double secondOperand, final double thriftOperand);
}
