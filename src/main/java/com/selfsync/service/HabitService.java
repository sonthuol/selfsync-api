package com.selfsync.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.selfsync.entity.Habit;
import com.selfsync.repository.HabitRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HabitService {
    HabitRepository habitRepository;

    public List<Habit> getAllHabits() {
        return habitRepository.findAll();
    }

    public Optional<Habit> getHabit(Long id) {
        return habitRepository.findById(id);
    }

    public Habit saveHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    public void deleteHabit(Long id) {
        habitRepository.deleteById(id);
    }

    // Thêm completed day
    public Habit addCompletedDay(Long id, String date) {
        Habit habit = habitRepository.findById(id).orElseThrow();
        if (habit.getCompletedDays() == null) {
            habit.setCompletedDays(new ArrayList<>());
        }
        if (!habit.getCompletedDays().contains(date)) {
            habit.getCompletedDays().add(date);
            habit.setUpdatedAt(LocalDateTime.now());
            habitRepository.save(habit);
        }
        return habit;
    }

    // Bỏ completed day
    public Habit removeCompletedDay(Long id, String date) {
        Habit habit = habitRepository.findById(id).orElseThrow();
        if (habit.getCompletedDays() != null) {
            habit.getCompletedDays().remove(date);
        }
        habit.setUpdatedAt(java.time.LocalDateTime.now());
        habitRepository.save(habit);
        return habit;
    }
}