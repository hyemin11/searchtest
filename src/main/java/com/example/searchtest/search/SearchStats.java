package com.example.searchtest.search;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class SearchStats {

    @Id
    private String keyword;

    @Column
    private long count;
}
