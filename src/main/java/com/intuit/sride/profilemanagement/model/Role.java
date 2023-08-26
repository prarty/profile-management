package com.intuit.sride.profilemanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ROLES", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"NAME", "APPLICATION_NAME"})
})
@EqualsAndHashCode(of = {"name", "applicationName"}, callSuper = false)
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "APPLICATION_NAME")
    private String applicationName;
    @Column(name = "START_DATE")
    private LocalDate startDate;
    @Column(name = "END_DATE")
    private LocalDate endDate;
    @Column(name = "ENABLED")
    private Boolean enabled;

    @OneToOne(targetEntity = Permission.class,fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "permission_id")
    private Permission permissions;

}
