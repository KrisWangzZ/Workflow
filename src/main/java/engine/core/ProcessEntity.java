package engine.core;

import engine.Action;
import engine.State;
import engine.bpmn.core.common.FlowNode;
import engine.bpmn.core.common.SequenceFlow;
import engine.bpmn.process.activities.UserTask;
import engine.service.impl.MemRuntimeService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.*;

public class ProcessEntity implements ProcessInstance {

    //Deployment => ProcessDefinition
    //ProcessEntity => Deployment => ProcessDefinition

    public ProcessEntity(Deployment deployment) {
        this.deployment = deployment;
    }

    private Map<String, FlowNode> candidates = new HashMap<String, FlowNode>();

    private Deployment deployment;


    public String getDeploymentId() {
        return deployment.getId();
    }

    public Map<String, FlowNode> getCandidates() {
        return candidates;
    }

    public FlowNode removeCandidate(FlowNode flowNode) {
        return candidates.remove(flowNode.getId());
    }

    public void addCandidate(FlowNode flowNode) {
        candidates.put(flowNode.getId(),flowNode);
    }

    public String getId() {
        return deployment.getProcessDefinition().getProcess().getId();
    }

    public boolean isSuspended() {
        return false;
    }

    public boolean isEnded() {
        return false;
    }

    public String getParentId() {
        return null;
    }

    public String getName() {
        return  deployment.getProcessDefinition().getProcess().getName();
    }

    private FlowNodeExecution getNextExecution() {

        Collection<FlowNode> nodes = candidates.values();

        FlowNodeExecution execution = null;

        for (FlowNode node : nodes) {

            // 判断当前节点是否是UseTask
            if (node instanceof UserTask && node.getSTATE() != State.PRE_INVOKED) {
                if(node.getSTATE() == State.INIT ) MemRuntimeService.generateRecords(node,0);
                else if(node.getSTATE() == State.INVOKED )
                    node.setSTATE(State.INIT);
                continue;
            }

            List<SequenceFlow> SequenceFlows = node.getIncoming();

            // 1)如果没有边,当然可以执行
            if (null == SequenceFlows || 0 == SequenceFlows.size()) {
                execution = new FlowNodeExecution();
                execution.setFlowNode(node);
                execution.setAction(Action.INVOKE);
                break;
            }

            // 2)聚合边的状态
            int state = 0;
            for (SequenceFlow SequenceFlow : SequenceFlows) {
                state |= SequenceFlow.getSTATE();
            }

            // 如果存在INIT的边,肯定不会被执行
            int newState = state & State.INIT;
            if (0 != newState) {
                // 继续判断下一个节点
                continue;
            }

            // 既然所有的边都已经执行过了,那么就可以判断当前节点要执行的动作
            int invokedState = State.INVOKED & state;
            execution = new FlowNodeExecution();
            execution.setFlowNode(node);
            execution.setAction(0 != invokedState ? Action.INVOKE : Action.IGNORE);
            break;

        }
        if(execution!=null)
            //生成表单
            MemRuntimeService.generateRecords(execution.getFlowNode(),execution.getFlowNode() instanceof UserTask ? 1 : 0);
        return execution;
    }

    public void run() throws Exception {

        //自动化执行
        runExecution();

    }

    private void runExecution() throws Exception {

        FlowNodeExecution flowNodeExecution = null;

        while (null != (flowNodeExecution = getNextExecution())){

            FlowNode flowNode = (FlowNode)flowNodeExecution.getFlowNode();

            this.removeCandidate(flowNode);

            if (Action.INVOKE == flowNodeExecution.getAction()) {
                flowNode.act(Action.INVOKE);
            } else {
                flowNode.act(Action.IGNORE);
            }

            // 补充新节点
            List<SequenceFlow> SequenceFlows = flowNode.getOutgoing();
            for (SequenceFlow SequenceFlow : SequenceFlows) {
                FlowNode targetNode = SequenceFlow.getTargetNode();
                // 重复的会覆盖,因为内部用了hashmap,没有关系
                if(SequenceFlow.getSTATE() == State.INVOKED) this.addCandidate(targetNode);
            }

            MemRuntimeService.generateRecords(flowNodeExecution.getFlowNode(),1);

        }
    }
}
