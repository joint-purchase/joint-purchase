package com.jointpurchases.domain.product.elasticsearch.repository;

import com.jointpurchases.domain.product.elasticsearch.document.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductDocumentRepository extends ElasticsearchRepository<ProductDocument, Long> {
}