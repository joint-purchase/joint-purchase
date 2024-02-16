package com.jointpurchases.domain.review;

import com.jointpurchases.domain.review.model.entity.ReviewEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class JointPurchasesApplicationReviewTests {

	@Test
	@DisplayName("리뷰가 생성 되는지 확인")
	void createReviewEntity() {
		LocalDateTime localDateTime = LocalDateTime.now();
		/*
		Given
		 */
		ReviewEntity reviewEntity = ReviewEntity.builder().
				title("hi").
				contents("this is test").
				rating(5).
				registerDate(localDateTime).
				modifiedDate(localDateTime).
				build();

		/*
		When,Then
		 */
		Assertions.assertThat(reviewEntity.getTitle()).isEqualTo("hi");
		Assertions.assertThat(reviewEntity.getContents()).isEqualTo("this is test");
		Assertions.assertThat(reviewEntity.getRating()).isEqualTo(5);
		Assertions.assertThat(reviewEntity.getRegisterDate()).isEqualTo(localDateTime);
		Assertions.assertThat(reviewEntity.getModifiedDate()).isEqualTo(localDateTime);
	}
}