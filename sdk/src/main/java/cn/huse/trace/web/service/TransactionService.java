package cn.huse.trace.web.service;

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

    public void addTransaction(Transaction transaction) throws DaoException {
        //构造Id
        transaction.setId(Transaction.TRANSACTION_FLAG + "_" + transaction.getInId() + "_" + transaction.getOutId() + "_" + Utils.getUUID());
        transactionDao.add(transaction);
    }

    public float getBalance(String userId) {
       return transactionDao.getBalance(userId);
    }

    public List<Transaction> getTransactionByUserId(String userKey) {
        return transactionDao.getTransactionByUserId(userKey);
    }

    public List<Transaction> getTransactionInByUserId(String userKey) {
        return transactionDao.getTransactionInByUserId(userKey);
    }

    public List<Transaction> getTransactionOutByUserId(String userKey) {
        return transactionDao.getTransactionOutByUserId(userKey);
    }
}
