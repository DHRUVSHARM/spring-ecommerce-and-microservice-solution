package com.dhruv.ecommerce.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    // spring data jpa translates function name to sql query
    // like : SELECT * FROM Product WHERE id IN (:ids) ORDER BY id;
    List<Product> findAllByIdInOrderById(List<Integer> ids);
}
