package expressions;

import expressions.baseExpressions.AbstractExpression;
import org.objectweb.asm.MethodVisitor;
import org.parboiled.Node;
import org.parboiled.buffers.InputBuffer;
import org.parboiled.support.ParseTreeUtils;

import java.util.HashMap;
import java.util.List;

import static expressions.Utils.getClassName;
import static expressions.Utils.getTypeVariable;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;

public class Print implements AbstractExpression {
    @Override
    public void interpret(Node<?> node, MethodVisitor methodVisitor, List<String> variableMetadata, InputBuffer inputBuffer, HashMap<String, String> variablesType) {
        try {
            methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            Node<?> child = node.getChildren().get(2);
            String typeExpression = child.getChildren().get(0).getLabel();
            System.out.println(typeExpression);
            String nodeText = getClassName(node, inputBuffer, variablesType, typeExpression);
            AbstractExpression statement = Utils.getClassExpression("expressions.baseExpressions.", nodeText);
            statement.interpret(child, methodVisitor, variableMetadata, inputBuffer, variablesType);
            String descriptor = determineDescriptor(inputBuffer, variablesType, child);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "("+descriptor+")V", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String determineDescriptor(InputBuffer inputBuffer, HashMap<String, String> variablesType, Node<?> child) throws Exception {
        String typeVariable = getTypeVariable(child, inputBuffer, variablesType);
        String descriptor = "Ljava/lang/String;";
        if(typeVariable.equals("Int")){
            descriptor = "I";
        }
        return descriptor;
    }
}
