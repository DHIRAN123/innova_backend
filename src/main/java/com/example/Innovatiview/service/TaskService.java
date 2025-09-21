package com.example.Innovatiview.service;

import com.example.Innovatiview.DTO.TaskEntity;
import com.example.Innovatiview.DTO.TaskResponse;
import com.example.Innovatiview.DTO.UserEntity;
import com.example.Innovatiview.repository.TaskRepository;
import com.example.Innovatiview.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskResponse handleCommand(String userId, String command) {
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return new TaskResponse("❌ User not found", userId, null);
        }

        UserEntity user = userOpt.get();
        String lowerCmd = command.toLowerCase();

        switch (lowerCmd) {
            case "create a new task":
                TaskEntity newTask = new TaskEntity("Task for user " + userId, user);
                taskRepository.save(newTask);
                return new TaskResponse("✅ Task Created", userId, newTask.getDescription());

            case "proceed to last task":
                TaskEntity lastTask = taskRepository.findTopByUserOrderByIdDesc(user);
                if (lastTask != null) {
                    return new TaskResponse("➡️ Proceeding to last task", userId, lastTask.getDescription());
                } else {
                    return new TaskResponse("❌ No tasks found", userId, null);
                }

            default:
                return new TaskResponse("❌ Invalid command", userId, null);
        }
    }

    public TaskResponse chooseOption(String userId, String option) {
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return new TaskResponse("❌ User not found", userId, null);
        }

        UserEntity user = userOpt.get();
        TaskEntity lastTask = taskRepository.findTopByUserOrderByIdDesc(user);

        if (lastTask == null) {
            return new TaskResponse("❌ No task found for this user", userId, null);
        }

        // save option (BIO, FRISKING, CSO)
        lastTask.setOptionChosen(option.toUpperCase());
        taskRepository.save(lastTask);

        return new TaskResponse("✅ Option chosen: " + option, userId, lastTask.getDescription());
    }

}
