package org.simbiosis.ui.bprs.loan.shared;

public enum LoanScheduleTypeDv {
	FLAT, EFFECTIVE, ANUITAS;
	public static String valueToString(int value) {
		switch (value) {
		case 1:
			return "FLAT";
		case 2:
			return "EFFECTIVE";
		case 3:
			return "ANUITAS";
		default:
			return "";
		}
	}
}
