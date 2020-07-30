package com.abc.asms;

import java.util.ArrayList;
import java.util.HashMap;

public class PermitUseFunction {
	HashMap<String, ArrayList<String>> permitList = new HashMap<String, ArrayList<String>>();

	PermitUseFunction() {

		ArrayList<String> accountList = new ArrayList<>();
		accountList.add("10");
		accountList.add("11");

		ArrayList<String> salesList = new ArrayList<>();
		salesList.add("1");
		salesList.add("11");

		ArrayList<String> allList = new ArrayList<>();
		allList.addAll(accountList);
		allList.addAll(salesList);
		allList.add("0");

		permitList.put("account", accountList);
		permitList.put("sales", salesList);
		permitList.put("all", allList);

	}

	public ArrayList<String> getPermitList(String targetFunctionName) {
		return permitList.get(targetFunctionName);
	}
}
