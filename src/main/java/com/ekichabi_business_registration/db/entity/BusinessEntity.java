package com.ekichabi_business_registration.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ToString.Include
    @JsonIgnore
    private Long id;

    @ToString.Include
    private String name;

    @ManyToOne()
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
    private CategoryEntity category;

    @ManyToMany()
    @JoinTable(name = "BUSINESS_TO_SUBCATEGORY",
            joinColumns = @JoinColumn(name = "BUSINESS_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "SUBCATEGORY_ID", referencedColumnName = "ID"))
    @Builder.Default
    private List<SubcategoryEntity> subcategories = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "SUBVILLAGE_ID")
    private SubvillageEntity subvillage;

    @ToString.Include
    @Column(name = "COORDINATES")
    private String coordinates;

    @ToString.Include
    @Column(name = "VERIFIED")
    @Type(type = "numeric_boolean")
    private Boolean verified;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "BUSINESS_ACCOUNT",
            joinColumns = @JoinColumn(name = "BUSINESS_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID"))
    @Builder.Default
    private List<AccountEntity> owners = new ArrayList<>();

    @ElementCollection
    @Builder.Default
    private List<String> phoneNumbers = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
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
