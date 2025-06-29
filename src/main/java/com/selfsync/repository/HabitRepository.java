package com.selfsync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selfsync.model.Habit;

public interface HabitRepository extends JpaRepository<Habit, Long> {
}