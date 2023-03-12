package com.example.demo.dao.param;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddParam {
    private String type;
    private String name;
    private String point;
    private String actors;
}
