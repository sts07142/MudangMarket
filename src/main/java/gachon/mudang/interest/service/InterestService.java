package gachon.mudang.interest.service;

public interface InterestService {
    // Add a new interest to the interest list
    Long addInterestList(String email, Long productId);

    // Delete an interest from the interest list for a specific product
    void deleteInterestByProductList(String email, Long productId);

    // Check for duplicate interests for a member and a product
    void checkInterestDuplicate(Long memberId, Long productId);
}
