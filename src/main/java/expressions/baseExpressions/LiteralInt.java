package expressions.baseExpressions;

import expressions.baseExpressions.AbstractExpression;
import org.objectweb.asm.MethodVisitor;
import org.parboiled.Node;
import org.parboiled.buffers.InputBuffer;
import org.parboiled.support.ParseTreeUtils;

import java.util.HashMap;
import java.util.List;

import static jdk.internal.org.objectweb.asm.Opcodes.BIPUSH;

public class LiteralInt implements AbstractExpression {
    @Override
    public void interpret(Node<?> node, MethodVisitor methodVisitor, List<String> variableMetadata, InputBuffer inputBuffer, HashMap<String, String> variablesType) {
        String intLiteral = ParseTreeUtils.getNodeText(node, inputBuffer);
        System.out.println(intLiteral);
        methodVisitor.visitIntInsn(BIPUSH, Integer.parseInt(intLiteral));
    }
}
