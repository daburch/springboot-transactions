package com.daburch.springboot.transactions.model;

import com.daburch.springboot.transactions.exception.ValidationException;
import org.springframework.util.StringUtils;

import javax.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String description;
    private float amount;
    private String category;
    private Date date;

    public Transaction() {
    }

    public Transaction(long id, String description, float amount, String category, Date date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public void validate() throws ValidationException {
        if (id != 0) {
            throw new ValidationException("Transaction ID should not be set. This will be handled by the database");
        }

        // defaults
        if (description == null) description = "";
        if (category == null) category = "OTHER";
        if (date == null) date = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id &&
                Float.compare(that.amount, amount) == 0 &&
                Objects.equals(description, that.description) &&
                Objects.equals(category, that.category) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, amount, category, date);
    }
}
