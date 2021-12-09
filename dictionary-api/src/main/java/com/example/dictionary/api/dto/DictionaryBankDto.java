package com.example.dictionary.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class DictionaryBankDto implements Serializable {
    @ApiModelProperty("Идентификатор Банка")
    private Long id;

    @ApiModelProperty("Наименование Банка")
    private String name;

    @ApiModelProperty("Адресс Банка")
    private String address;

    @ApiModelProperty("БИК Банка")
    private String bic;
}
