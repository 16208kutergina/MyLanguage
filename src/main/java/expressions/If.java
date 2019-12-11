package expressions;

import expressions.baseExpressions.AbstractExpression;
import org.objectweb.asm.MethodVisitor;
import org.parboiled.Node;
import org.parboiled.buffers.InputBuffer;

import java.util.HashMap;
import java.util.List;

public class If implements AbstractExpression {
    @Override
    public void interpret(Node<?> node, MethodVisitor methodVisitor, List<String> variableMetadata, InputBuffer inputBuffer, HashMap<String, String> variablesType) {
        System.out.println(node.getChildren());
    }
}
