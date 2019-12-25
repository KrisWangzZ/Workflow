package engine.bpmn.process.gateways;

import engine.Action;
import engine.State;
import engine.bpmn.core.common.SequenceFlow;

import java.util.List;

public class ExclusiveGateway extends GateWay {
    private SequenceFlow defaultSF;

    @Override
    public void act(int action) throws Exception {
        super.act(action);
        this.setSTATE(State.INVOKED);
        List<SequenceFlow> edges = this.getOutgoing();

        if (null == edges) {
            return;
        }

        boolean invokeFound = false;

        for (SequenceFlow edge : edges) {

            if (edge instanceof SequenceFlow) {

                // 如果前面已经有表达式计算为true的,则后面的都直接设置为忽略
                if (invokeFound) {
                    edge.act(Action.IGNORE);
                    continue;
                }
                edge.act(Action.INVOKE);

                // 碰到第一个为true的
                invokeFound = (State.INVOKED == edge.getSTATE());

            }
        }
    }
}
