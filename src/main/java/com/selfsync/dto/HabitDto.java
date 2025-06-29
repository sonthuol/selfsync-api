package com.selfsync.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitDto {
    private Long id;
    private String name;
    private String description;
    private List<String> completedDays;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 