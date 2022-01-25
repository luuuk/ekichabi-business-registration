package com.ekichabi_business_registration.db.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "BUSINESS")
public class BusinessEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    // TODO add fields as defined in data model

    protected BusinessEntity() {}

    public BusinessEntity(String name){
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
