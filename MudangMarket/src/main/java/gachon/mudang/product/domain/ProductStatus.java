package gachon.mudang.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    TRADE("판매중"),
    COMPLETED("거래완료"),
    RESERVATION("예약중");

    private String value;

}
