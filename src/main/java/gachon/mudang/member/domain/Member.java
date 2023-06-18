package gachon.mudang.member.domain;

import gachon.mudang.interest.domain.Interest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import gachon.mudang.product.domain.Product;
import gachon.mudang.product.domain.ProductStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "manner")
    private int mannerTemp = 0;

    @Column(name = "role")
    private MemberRole memberRole;

    @Column(name = "profile")
    private String profile;

    @OneToMany(mappedBy = "member")
    private List<Interest> interests = new ArrayList<>();

    @OneToMany(mappedBy = "seller")
    private List<Product> products = new ArrayList<>();

    // member info
    @Builder
    public Member(Long id, String email, String password, String name, String nickName, String phone, MemberRole memberRole) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.phone = phone;
        this.memberRole = memberRole;
        this.profile = MemberImageInit.INIT_URL;
    }

    /* Business Logic */

    // Update Nickname
    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

    // Update Profile
    public void updateProfile(String profile) {
        this.profile = profile;
    }

    // Encrypt member password
    public Member encryptPassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
        return this;
    }

    /* Query logic */

    // Extract only product information from the list of interests
    public List<Product> getProductByInterest(){
        return this.getInterests().stream()
                .map((item)-> item.getProduct())
                .collect(Collectors.toList());
    }

    //  Product extraction by interest list status
    public List<Product> getInterestStatus(ProductStatus status){
        if(status == null) return getProductByInterest();
        return interests.stream()
                .filter((item) -> item.getProduct().getStatus() == status)
                .map((item) -> item.getProduct())
                .collect(Collectors.toList());
    }

    // Inquiry of sales details by product
    public List<Product> getProductByStatus(ProductStatus status){
        if(status == null) return products;
        return products.stream()
                .filter((item)->item.getStatus() == status)
                .collect(Collectors.toList());
    }

}
