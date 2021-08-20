package com.yushin.web.dto.transaction;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BalanceDto {
    private BigDecimal deposit; // 입금
    private BigDecimal minus_money; // 결제


}
