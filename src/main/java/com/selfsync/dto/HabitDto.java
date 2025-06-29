package com.selfsync.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HabitDto {
    Long id;
    String name;
    String description;
    List<String> completedDays;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
} 