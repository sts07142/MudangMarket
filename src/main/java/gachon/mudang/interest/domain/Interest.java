package gachon.mudang.interest.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import gachon.mudang.member.domain.Member;
import gachon.mudang.product.domain.Product;

import javax.persistence.*;

@Entity
@Table(name = "interest")
@Getter
@NoArgsConstructor
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;  // Unique identifier of the interest

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  // Reference to the member who has the interest

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;  // Reference to the product that is of interest

    @Builder
    public Interest(Member member, Product product) {
        this.member = member;
        this.product = product;
        product.addInterestCount();  // Increment the interest count of the product
        member.getInterests().add(this);  // Add this interest to the member's list of interests
    }

    public void reduceProductInterestCount(){
        product.reduceInterestCount();  // Decrement the interest count of the product
        member.getInterests().remove(this);  // Remove this interest from the member's list of interests
    }
}
