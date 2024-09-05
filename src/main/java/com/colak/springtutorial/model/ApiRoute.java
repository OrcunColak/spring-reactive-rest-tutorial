package com.colak.springtutorial.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ApiRoute {
    private String id;
    private String path;
    private String serviceId;
    private String url;
}
