import expressions.Program;
import org.objectweb.asm.*;
import org.parboiled.Node;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParseTreeUtils;
import org.parboiled.support.ParsingResult;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;


public class Compile implements Opcodes {
private List<String> variablesMetadata = new LinkedList<>();
private HashMap<String, String> variablesType = new HashMap<>();

    public byte[] dump(ParsingResult<?> ASM) throws Exception {
        ClassWriter classWriter = new ClassWriter(COMPUTE_MAXS);
        createClass(classWriter);
        createMethodInit(classWriter);
        MethodVisitor mainMethod = createMainMethod(classWriter);
        Label L0 = new Label();
        mainMethod.visitLabel(L0);

        interpretASM(mainMethod, ASM);


        mainMethod.visitInsn(RETURN);
        Label L1 = new Label();
        mainMethod.visitLabel(L1);
        mainMethod.visitLocalVariable("args", "[Ljava/lang/String;",null, L0, L1, 0);
        mainMethod.visitMaxs(4, 4);//variablesMetadata.size()+1, variablesMetadata.size());
        classWriter.visitEnd();
        return classWriter.toByteArray();
    }

    private void interpretASM(MethodVisitor method, ParsingResult<?> ASM) {
        Program program = new Program();
        program.interpret(ASM.parseTreeRoot, method, variablesMetadata, ASM.inputBuffer, variablesType);
    }

    private void createClass(ClassWriter classWriter) {
        classWriter.visit(52,
                ACC_PUBLIC + ACC_SUPER,
                "Main",
                null,
                "java/lang/Object",
                null);
        classWriter.visitSource("Main.java", null);
    }

    private void createMethodInit(ClassWriter classWriter) {
        MethodVisitor mv;
        mv = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        Label L0 = new Label();
        mv.visitLabel(L0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        Label L1 = new Label();
        mv.visitLabel(L1);
        mv.visitLocalVariable("this", "LMain;", null, L0, L1, 0);
        variablesMetadata.add("this");
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }

    private MethodVisitor createMainMethod(ClassWriter classWriter) {
        return classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC,
                "main",
                "([Ljava/lang/String;)V",
                null,
                null);
    }

//    private void print(MethodVisitor mv, ) {
//        mv.visitLdcInsn("ds");
//        mv.visitVarInsn(ASTORE, 1);
//        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//        mv.visitVarInsn(ALOAD, 1);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V",false);
//
//        mv.visitMaxs(2, 2);
//        mv.visitEnd();
//    }



    public static void main(String[] args) {
        try {
//            String input = "print(\"a\");";//"String a; a=\"b\";";
//            String input = "print(2);";//"String a; a=\"b\";";
//            String input = "Int t; t = 99; print(t);";//"String a; a=\"b\";";
//            String input = "String a;";//"String a; a=\"b\";";
//            String input = "Int a;";//"String a; a=\"b\";";
//            String input = "String a; a = \"b\";";
//            String input = "String a; a = \"b\"; print(a);";
//            String input = "Int s; Int i; i = 8; i < 9;";
            String input = "Int i; i = 8; if(i < 9){ print(i);}";
            CalculatorParser parser = Parboiled.createParser(CalculatorParser.class);
            ParsingResult<?> result = new ReportingParseRunner(parser.Program()).run(input);
            String parseTreePrintOut = ParseTreeUtils.printNodeTree(result);
//            System.out.println(result.parseErrors.get(0).getStartIndex());
            System.out.println(parseTreePrintOut);
            Node<?> parseTreeRoot = result.parseTreeRoot;
            result.parseTreeRoot.getChildren();
            Compile compile = new Compile();
            byte[] dump = compile.dump(result);
            FileOutputStream fos = new FileOutputStream("Main.class");
            fos.write(dump);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
