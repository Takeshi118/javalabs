package com.example.labwork5.service;
import com.example.labwork5.model.Task;
import com.example.labwork5.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findByUserId(String userId) {
        return taskRepository.findByUserId(userId);
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }
}

