package com.example.dictionary.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ApiModel("Перевод со счета на счет")
public class TransferAccountDto {
    @ApiModelProperty("Номер счета с которго необходимо снять сумму")
    private String withdrawFromAccountNumber;

    @ApiModelProperty("Номер счета на который нужно перевести сумму")
    private String depositIntoAccountNumber;

    @ApiModelProperty("Сумма")
    private Double summ;
}
