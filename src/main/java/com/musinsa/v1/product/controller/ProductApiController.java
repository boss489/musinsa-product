package com.musinsa.v1.product.controller;

import com.musinsa.v1.product.model.dto.CategoryPriceResponse;
import com.musinsa.v1.product.model.dto.LowestPriceResponse;
import com.musinsa.v1.product.model.dto.LowestPriceWrapperResponse;
import com.musinsa.v1.product.model.dto.ProductRequest;
import com.musinsa.v1.product.service.ProductService;
import com.musinsa.v1.product.model.dto.ProductResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product API", description = "상품 관리 api")
public class ProductApiController {

	private final ProductService productService;

	@Operation(summary = "상품 조회")
	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAllProducts() {
		List<ProductResponse> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}

	@Operation(summary = "카테고리 별 최저가")
	@GetMapping("/lowest-prices")
	public ResponseEntity<List<LowestPriceResponse>> getLowestPrices() {
		List<LowestPriceResponse> response = productService.calculateLowestPrices();
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "최저가 판매 브랜드 정보")
	@GetMapping("/lowest-total")
	public ResponseEntity<LowestPriceWrapperResponse> getLowestTotal() {
		LowestPriceWrapperResponse response = productService.calculateLowestTotal();
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "카테고리 명 별 상품 가격 정보 조회")
	@GetMapping("/category/prices")
	public ResponseEntity<CategoryPriceResponse> getCategoryPrices(@RequestParam String categoryName) {
		CategoryPriceResponse response = productService.getCategoryPrices(categoryName);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "상품 추가")
	@PostMapping
	public ResponseEntity<String> addProduct(@Valid @RequestBody ProductRequest productRequest) {
		productService.saveProduct(productRequest);
		return ResponseEntity.ok("Product added successfully");
	}

	@Operation(summary = "상품 수정")
	@PutMapping("/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {
		productService.updateProduct(id, productRequest);
		return ResponseEntity.ok("Product updated successfully");
	}

	@Operation(summary = "상품 삭제")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.ok("Product deleted successfully");
	}
}
