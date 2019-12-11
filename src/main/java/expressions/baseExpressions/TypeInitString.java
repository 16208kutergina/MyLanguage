package expressions.baseExpressions;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.parboiled.Node;
import org.parboiled.buffers.InputBuffer;
import org.parboiled.support.ParseTreeUtils;

import java.util.HashMap;
import java.util.List;

import static org.objectweb.asm.Opcodes.ASTORE;


public class TypeInitString implements AbstractExpression {
    @Override
    public void interpret(Node<?> node, MethodVisitor methodVisitor, List<String> variableMetadata, InputBuffer inputBuffer, HashMap<String, String> variablesType) {
        String nameVariable = ParseTreeUtils.getNodeText(node, inputBuffer);
        Label L0 = new Label();
        methodVisitor.visitLabel(L0);
        methodVisitor.visitLdcInsn("");
        methodVisitor.visitVarInsn(ASTORE, variableMetadata.size());
        Label L1 = new Label();
        methodVisitor.visitLabel(L1);
        methodVisitor.visitLocalVariable(nameVariable, "Ljava/lang/String;", null, L0, L1, variableMetadata.size());
        variableMetadata.add(nameVariable);
        variablesType.put(nameVariable, "String");
    }
}
