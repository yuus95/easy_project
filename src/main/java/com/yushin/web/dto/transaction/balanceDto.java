package com.yushin.web.dto.transaction;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class balanceDto {
    private BigDecimal payment; // 결제
    private BigDecimal deposit; // 입금
    private BigDecimal transfer; // 이체

}
