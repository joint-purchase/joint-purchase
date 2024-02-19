package com.jointpurchases.Review;

import com.jointpurchases.domain.review.model.dto.CreateReviewDto;
import com.jointpurchases.domain.review.model.dto.ModifyReviewDto;
import com.jointpurchases.domain.review.model.entity.ProductEntity;
import com.jointpurchases.domain.review.model.entity.ReviewEntity;
import com.jointpurchases.domain.review.model.entity.ReviewImageEntity;
import com.jointpurchases.domain.review.repository.ProductRepository;
import com.jointpurchases.domain.review.repository.ReviewImageRepository;
import com.jointpurchases.domain.review.repository.ReviewRepository;
import com.jointpurchases.domain.review.service.ReviewService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

	@Mock
	private ReviewRepository reviewRepository;

	@Mock
	private ReviewImageRepository reviewImageRepository;

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ReviewService reviewService;

	@Test
	@DisplayName("리뷰 작성")
	@Transactional
	void createReview() throws IOException {
		/*
		Given
		 */
		ProductEntity product = new ProductEntity(1);
		ReviewEntity review = ReviewEntity.builder().
				product(product).
				title("title").
				contents("contents").
				rating(5).
				build();

		/*
		When
		 */
		CreateReviewDto.Response response = new CreateReviewDto.Response();
		response = reviewService.createReview(product.getId(), review.getTitle(), review.getContents(), review.getRating(), null);

		/*
		Then
		 */
		ArrayList<ReviewImageEntity> list = new ArrayList();

		assertThat(review.getProduct().getId()).isEqualTo(response.getProductId());
		assertThat(review.getTitle()).isEqualTo(response.getTitle());
		assertThat(review.getContents()).isEqualTo(response.getContents());
		assertThat(review.getRating()).isEqualTo(response.getRating());
		assertThat(list).isEqualTo(response.getFilePaths());

	}
	@Test
	@DisplayName("리뷰 수정")
	@Transactional
	@Disabled
	void modifyReview() throws IOException {
		/*
		Given
		 */
		ReviewEntity review = reviewRepository.save(ReviewEntity.builder().
				title("title").
				contents("contents").
				rating(3).
				registerDate(LocalDateTime.of(2024, 2, 16, 0, 0, 0)).
				build());

		String title = "modifytitle";
		String contents = "modifycontents";
		int rating = 5;

		/*
		When
		 */
		ModifyReviewDto.Response response = reviewService.modifyReview(review.getId(), title, contents, rating,null );

		/*
		Then
		 */
		ArrayList<ReviewImageEntity> list = new ArrayList();

		assertThat(title).isEqualTo(response.getTitle());
		assertThat(contents).isEqualTo(response.getContents());
		assertThat(rating).isEqualTo(response.getRating());
		assertThat(LocalDateTime.of(2024, 2, 16, 0, 0, 0)).isEqualTo(response.getRegisterDate());
		assertThat(list).isEqualTo(response.getFilePaths());

	}
}
