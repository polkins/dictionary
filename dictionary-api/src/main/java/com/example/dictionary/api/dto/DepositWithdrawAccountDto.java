package com.example.dictionary.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ApiModel("Пополнение или снятие со счета")
public class DepositWithdrawAccountDto {
    @ApiModelProperty("Номер счета")
    private String accountNumber;

    @ApiModelProperty("Сумма")
    private Double summ;
}
