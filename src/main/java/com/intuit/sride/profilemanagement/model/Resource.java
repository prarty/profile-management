package com.intuit.sride.profilemanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "RESOURCES")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"resourceId"}, callSuper = false)
public class Resource implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "INT(11)")
    private Long id;
    @Column(name = "RESOURCE_ID")
    private String resourceId;
    @Column(name = "RESOURCE_DESC")
    private String description;

}
