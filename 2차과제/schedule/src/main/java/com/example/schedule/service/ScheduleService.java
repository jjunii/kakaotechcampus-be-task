package com.example.schedule.service;

import com.example.schedule.ScheduleApplication;
import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    // 일정 생성
    ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto);

    // 전체 일정 조회
    List<ScheduleResponseDto> findSchedules(String author, LocalDate updatedAt);

    // 선택 일정 조회
    ScheduleResponseDto findScheduleById(Long id);

    // 선택 일정 수정
    ScheduleResponseDto updateSchedule(Long id, String password, String todo, String author);

    // 선택 일정 삭제
    void deleteSchedule(Long id, String password);
}
