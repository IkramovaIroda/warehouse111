package com.project.warehouse.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttachmentDto implements Serializable {
    private final String name;
    private  final  Long size;
    private final String content_type;

}
