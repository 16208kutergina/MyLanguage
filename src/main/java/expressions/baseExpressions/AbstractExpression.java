package expressions.baseExpressions;

import org.objectweb.asm.MethodVisitor;
import org.parboiled.Node;
import org.parboiled.buffers.InputBuffer;

import java.util.HashMap;
import java.util.List;

public interface AbstractExpression {
    void interpret(Node<?> node, MethodVisitor methodVisitor, List<String> variableMetadata, InputBuffer inputBuffer, HashMap<String, String> variablesType);
}
