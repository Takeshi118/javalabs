package com.example.labwork5.controller;
import com.example.labwork5.model.Task;
import com.example.labwork5.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public String listTasks(Model model, Principal principal) {
        String userId = principal.getName();
        model.addAttribute("tasks", taskService.findByUserId(userId));
        return "tasks";
    }

    @GetMapping("/add")
    public String showAddTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "add_task";
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task, Principal principal) {
        task.setUserId(principal.getName());
        taskService.save(task);
        return "redirect:/tasks";
    }
}

