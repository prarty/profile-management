package com.intuit.sride.profilemanagement.model;

import com.google.common.collect.Sets;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "PERMISSIONS")
public class Permission implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "INT(11)")
    private Long id;
    @Column(name = "PERMISSION_NAME")
    private String name;
    @Column(name = "PERMISSION_DESC")
    private String description;

    @ManyToMany(targetEntity = Resource.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "PERMISSION_RESOURCES",
            joinColumns = { @JoinColumn(name = "permission_id", columnDefinition = "INT(11)") },
            inverseJoinColumns = { @JoinColumn(name = "resource_id", columnDefinition = "INT(11)") })
    private Set<Resource> resources;

}
