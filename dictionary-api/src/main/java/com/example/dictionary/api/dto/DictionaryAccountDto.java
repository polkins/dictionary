package com.example.dictionary.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("Счет клиента")
public class DictionaryAccountDto {
    @ApiModelProperty("Идентификатор счета")
    private Long id;

    @ApiModelProperty("Статус счета")
    private StatusAccountDto accountStatus;

    @ApiModelProperty("Банк в котором находится счет")
    private DictionaryBankDto bank;

    @ApiModelProperty("Номер счета")
    private Long accountNumber;

    @ApiModelProperty("Идентификатор клиента")
    private Long clientId;
}
