package com.abc.asms;

//このクラスは使用しない（開発ルール.txtの2020/7/30変更点参照）
public class CheckLength {
	private String value;
	private int max;

	public boolean checkLength(String value, int max) {
		this.value = value;
		this.max = max;
		int length = value.getBytes().length;
		if (length <= max) {
			return true;
		}
		return false;
	}

	public boolean inputEmptyCheck(String value) {
		this.value = value;
		int length = value.getBytes().length;
		if (length == 0) {
			return true;
		}
		return false;
	}
}
