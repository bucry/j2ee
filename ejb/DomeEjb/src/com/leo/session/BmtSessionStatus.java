package com.leo.session;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;

@Stateful(mappedName = "BmtSessionStatus")
@TransactionManagement(TransactionManagementType.BEAN)
public class BmtSessionStatus implements IBmtSessionStatus {
	@Resource
	private UserTransaction tx;
}
