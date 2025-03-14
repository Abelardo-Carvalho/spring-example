package com.example.springboot.service;

import com.example.springboot.dtos.ProductModelDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {


    private final ProductRepository productRepository;

    public ProductService (ProductRepository productRepository){
        this.productRepository = productRepository;
    } //ESTE CONSTRUTOR INJETA A DEPENDÊNCIA DO PRODUCT REPOSITORY AUTOMATICAMENTE, ATRAVÉS DO SPRING, ELIMINANDO A NECESSIDADE DE UMA O @AUTOWIRED. SEM A INJEÇÃO DO RESPOSITORY DÁ ERRO DE REPOSITORY NULL

    public ResponseEntity<ProductModel> saveProductsService (ProductModelDto productModelDto){
        var productModel = new ProductModel(); //instacia ProductModel
        BeanUtils.copyProperties(productModelDto, productModel);//copia os dados do dto para o model
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel)); //O status retorna se deu certo a operação no postman.
        // O body é o que retorna no body do postman. O metodo save não existe no ProductRepository, mas extende do JPA.
    }

    public ResponseEntity<List<ProductModel>> getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    public ResponseEntity<Object> getOneProduct(@PathVariable(value="id") UUID id) {
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");

        }
        return ResponseEntity.status(HttpStatus.OK).body(productO.get());
    }

    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,@RequestBody @Valid ProductModelDto productRecordDto){
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        var productModel = productO.get(); // atribui ao productModel o mesmo UUID que foi pesquisado para salvar os dados com o mesmo ID
        BeanUtils.copyProperties(productRecordDto, productModel); // copia o name e value para o productModel. O UUID já foi inserido na linha anterior
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id){
        Optional <ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productRepository.delete(productO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted sucefull");
    }

}
