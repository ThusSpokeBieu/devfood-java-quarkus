package com.github.gmessiasc.devfood.register.domain.Restaurants;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.github.gmessiasc.devfood.register.domain.Localizations.Localization;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "entity_restaurant", 
        uniqueConstraints = 
            @UniqueConstraint(columnNames = {"cnpj", "name"}) )
public class Restaurant extends PanacheEntityBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String owner;

    public String cnpj;

    public String name; 

    @OneToOne(cascade = CascadeType.ALL)
    public Localization localization;

    @CreationTimestamp
    public Date creationDate;

    @UpdateTimestamp
    public Date updateDate;
}
