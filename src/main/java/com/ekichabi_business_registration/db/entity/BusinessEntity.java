package com.ekichabi_business_registration.db.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "BUSINESS")
public class BusinessEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    private CategoryEntity category;

    @ManyToMany
    @JoinTable(name="BUSINESS_TO_SUBCATEGORY")
    private List<SubcategoryEntity> subcategory;

    @ManyToOne
    private DistrictEntity district;

    @ManyToOne
    private VillageEntity village;

    @ManyToOne
    private SubvillageEntity subvillage;

    public BusinessEntity(String name){
        this.name = name;
    }

    protected BusinessEntity() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null ||
                Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        BusinessEntity that = (BusinessEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // TODO add fields as defined in data model
}
