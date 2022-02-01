package com.ekichabi_business_registration.db.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "BUSINESS")
public class BusinessEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ToString.Include
    private Long id;

    @ToString.Include
    private String name;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
    private CategoryEntity category;

    @ManyToMany
    @JoinTable(name="BUSINESS_TO_SUBCATEGORY",
            joinColumns = @JoinColumn(name = "BUSINESS_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "SUBCATEGORY_ID", referencedColumnName = "ID"))
    @Singular
    private List<SubcategoryEntity> subcategories;

    @ManyToOne
    @JoinColumn(name = "SUBVILLAGE_ID")
    private SubvillageEntity subvillage;

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

}
