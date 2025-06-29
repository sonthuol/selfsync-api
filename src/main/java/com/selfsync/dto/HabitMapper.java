package com.selfsync.dto;

import com.selfsync.entity.Habit;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class HabitMapper {
    public static HabitDto toDto(Habit habit) {
        return HabitDto.builder()
                .id(habit.getId())
                .name(habit.getName())
                .description(habit.getDescription())
                .completedDays(habit.getCompletedDays())
                .createdAt(habit.getCreatedAt())
                .updatedAt(habit.getUpdatedAt())
                .build();
    }

    public static Habit toEntity(HabitDto dto) {
        return Habit.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .completedDays(dto.getCompletedDays())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
} 