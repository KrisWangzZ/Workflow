package engine.service;

import engine.bpmn.core.common.ConditionExpression;
import engine.core.ProcessDefinition;
import org.springframework.core.io.Resource;

public interface XmlParserService {
    ProcessDefinition parse(Resource resource) throws Exception;
}
