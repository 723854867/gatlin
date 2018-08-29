//package org.gatlin.soa.user.manager;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.gatlin.dao.bean.model.Query;
//import org.gatlin.soa.user.bean.entity.UserBorrowContract;
//import org.gatlin.soa.user.mybatis.dao.UserBorrowContractDao;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserBorrowContractManager {
//	@Resource
//	private UserBorrowContractDao userBorrowContractDao;
//
//	public long insert(UserBorrowContract userBorrowContract) {
//		return userBorrowContractDao.insert(userBorrowContract);
//	}
//
//	public void update(UserBorrowContract userBorrowContract) {
//		userBorrowContractDao.update(userBorrowContract);
//	}
//
//	public List<UserBorrowContract> getByInvestId(String InvestId) {
//		return userBorrowContractDao.getByInvestId(InvestId);
//	}
//	public List<UserBorrowContract> getByInvestIdList(String InvestId) {
//		return userBorrowContractDao.queryList(new Query().eq("invest_id", InvestId));
//	}
//	
//	public UserBorrowContract getById(long id) {
//		return userBorrowContractDao.getByKey(id);
//	}
//
//	public void batchInsert(List<UserBorrowContract> list) {
//		userBorrowContractDao.batchInsert(list);
//	}
//
//	/**
//	 * 随机查询一条(累计金额小于20万)
//	 * 
//	 * @return
//	 */
//	public UserBorrowContract selectOneForList(BigDecimal money) {
//		return userBorrowContractDao.selectOneForList(money);
//	}
//
//	/**
//	 * 随机查询一条（资产下面随机一条， 不包含已经签署过合同的人）
//	 * 
//	 * @return
//	 */
//	public UserBorrowContract selectOneForAssetList() {
//		UserBorrowContract contract = userBorrowContractDao.selectOneForAssetToBorrowContract();
//		if (null == contract) {
//			contract = userBorrowContractDao.selectOneForAsset();
//		}
//		return contract;
//	}
//
//	/**
//	 * 随机查询一条(累计金额小于20万，并且名字不包含在内的)
//	 * 
//	 * @param moeny
//	 * @param name
//	 * @return
//	 */
//	public UserBorrowContract selectOneForListByBorrowName(BigDecimal moeny, String name) {
//		return userBorrowContractDao.selectOneForListByBorrowName(moeny, name);
//	}
//
//	/**
//	 * 查询合同签署失败的记录
//	 * 
//	 * @return
//	 */
//	public List<UserBorrowContract> selectContractBycontract() {
//		return userBorrowContractDao.selectContractBycontract();
//	}
//	
//	public int batchUpdate(List<UserBorrowContract> list){
//		return userBorrowContractDao.updateCollection(list);
//	}
//	
//	public List<UserBorrowContract> queryList(Query query){
//		return userBorrowContractDao.queryList(query);
//	}
//}
