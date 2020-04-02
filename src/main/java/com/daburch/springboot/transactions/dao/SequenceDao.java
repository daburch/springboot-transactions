package com.daburch.springboot.transactions.dao;

import com.daburch.springboot.transactions.exception.SequenceException;

public interface SequenceDao {

    int getNextSequenceId(String key) throws SequenceException;

}
