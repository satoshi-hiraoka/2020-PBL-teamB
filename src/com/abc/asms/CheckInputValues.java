package com.abc.asms;

public class CheckInputValues {

	public static boolean checkLength(String value, int max) {
		int length = value.getBytes().length;
		if (length > max) {
			return true;
		}
		return false;
	}

	public static boolean inputEmptyCheck(String value) {
		int length = value.getBytes().length;
		if (length == 0) {
			return true;
		}
		return false;
	}

	public static boolean mailFormatCheck(String mail) {
		String mailFormat = "^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$";
		if (!(mail.matches(mailFormat))) {
			return true;
		}
		return false;
	}

	public static boolean passwordCheck(String password, String passwordCheck) {
		if (!(password.equals(passwordCheck))) {
			return true;
		}
		return false;
	}

	public static boolean radioButtonCheck(String value) {
		if (value.equals("0") || value.equals("1")) {
			return false;
		}
		return true;
	}
}