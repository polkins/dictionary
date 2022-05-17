package com.example.dictionary.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
