package engine.core;

import java.util.Map;

public class ProcessManager {
    private Map<String, ProcessInstance> processes;

    public ProcessInstance getProcess(String id) {
        return processes.get(id);
    }

    public void setProcesses(Map<String, ProcessInstance> processes) {
        this.processes = processes;
    }
}
