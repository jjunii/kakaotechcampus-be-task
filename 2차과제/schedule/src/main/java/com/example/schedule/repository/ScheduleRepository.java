package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {

    // 일정 생성
    Long saveSchedule(Schedule schedule);

    // 전체 일정 조회
    List<Schedule> findSchedules(String author, LocalDate updatedAt);

    // 선택 일정 조회
    Schedule findScheduleById(Long id);

    // 선택 일정 수정
    void updateSchedule(Long id, String todo, String author);

    // 선택 일정 삭제
    void deleteSchedule(Long id);
}
