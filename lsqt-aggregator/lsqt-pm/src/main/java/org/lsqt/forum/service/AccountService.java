package org.lsqt.forum.service;

import org.lsqt.forum.domain.Account;
import org.lsqt.forum.persistence.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

  @Autowired
  private AccountMapper accountMapper;

  @Transactional
  public void insertAccount(Account account) {
    accountMapper.insertAccount(account);
  }

  @Transactional
  public void updateAccount(Account account) {
    accountMapper.updateAccount(account);
  }

}
