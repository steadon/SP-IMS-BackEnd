package com.example.demo.dao.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProgramResultList {
    List<ProgramResult> programResults;
}
