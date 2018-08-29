package org.gatlin.soa.user.service;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.user.api.UserBorrowContractService;
import org.gatlin.soa.user.bean.entity.UserBorrowContract;
import org.gatlin.soa.user.manager.UserBorrowContractManager;
import org.springframework.stereotype.Service;

@Service("userBorrowContractService")
public class UserBorrowContractServiceImpl implements UserBorrowContractService {

	@Resource
	private UserBorrowContractManager userBorrowContractManager;

	/**
	 * 随机查询一条(累计金额小于20万)
	 * 
	 * @return
	 */
	public UserBorrowContract selectOneForList(BigDecimal money) {
		return userBorrowContractManager.selectOneForList(money);
	}

	/**
	 * 随机查询一条（资产下面随机一条， 不包含已经签署过合同的人）
	 * 
	 * @return
	 */
	public UserBorrowContract selectOneForAssetList() {
		return userBorrowContractManager.selectOneForAssetList();
	}

	/**
	 * 随机查询一条(累计金额小于20万，并且名字不包含在内的)
	 * 
	 * @param moeny
	 * @param name
	 * @return
	 */
	public UserBorrowContract selectOneForListByBorrowName(BigDecimal moeny, String name) {
		return userBorrowContractManager.selectOneForListByBorrowName(moeny, name);
	}

	public long insert(UserBorrowContract userBorrowContract) {
		return userBorrowContractManager.insert(userBorrowContract);
	}

	public void update(UserBorrowContract userBorrowContract) {
		userBorrowContractManager.update(userBorrowContract);
	}

	public List<UserBorrowContract> getByInvestId(String InvestId) {
		return userBorrowContractManager.getByInvestId(InvestId);
	}
	
	
	
	public List<UserBorrowContract> getByInvestIdList(String InvestId) {
		return userBorrowContractManager.getByInvestIdList(InvestId);
	}
	
	public UserBorrowContract getById(long id) {
		return userBorrowContractManager.getById(id);
	}

	

	/**
	 * 查询合同签署失败的记录
	 * 
	 * @return
	 */
	public List<UserBorrowContract> selectContractBycontract() {
		return userBorrowContractManager.selectContractBycontract();
	}
	public void batchInsert(List<UserBorrowContract> list){
		userBorrowContractManager.batchInsert(list);
	}

	public int batchUpdate(List<UserBorrowContract> list){
		return userBorrowContractManager.batchUpdate(list);
	}
	
	public List<UserBorrowContract> queryList(Query query){
		return userBorrowContractManager.queryList(query);
	}
}
