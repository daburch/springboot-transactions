package com.daburch.springboot.transactions.dao.impl;

import com.daburch.springboot.transactions.dao.SequenceDao;
import com.daburch.springboot.transactions.exception.SequenceException;
import com.daburch.springboot.transactions.model.SequenceId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@Profile("mongo")
public class SequenceDaoImpl implements SequenceDao {

    private MongoOperations mongoOperation;

    @Autowired
    public SequenceDaoImpl(MongoOperations mongoOperation) {
        this.mongoOperation = mongoOperation;
    }

    @Override
    public int getNextSequenceId(String key) throws SequenceException {
        //get sequence id
        Query query = new Query(Criteria.where("_id").is(key));

        //increase sequence id by 1
        Update update = new Update();
        update.inc("seq", 1);

        //return new increased id
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        //this is the magic happened.
        SequenceId seqId =
                mongoOperation.findAndModify(query, update, options, SequenceId.class);

        //if no id, throws SequenceException
        //optional, just a way to tell user when the sequence id is failed to generate.
        if (seqId == null) {
            throw new SequenceException("Unable to get sequence id for key : " + key);
        }

        return seqId.getSeq();
    }
}
