package com.jointpurchases.Review;

import com.jointpurchases.domain.review.model.dto.CreateReviewDto;
import com.jointpurchases.domain.review.model.dto.ModifyReviewDto;
import com.jointpurchases.domain.review.model.entity.ReviewEntity;
import com.jointpurchases.domain.review.model.entity.ReviewImageEntity;
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


	@InjectMocks
	private ReviewService reviewService;

	@Test
	@DisplayName("리뷰 작성")
	@Transactional
	@Disabled
	void createReview() throws IOException {

	}
	@Test
	@DisplayName("리뷰 수정")
	@Transactional
	@Disabled
	void modifyReview() throws IOException {

	}
}
