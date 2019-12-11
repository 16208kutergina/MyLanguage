package expressions.baseExpressions;

import expressions.baseExpressions.AbstractExpression;
import org.objectweb.asm.MethodVisitor;
import org.parboiled.Node;
import org.parboiled.buffers.InputBuffer;
import org.parboiled.support.ParseTreeUtils;

import java.util.HashMap;
import java.util.List;

public class LiteralString implements AbstractExpression {
    @Override
    public void interpret(Node<?> node, MethodVisitor methodVisitor, List<String> variableMetadata, InputBuffer inputBuffer, HashMap<String, String> variablesType) {
        String stringLiteral = ParseTreeUtils.getNodeText(node, inputBuffer);
        String str = stringLiteral.substring(1, stringLiteral.length()-1);
        methodVisitor.visitLdcInsn(str);
    }
}
