package com.epam.esm.utils;

import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;

@Component
public class Paginator {

    private final static int resultPerPage = 10;

    private Paginator() {
    }

    public void paginateQuery (int currentPage, TypedQuery <?> typedQuery) {
        typedQuery.setFirstResult((currentPage - 1) * resultPerPage);
        typedQuery.setMaxResults(resultPerPage);
    }
}
