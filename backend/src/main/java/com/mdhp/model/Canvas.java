package com.mdhp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "canvas")
@Data
@Getter
@Setter
public class Canvas {
    @Id
    private String uuid;
    private String area;
    private String createdOn;
    private int days;
    private String url;
    private String imageUrl;
    private String hoverText;
    private boolean active;
}
