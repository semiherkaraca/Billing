package com.billing.model;

import com.billing.dto.UserDto;
import com.billing.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
//@EqualsAndHashCode(callSuper = true, exclude = {"roles"})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "USER", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"FIRST_NAME", "LAST_NAME", "EMAIL"},
                name = "unique_key_user"
        )
})
public class User extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstname;

    @Column(name = "LAST_NAME")
    private String lastname;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;

    @Override
    public Long getId() {
        return this.id;
    }

    public static User generateUser(UserDto userDto) {
        return User.builder()
                .firstname(userDto.getName())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .createdDate(DateUtils.getSimpleCurrentDate())
                .build();
    }
}
