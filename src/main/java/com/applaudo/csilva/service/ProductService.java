package com.applaudo.csilva.service;

import com.applaudo.csilva.dto.ProductDto;

public interface ProductService {

    ProductDto getProductById(Integer id);

    Iterable<ProductDto> getAllProducts();

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto, Integer id);

    ProductDto deleteProduct(Integer id);

}
