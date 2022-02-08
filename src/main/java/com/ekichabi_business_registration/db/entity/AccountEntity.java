package com.ekichabi_business_registration.db.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ACCOUNT")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ToString.Include
    private Long id;

    @ToString.Include
    private String name;

    private String password;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null ||
                Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        AccountEntity that = (AccountEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @ManyToMany(mappedBy = "owners")
    private Collection<BusinessEntity> ownedBusinesses;
}
