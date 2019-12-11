package expressions;

import expressions.baseExpressions.AbstractExpression;
import org.objectweb.asm.MethodVisitor;
import org.parboiled.Node;
import org.parboiled.buffers.InputBuffer;
import org.parboiled.support.ParseTreeUtils;

import java.util.HashMap;
import java.util.List;

import static expressions.Utils.getClassExpression;

public class InitVariable implements AbstractExpression {

    @Override
    public void interpret(Node<?> node, MethodVisitor methodVisitor, List<String> variableMetadata, InputBuffer inputBuffer, HashMap<String, String> variablesType) {
        try {
            String nameVariable = ParseTreeUtils.getNodeText(node.getChildren().get(2), inputBuffer);
            if(variableMetadata.contains(nameVariable))throw new Exception("Name variable already exist");
            Node<?> child = node.getChildren().get(0);
            String nodeText = ParseTreeUtils.getNodeText(child, inputBuffer);
            AbstractExpression statement = getClassExpression("expressions.baseExpressions.TypeInit", nodeText);
            statement.interpret(node.getChildren().get(2), methodVisitor, variableMetadata, inputBuffer, variablesType);
//            AbstractExpression statement1 = getClassExpression("expressions.Variable", nodeText);
//            statement1.interpret(node, methodVisitor, variableMetadata, inputBuffer, variablesType);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
