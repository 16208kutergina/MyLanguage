import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParseTreeUtils;
import org.parboiled.support.ParsingResult;
import org.parboiled.support.ValueStack;


@BuildParseTree
class CalculatorParser extends BaseParser<Object> {

    Rule Program(){
        return ZeroOrMore(Sequence(Statement(), Divider().suppressNode()).skipNode());
    }

    Rule Statement() {
        return  FirstOf(InitVariable(), If(), While(), Print(), Assignment(), Condition());
    }

    Rule Divider(){
        return Sequence(Space(), ";", Space());
    }

    Rule Assignment(){
        return Sequence(Variable(), Space().suppressNode(),"=", Space().suppressNode(),  FirstOf(Term().skipNode(), Condition() ));
    }

    Rule Condition(){
        return Sequence(Term(), Space().suppressNode(), FirstOf(">", "<", ">=", "<=", "=="), Space().suppressNode(), Term());
    }

    Rule Term(){
        return FirstOf(Variable(), Literal().skipNode());
    }

    Rule Literal(){
        return FirstOf(LiteralInt(), LiteralString());
    }

    Rule LiteralInt(){
        return OneOrMore(CharRange('0', '9'));
    }

    Rule LiteralString(){
        return Sequence("\"", OneOrMore(CharRange('A', 'z')), "\"");
    }

    Rule Variable(){
        return Sequence(OneOrMore(CharRange('A', 'z')).skipNode(), ZeroOrMore(CharRange('0', '9')).skipNode(),ZeroOrMore(CharRange('A', 'z')).skipNode());
    }

    Rule InitVariable() {
        return Sequence(Type()," ", Space().suppressNode(), Variable());
    }

    Rule Type(){
        return FirstOf("Int", "String");
    }

    Rule While(){
        return Sequence("while", Space().suppressNode(),"(", Space().suppressNode(), Condition(), Space().suppressNode(), ")", "{", Space().suppressNode(), ZeroOrMore(Statement()).skipNode(), Space().suppressNode(), "}");
    }

    Rule Print(){
        return Sequence("print", Space().suppressNode(),"(", Space().suppressNode(), Term(), Space().suppressNode(), ")" );
    }

    Rule Space(){
        return Sequence(ZeroOrMore(" "), ZeroOrMore("\n")).suppressNode();
    }

    Rule If(){
        return Sequence(
                "if",Space().suppressNode(),"(", Space().suppressNode(), Condition(), Space().suppressNode(),")", Space().suppressNode(), "{", Space().suppressNode(), ZeroOrMore(Statement()), Space().suppressNode(),"}"
        );
    }



    public static void main(String[] args){
        String input = "int t;";
        CalculatorParser parser = Parboiled.createParser(CalculatorParser.class);
        ParsingResult<?> result = new ReportingParseRunner(parser.Program()).run(input);
        String parseTreePrintOut = ParseTreeUtils.printNodeTree(result);
//        System.out.println(result.parseTreeRoot.getChildren().get(0).getChildren().get(2).getChildren());
        System.out.println(parseTreePrintOut);
    }
}