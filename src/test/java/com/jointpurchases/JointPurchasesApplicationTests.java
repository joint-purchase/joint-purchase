package com.jointpurchases;

import com.jointpurchases.domain.review.model.entity.ReviewEntity;
import com.jointpurchases.domain.review.repository.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class JointPurchasesApplicationTests {

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
@DataJpaTest
@AutoConfigureTestDatabase
class ReviewRepositoryTest {

	@Autowired
	ReviewRepository reviewRepository;

	@Test
	@DisplayName("리뷰 만들기")
	void createReview(){
		LocalDateTime localDateTime = LocalDateTime.now();
        /*
        given
         */
		ReviewEntity reviewEntity1 = ReviewEntity.builder().
				title("hi1").
				contents("this is test1").
				rating(5).
				registerDate(localDateTime).
				modifiedDate(localDateTime).
				build();
		ReviewEntity reviewEntity2 = ReviewEntity.builder().
				title("hi2").
				contents("this is test2").
				rating(4).
				registerDate(localDateTime).
				modifiedDate(localDateTime).
				build();

        /*
        when
         */
		ReviewEntity result1 = reviewRepository.save(reviewEntity1);
		ReviewEntity result2 = reviewRepository.save(reviewEntity2);

        /*
        then
         */
		Assertions.assertThat(result1.getTitle()).isEqualTo(reviewEntity1.getTitle());
		Assertions.assertThat(result2.getTitle()).isEqualTo(reviewEntity2.getTitle());
	}
}


