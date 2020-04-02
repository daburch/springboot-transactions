package com.daburch.springboot.transactions.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class is used for creating and storing an auto-incrementing ID for the MongoTransactionRepository
 */
@Document("sequence")
public class SequenceId {

    @Id
    private String id;

    private int seq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
