package com.selfsync.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.selfsync.model.Habit;
import com.selfsync.service.HabitService;

@RestController
@RequestMapping("/api/habits")
public class HabitController {
    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @GetMapping
    public List<Habit> getAllHabits() {
        return habitService.getAllHabits();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habit> getHabit(@PathVariable Long id) {
        return habitService.getHabit(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Habit createHabit(@RequestBody Habit habit) {
        habit.setCreatedAt(java.time.LocalDateTime.now());
        habit.setUpdatedAt(java.time.LocalDateTime.now());
        return habitService.saveHabit(habit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habit> updateHabit(@PathVariable Long id, @RequestBody Habit habit) {
        return habitService.getHabit(id)
                .map(existing -> {
                    habit.setId(id);
                    habit.setUpdatedAt(java.time.LocalDateTime.now());
                    return ResponseEntity.ok(habitService.saveHabit(habit));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable Long id) {
        habitService.deleteHabit(id);
        return ResponseEntity.noContent().build();
    }

    // Đánh dấu hoàn thành 1 ngày
    @PostMapping("/{id}/complete")
    public Habit completeDay(@PathVariable Long id, @RequestParam String date) {
        return habitService.addCompletedDay(id, date);
    }

    // Bỏ đánh dấu hoàn thành 1 ngày
    @DeleteMapping("/{id}/complete")
    public Habit uncompleteDay(@PathVariable Long id, @RequestParam String date) {
        return habitService.removeCompletedDay(id, date);
    }

    // Lấy lịch hoàn thành
    @GetMapping("/{id}/completed-days")
    public List<String> getCompletedDays(@PathVariable Long id) {
        return habitService.getHabit(id).map(Habit::getCompletedDays).orElse(List.of());
    }
}