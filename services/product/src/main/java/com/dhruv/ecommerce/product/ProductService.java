package com.dhruv.ecommerce.product;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    private final ProductMapper mapper;


    public Integer createProduct(@Valid ProductRequest request) {
        // gives a built product from the product request
        var product = mapper.toProduct(request);
        // save it in the repo and return the create id as well
        return repository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        // product purchase happens from a wishlist
        var productIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        var storedProducts = repository.findAllByIdInOrderById(productIds);
        // we return the ids of the products that are found in the db

        /*
        if(productIds.size() != storedProducts.size()) {
            // all the products requested are not in the db

        }

         */

        return null;
    }


    public ProductResponse findById(Integer productId) {
        // this method will help you find a product by id
        return repository.findById(productId)
                .map(mapper :: toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID : " + productId));
    }

    public List<ProductResponse> findAll() {
        // finds all the products ( you might need pagination )
        // collectors helps in the reduction process
        return repository.findAll()
                .stream()
                .map(mapper :: toProductResponse)
                .collect(Collectors.toList());
    }
}
