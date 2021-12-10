package com.example.dictionary.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ApiModel("Счет клиента")
public class DictionaryAccountDto {
    @ApiModelProperty("Идентификатор счета")
    private Long id;

    @ApiModelProperty("Статус счета")
    private StatusAccountDto status;

    @ApiModelProperty("Банк в котором находится счет")
    private DictionaryBankDto bank;

    @ApiModelProperty("Номер счета")
    private Long accountNumber;

    @ApiModelProperty("Идентификатор клиента")
    private Long idClient;
}
