package com.example.dictionary.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ApiModel("Создать счет для клиента")
public class CreateAccountDto {
    @ApiModelProperty("Инн")
    private String inn;

    @ApiModelProperty("БИК")
    private String bic;
}
