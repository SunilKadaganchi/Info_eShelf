package com.eShelf.info.e.library.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CategoryResponseDto {
    private UUID id;
    private String categoryName;
}
