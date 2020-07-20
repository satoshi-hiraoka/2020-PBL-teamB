package com.abc.asms;

public class CheckLength implements InterfaceCheckLength {
	private String value;
	private int max;

	CheckLength() {

	}
@Override
	public boolean checkLength(String value, int max) {
		this.value = value;
		this.max = max;
		int length = value.getBytes().length;
		if (length >= max) {
			return true;
		}
		return false;
	}
@Override
	public boolean inputEmptyCheck(String value) {
		this.value = value;
		int length = value.getBytes().length;
		if (length == 0) {
			return true;
		}
		return false;

	}
}