
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by kynan on 9/9/16.
 * Collaboration with TA, Lecture Notes
 */

public class Main {

    public static void main(String[] args) throws Exception{

        //Read input file
        String inputFile = null;
        if(args.length>0) inputFile = args[0];
        InputStream is = System.in;
        if (inputFile!=null) is = new FileInputStream(inputFile);

        //Pass fill into ANTLR4 to be analyze
        ANTLRInputStream input = new ANTLRInputStream(is);
        GrammerLexer lexer = new GrammerLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GrammerParser parser = new GrammerParser(tokens);
        ParseTree tree= parser.prog();
        EvalVisitor eval = new EvalVisitor();

        //Evaluate the tree
        eval.visit(tree);

    }//end main method
}//end Main Class

