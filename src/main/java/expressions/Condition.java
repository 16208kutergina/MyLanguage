package expressions;

import expressions.baseExpressions.AbstractExpression;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.parboiled.Node;
import org.parboiled.buffers.InputBuffer;
import org.parboiled.support.ParseTreeUtils;

import java.util.HashMap;
import java.util.List;

import static expressions.Utils.getClassExpression;
import static expressions.Utils.getClassName;
import static org.objectweb.asm.Opcodes.*;

public class Condition implements AbstractExpression {

    @Override
    public void interpret(Node<?> node, MethodVisitor methodVisitor, List<String> variableMetadata, InputBuffer inputBuffer, HashMap<String, String> variablesType) {
        System.out.println(node.getChildren());
        System.out.println(node.getChildren().get(0).getChildren());
        try {
            interpretNodeNumber(0, node, methodVisitor, variableMetadata, inputBuffer, variablesType);
            interpretNodeNumber(2, node, methodVisitor, variableMetadata, inputBuffer, variablesType);
            Node<?> operation = node.getChildren().get(1);
            String operationText = ParseTreeUtils.getNodeText(operation, inputBuffer);
            System.out.println(operationText);
            int opcodes = getOpcodes(operationText);
            Label label2 = new Label();
            methodVisitor.visitJumpInsn(opcodes, label2);
            methodVisitor.visitInsn(ICONST_1);
            Label label3 = new Label();
            methodVisitor.visitJumpInsn(GOTO, label3);
            methodVisitor.visitLabel(label2);
            methodVisitor.visitFrame(Opcodes.F_APPEND, 1, new Object[]{Opcodes.INTEGER}, 0, null);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(label3);
            methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{Opcodes.INTEGER});
            methodVisitor.visitVarInsn(ISTORE, variableMetadata.size());
            Label label4 = new Label();
            methodVisitor.visitLabel(label4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void interpretNodeNumber(int number, Node<?> node, MethodVisitor methodVisitor, List<String> variableMetadata, InputBuffer inputBuffer, HashMap<String, String> variablesType) throws Exception {
        Node<?> left = node.getChildren().get(number).getChildren().get(0);
        System.out.println(left.getLabel());
        String classNameLeft = getClassName(left, inputBuffer, variablesType, left.getLabel());
        AbstractExpression classExpressionLeft = getClassExpression("expressions.baseExpressions.", classNameLeft);
        classExpressionLeft.interpret(left, methodVisitor, variableMetadata, inputBuffer, variablesType);
    }

    private int getOpcodes(String operation) throws Exception {
switch (operation){
    case "<":
        return Opcodes.IF_ICMPGE;
    case ">":
        return Opcodes.IF_ICMPLE;
    case "<=":
        return Opcodes.IF_ICMPGT;
    case ">=":
        return Opcodes.IF_ICMPLT;
    case "==":
        return Opcodes.IF_ICMPNE;
}
throw new Exception("Unknown operation");
    }


}
