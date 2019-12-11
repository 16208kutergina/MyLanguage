package expressions;

import expressions.baseExpressions.AbstractExpression;
import org.objectweb.asm.MethodVisitor;
import org.parboiled.Node;
import org.parboiled.buffers.InputBuffer;

import java.util.HashMap;
import java.util.List;

import static expressions.Utils.getClassExpression;

public class Statement implements AbstractExpression {
    @Override
    public void interpret(Node<?> node, MethodVisitor methodVisitor, List<String> variableMetadata, InputBuffer inputBuffer, HashMap<String, String> variablesType) {
        try {
            Node<?> child = node.getChildren().get(0);
            AbstractExpression statement = getClassExpression("expressions.", child.getLabel());
            statement.interpret(child, methodVisitor, variableMetadata, inputBuffer, variablesType);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
