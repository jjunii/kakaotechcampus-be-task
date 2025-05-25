package com.example.schedule.controller;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 생성: C, POST
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {

        return new ResponseEntity<>(scheduleService.saveSchedule(scheduleRequestDto), HttpStatus.CREATED);
    }

    // 전체 일정 조회: R(전체), GET
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findSchedules(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate updatedAt  // YYYY-MM-DD
    ) {
        return new ResponseEntity<>(scheduleService.findSchedules(author, updatedAt), HttpStatus.OK);
    }

    // 선택 일정 조회: R(단건), GET
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {

        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    // 선택 일정 수정: U(부분), PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestParam String password,
            @RequestBody ScheduleRequestDto scheduleRequestDto
    ) {
        return new ResponseEntity<>(scheduleService.updateSchedule(id, password, scheduleRequestDto.getTodo(), scheduleRequestDto.getAuthor()), HttpStatus.OK);
    }

    // 선택 일정 삭제: D, DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestParam String password
    ) {
        scheduleService.deleteSchedule(id, password);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
