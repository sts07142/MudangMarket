package gachon.mudang.interest.repository;

import gachon.mudang.interest.domain.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long> {
    // Method to query interest based on member ID and product ID
    Optional<Interest> findByMemberIdAndProductId(Long memberId, Long productId);
}
