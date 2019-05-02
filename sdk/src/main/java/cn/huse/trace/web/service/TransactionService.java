package cn.huse.trace.web.service;

import cn.huse.trace.web.cache.CacheHelper;
import cn.huse.trace.web.common.Utils;
import cn.huse.trace.web.dao.DaoException;
import cn.huse.trace.web.dao.TransactionDao;
import cn.huse.trace.web.entity.Transaction;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: huanxi
 * @date: 2019/4/28 13:44
 */
@Service
public class TransactionService {
    @Resource
    TransactionDao transactionDao;

    @Resource
    CacheHelper cacheHelper;

    public void addTransaction(Transaction transaction) throws DaoException {
        //设置缓存无效
        cacheHelper.setKeyAble(CacheHelper.Balance + transaction.getOutId(), false);
        cacheHelper.setKeyAble(CacheHelper.Balance + transaction.getInId(), false);
        //构造Id
        transaction.setId(Transaction.FLAG + "_" + Utils.getUUID());
        transaction.setTime(System.currentTimeMillis());
        transactionDao.add(transaction);
    }

    public float getBalance(String userId) {
        return transactionDao.getBalance(userId);
    }

    public List<Transaction> getTransactionByUserId(String userKey, int page, int size) {
        return transactionDao.getTransactionByUserId(userKey, page, size);
    }

    public List<Transaction> getTransactionInByUserId(String userKey) {
        return transactionDao.getTransactionInByUserId(userKey);
    }

    public List<Transaction> getTransactionOutByUserId(String userKey) {
        return transactionDao.getTransactionOutByUserId(userKey);
    }
}
