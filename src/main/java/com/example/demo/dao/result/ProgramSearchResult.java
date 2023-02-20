package com.example.demo.dao.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProgramSearchResult extends ProgramResult {
    private Integer num;
}
