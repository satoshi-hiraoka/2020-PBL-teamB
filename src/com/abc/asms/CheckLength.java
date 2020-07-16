package com.abc.asms;

public class CheckLength implements interfaceCheckLength {
	private String value;
	private int max;

	public CheckLength() {

	}

	public boolean checkLength(String value,int max) {
		this.value = value;
		this.max = max;
		int length = value.getBytes().length;
		if (length >= max) {
			return true;
		}
		return false;
	}
}
interface interfaceCheckLength{
	public boolean checkLength(String value,int max);
}
