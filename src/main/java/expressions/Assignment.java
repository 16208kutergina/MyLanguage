package expressions;

import expressions.baseExpressions.AbstractExpression;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.parboiled.Node;
import org.parboiled.buffers.InputBuffer;
import org.parboiled.support.ParseTreeUtils;
import java.util.HashMap;
import java.util.List;

import static expressions.Utils.getClassExpression;

public class Assignment implements AbstractExpression {
    @Override
    public void interpret(Node<?> node, MethodVisitor methodVisitor, List<String> variableMetadata, InputBuffer inputBuffer, HashMap<String, String> variablesType) {
        try {
            String nameVariable = ParseTreeUtils.getNodeText(node.getChildren().get(0), inputBuffer);
            if (!variableMetadata.contains(nameVariable)) throw new Exception("No such variable");
            Node<?> literal = node.getChildren().get(2).getChildren().get(0);
            AbstractExpression statementLiteral = getClassExpression("expressions.baseExpressions.", literal.getLabel());
            statementLiteral.interpret(literal, methodVisitor, variableMetadata, inputBuffer, variablesType);
            Node<?> variableNode = node.getChildren().get(0);
            String variableName =  ParseTreeUtils.getNodeText(variableNode, inputBuffer);
            String variableType = variablesType.get(variableName);
            AbstractExpression statementVariable = getClassExpression("expressions.baseExpressions.StoreVariable", variableType);
            statementVariable.interpret(variableNode, methodVisitor, variableMetadata, inputBuffer, variablesType);
            Label L0 = new Label();
            methodVisitor.visitLabel(L0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
