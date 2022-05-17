package com.example.dictionary.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("Счет клиента")
public class DictionaryAccountDto {
    @ApiModelProperty("Идентификатор счета")
    private Long id;

    @ApiModelProperty("Номер счета")
    private String accountNumber;

    @ApiModelProperty("Идентификатор клиента")
    private Long clientId;

    @ApiModelProperty("Идентификатор банка")
    private Long bankId;

    @ApiModelProperty("Баланс")
    private Double balance;
}
