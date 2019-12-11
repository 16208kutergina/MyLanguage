package expressions.baseExpressions;

import expressions.baseExpressions.AbstractExpression;
import org.objectweb.asm.MethodVisitor;
import org.parboiled.Node;
import org.parboiled.buffers.InputBuffer;
import org.parboiled.support.ParseTreeUtils;

import java.util.HashMap;
import java.util.List;

import static org.objectweb.asm.Opcodes.ALOAD;

public class LoadVariableString implements AbstractExpression {
    @Override
    public void interpret(Node<?> node, MethodVisitor methodVisitor, List<String> variableMetadata, InputBuffer inputBuffer, HashMap<String, String> variablesType) {
        String nameVariable = ParseTreeUtils.getNodeText(node.getChildren().get(0), inputBuffer);
        methodVisitor.visitVarInsn(ALOAD, variableMetadata.indexOf(nameVariable));
    }
}
