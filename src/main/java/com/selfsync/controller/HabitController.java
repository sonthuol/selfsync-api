package com.selfsync.controller;

import com.selfsync.dto.HabitDto;
import com.selfsync.dto.HabitMapper;
import com.selfsync.entity.Habit;
import com.selfsync.service.HabitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/habits")
public class HabitController {
    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @GetMapping
    public List<HabitDto> getAllHabits() {
        return habitService.getAllHabits().stream()
                .map(HabitMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitDto> getHabit(@PathVariable Long id) {
        return habitService.getHabit(id)
                .map(HabitMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public HabitDto createHabit(@RequestBody HabitDto habitDto) {
        Habit habit = HabitMapper.toEntity(habitDto);
        habit.setCreatedAt(java.time.LocalDateTime.now());
        habit.setUpdatedAt(java.time.LocalDateTime.now());
        return HabitMapper.toDto(habitService.saveHabit(habit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitDto> updateHabit(@PathVariable Long id, @RequestBody HabitDto habitDto) {
        return habitService.getHabit(id)
                .map(existing -> {
                    Habit habit = HabitMapper.toEntity(habitDto);
                    habit.setId(id);
                    habit.setUpdatedAt(java.time.LocalDateTime.now());
                    return ResponseEntity.ok(HabitMapper.toDto(habitService.saveHabit(habit)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable Long id) {
        habitService.deleteHabit(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/complete")
    public HabitDto completeDay(@PathVariable Long id, @RequestParam String date) {
        return HabitMapper.toDto(habitService.addCompletedDay(id, date));
    }

    @DeleteMapping("/{id}/complete")
    public HabitDto uncompleteDay(@PathVariable Long id, @RequestParam String date) {
        return HabitMapper.toDto(habitService.removeCompletedDay(id, date));
    }

    @GetMapping("/{id}/completed-days")
    public List<String> getCompletedDays(@PathVariable Long id) {
        return habitService.getHabit(id).map(Habit::getCompletedDays).orElse(List.of());
    }
}