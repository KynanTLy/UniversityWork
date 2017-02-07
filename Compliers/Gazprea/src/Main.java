
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;


public class Main {
    //Mode keeps track if it is intepreter, mips, arms or x86
    private static int mode;

    public static void main(String[] args) throws Exception{
        //Read input file
        String inputFile = null;
        inputFile = args[0];

        InputStream is = System.in;
        if (inputFile!=null) is = new FileInputStream(inputFile);

        String content = new Scanner(new File(inputFile)).useDelimiter("\\Z").next();

        String code = content.replaceAll("\\.\\.", "\\$\\$"); // Very Dangerous Interval Hack

        //Pass fill into ANTLR4 to be analyze
        ANTLRInputStream input = new ANTLRInputStream(code);
        GrammarLexer lexer = new GrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GrammarParser parser = new GrammarParser(tokens);
        ParseTree tree= parser.prog();
        LLVMVisitor eval = new LLVMVisitor();

        eval.visit(tree);
    }//end main method


}//end Main Class

