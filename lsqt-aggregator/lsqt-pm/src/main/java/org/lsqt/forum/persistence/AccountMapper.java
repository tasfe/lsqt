package org.lsqt.forum.persistence;

import org.lsqt.forum.domain.Account;


public interface AccountMapper {
  void insertAccount(Account account);
  void updateAccount(Account account);
  
}
