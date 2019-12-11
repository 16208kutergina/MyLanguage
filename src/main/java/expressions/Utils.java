package expressions;

import expressions.baseExpressions.AbstractExpression;
import org.parboiled.Node;
import org.parboiled.buffers.InputBuffer;
import org.parboiled.support.ParseTreeUtils;

import java.util.HashMap;

public class Utils {
    public static AbstractExpression getClassExpression(String packageAndType, String nodeText) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String className = packageAndType + nodeText;
        Class c = Class.forName(className);
        return (AbstractExpression) c.newInstance();
    }

    public static String getClassName(Node<?> node, InputBuffer inputBuffer, HashMap<String, String> variablesType, String typeExpression) throws Exception {
        String nodeText;
      //  Node<?> child = node.getChildren().get(2).getChildren().get(0);
        if (typeExpression.equals("Variable")) {
            String typeVariable = getTypeVariable(node, inputBuffer, variablesType);
            nodeText = "LoadVariable" + typeVariable;
        } else {
            nodeText = node.getLabel();
        }
        return nodeText;
    }

    public static String getTypeVariable(Node<?> nodeVariable, InputBuffer inputBuffer, HashMap<String, String> variablesType) throws Exception {
        String nodeText = ParseTreeUtils.getNodeText(nodeVariable, inputBuffer);
        String s = variablesType.get(nodeText);
        if(s == null){
            throw new Exception("No such variable");
        }
        return s;
    }
}
