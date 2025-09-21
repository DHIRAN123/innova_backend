package com.example.Innovatiview.controller;

import com.example.Innovatiview.DTO.TaskResponse;
import com.example.Innovatiview.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * User chooses command after login:
     * - "create a new task"
     * - "proceed to last task"
     */
    @PostMapping("/handle")
    public TaskResponse handleTask(
            @RequestParam String userId,
            @RequestParam String command) {
        return taskService.handleCommand(userId, command);
    }

    @PostMapping("/choose-option")
    public TaskResponse chooseOption(
            @RequestParam String userId,
            @RequestParam String option) {
        return taskService.chooseOption(userId, option);
    }

}
