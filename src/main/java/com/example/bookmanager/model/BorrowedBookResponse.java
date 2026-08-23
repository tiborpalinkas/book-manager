package com.example.bookmanager.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowedBookResponse {

    private Integer id;
    private String title;
    private String authorName;
    private String isbn;
    private String owner;
    private byte[] cover;
    private double rate;
    private boolean returned;
    private boolean returnApproved;
}
