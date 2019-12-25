package engine.service;

import java.util.Map;

public interface FormService {
    void submitTaskFormData(String taskId, Map<String,?> data) throws Exception;
}
