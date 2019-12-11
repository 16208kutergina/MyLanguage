package expressions;

import expressions.baseExpressions.AbstractExpression;
import org.objectweb.asm.MethodVisitor;
import org.parboiled.Node;
import org.parboiled.buffers.InputBuffer;

import java.util.HashMap;
import java.util.List;

public class Program implements AbstractExpression {
    @Override
    public void interpret(Node<?> node, MethodVisitor methodVisitor, List<String> variableMetadata, InputBuffer inputBuffer, HashMap<String, String> variablesType) {
        List<? extends Node<?>> children = node.getChildren();
        for(Node<?> child : children){
            Statement statement = new Statement();
            statement.interpret(child, methodVisitor, variableMetadata, inputBuffer, variablesType);
        }
    }
}
