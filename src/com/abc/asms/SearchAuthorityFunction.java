package com.abc.asms;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchAuthorityFunction {
	HashMap<String, ArrayList<String>> authList = new HashMap<String, ArrayList<String>>();

	SearchAuthorityFunction() {

		ArrayList<String> noAuthList = new ArrayList<>();
		noAuthList.add("0");

		ArrayList<String> onlyAuthSalesList = new ArrayList<>();
		onlyAuthSalesList.add("1");

		ArrayList<String> onlyAuthAccountList = new ArrayList<>();
		onlyAuthAccountList.add("10");

		ArrayList<String> authSalesAndAccountList = new ArrayList<>();
		authSalesAndAccountList.add("11");

		ArrayList<String> noAuthSalesList = new ArrayList<>();
		noAuthSalesList.addAll(noAuthList);
		noAuthSalesList.addAll(onlyAuthAccountList);

		ArrayList<String> noAuthAccountList = new ArrayList<>();
		noAuthAccountList.addAll(noAuthList);
		noAuthAccountList.addAll(onlyAuthSalesList);

		ArrayList<String> authSalesList = new ArrayList<>();
		authSalesList.addAll(onlyAuthSalesList);
		authSalesList.addAll(authSalesAndAccountList);

		ArrayList<String> authAccountList = new ArrayList<>();
		authAccountList.addAll(onlyAuthAccountList);
		authAccountList.addAll(authSalesAndAccountList);

		ArrayList<String> allAuthList = new ArrayList<>();
		allAuthList.addAll(noAuthList);
		allAuthList.addAll(onlyAuthSalesList);
		allAuthList.addAll(onlyAuthAccountList);
		allAuthList.addAll(authSalesAndAccountList);


		authList.put("noAuth", noAuthList);
		authList.put("onlySales", onlyAuthSalesList);
		authList.put("onlyAccount", onlyAuthAccountList);
		authList.put("salesAndAccount", authSalesAndAccountList);
		authList.put("noSales", noAuthSalesList);
		authList.put("noAccount", noAuthAccountList);
		authList.put("sales", authSalesList);
		authList.put("account", authAccountList);
		authList.put("all", allAuthList);
	}

	public ArrayList<String> getAuthList(String targetfunctionName) {
		return authList.get(targetfunctionName);
	}
}
