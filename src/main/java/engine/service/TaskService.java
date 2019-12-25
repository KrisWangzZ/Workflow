package engine.service;

public interface TaskService {
    void claim(String taskId);
    void unclaim(String taskId);
    void complete(String taskId) throws Exception;
}
