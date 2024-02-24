package com.jointpurchases.domain.product.service.impl;

import com.jointpurchases.domain.product.elasticsearch.document.ProductDocument;
import com.jointpurchases.domain.product.elasticsearch.document.ProductSortOption;
import com.jointpurchases.domain.product.exception.ProductException;
import com.jointpurchases.domain.product.model.dto.response.ProductListResponseDto;
import com.jointpurchases.domain.product.model.dto.response.ProductResponseDto;
import com.jointpurchases.domain.product.model.entity.Product;
import com.jointpurchases.domain.product.repository.ProductRepository;
import com.jointpurchases.domain.product.service.ProductReadService;
import com.jointpurchases.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.jointpurchases.global.exception.ErrorCode.PRODUCT_NOT_FOUND;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductReadServiceImpl implements ProductReadService {

    private final ProductRepository productRepository;
    private final RedisUtil redisUtil;
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public Page<ProductListResponseDto> getAllProduct(Pageable pageable) {
        Page<Product> productPage = productRepository.findAllProduct(pageable);
        return productPage.map(ProductListResponseDto::of);
    }

    @Override
    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findProductWithProductId(id)
                .orElseThrow(()-> new ProductException(PRODUCT_NOT_FOUND));
        return ProductResponseDto.of(product, redisUtil.getLikeCount(id));
    }

    @Override
    public Page<ProductListResponseDto> getSearchProduct(Pageable pageable, String category, String keyword, String sort) {
        Criteria criteria = createCriteria(category, keyword);

        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria, pageable)
                .addSort(ProductSortOption.from(sort).getSortOption());

        SearchHits<ProductDocument> searchHits = elasticsearchOperations.search(criteriaQuery, ProductDocument.class);
        List<ProductListResponseDto> content = convertToDtoList(searchHits);

        return new PageImpl<>(content, pageable, searchHits.getTotalHits());
    }

    private Criteria createCriteria(String category, String keyword) {
        Criteria criteria = new Criteria("productName").matches(keyword);

        if (category != null) {
            criteria = criteria.and("categoryName").is(category);
        }

        return criteria;
    }

    private List<ProductListResponseDto> convertToDtoList(SearchHits<ProductDocument> searchHits) {
        return searchHits.getSearchHits()
                .stream()
                .map(hit -> ProductListResponseDto.of(hit.getContent()))
                .toList();
    }
}