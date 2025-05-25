package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto) {

        Schedule schedule = new Schedule(scheduleRequestDto.getTodo(), scheduleRequestDto.getAuthor(), scheduleRequestDto.getPassword());

        Long generatedId = scheduleRepository.saveSchedule(schedule);

        // id로 조회 결과 반환
        return new ScheduleResponseDto(scheduleRepository.findScheduleById(generatedId));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ScheduleResponseDto> findSchedules(String author, LocalDate updatedAt) {

        List<Schedule> schedules = scheduleRepository.findSchedules(author, updatedAt);

        List<ScheduleResponseDto> responseList = new ArrayList<>();
        for (Schedule schedule : schedules) {
            responseList.add(new ScheduleResponseDto(schedule));
        }
        return responseList;
    }

    @Transactional(readOnly = true)
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        return new ScheduleResponseDto(scheduleRepository.findScheduleById(id));
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String password, String todo, String author) {

        // id 존재 여부 확인 가능
        Schedule schedule = scheduleRepository.findScheduleById(id);

        // password 불일치
        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password is incorrect.");
        }

        // 수정 내용 없는 경우
        if (todo == null && author == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Content is required.");
        }

        scheduleRepository.updateSchedule(id, todo, author);

        // 수정 후 id로 조회 결과 반환
        return new ScheduleResponseDto(scheduleRepository.findScheduleById(id));
    }

    @Transactional
    @Override
    public void deleteSchedule(Long id, String password) {
        // id 존재 여부 확인 가능
        Schedule schedule = scheduleRepository.findScheduleById(id);

        // password 불일치
        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password is incorrect.");
        }

        scheduleRepository.deleteSchedule(id);
    }
}
