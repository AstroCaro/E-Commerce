package com.applaudo.csilva.service.impl;

import com.applaudo.csilva.dto.ProductDto;
import com.applaudo.csilva.exception.ResourceFoundException;
import com.applaudo.csilva.exception.ResourceNotFoundException;
import com.applaudo.csilva.model.Product;
import com.applaudo.csilva.repository.ProductRepository;
import com.applaudo.csilva.service.ProductService;
import com.applaudo.csilva.utils.ObjectMapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductDto getProductById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            return ObjectMapperUtils.map(product.get(), ProductDto.class);
        }
        throw new ResourceNotFoundException("Product not found with the given id");
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ObjectMapperUtils.mapAll(products, ProductDto.class);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Optional<Product> productFound = productRepository.findByName(productDto.getName());
        if (!productFound.isPresent()) {
            Product productCreated = productRepository.save(ObjectMapperUtils.map(productDto, Product.class));
            return ObjectMapperUtils.map(productCreated, ProductDto.class);
        }
        throw new ResourceFoundException("Product already exits with the given id");
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Integer id) {
        Optional<Product> productFound = productRepository.findById(id);
        if(!productFound.isPresent()) {
            throw new ResourceNotFoundException("Product not found with the given id");
        }
        if(productRepository.findByName(productDto.getName()).isPresent()) {
            throw new ResourceFoundException("Product already exits with the given name");
        }
        productFound.get().setName(productDto.getName());
        productFound.get().setStock(productDto.getStock());
        productFound.get().setPrice(productDto.getPrice());
        return ObjectMapperUtils.map(productRepository.save(productFound.get()), ProductDto.class);
    }

    @Override
    public ProductDto deleteProduct(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            ProductDto productDto = ObjectMapperUtils.map(product, ProductDto.class);
            productRepository.deleteById(id);
            return productDto;
        }
        throw new ResourceNotFoundException("Product not found with the given id");
    }
}
