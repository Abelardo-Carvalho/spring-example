package com.example.springboot.service;

import com.example.springboot.dtos.ProductModelDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    ProductRepository productRepository;

    public ResponseEntity<ProductModel> saveProductsService (ProductModelDto productModelDto){
        var productModel = new ProductModel(); //instacia ProductModel
        BeanUtils.copyProperties(productModelDto, productModel);//copia os dados do dto para o model
        productRepository.save(productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel)); //O status retorna se deu certo a operação no postman.
        // O body é o que retorna no body do postman. O metodo save não existe no ProductRepository, mas extende do JPA.
    }


}
