package com.example.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleRequestDto {

    // id, createdAt, updatedAt 자동 생성
    private String todo;
    private String author;
    private String password;
}
