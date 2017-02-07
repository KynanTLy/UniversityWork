
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.print.attribute.standard.Destination;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by kynan on 9/9/16.
 * Collaboration with TA, Lecture Notes
 */

public class Main {
    //Mode keeps track if it is intepreter, mips, arms or x86
    private static int mode;

    public static void main(String[] args) throws Exception{
        //Read input file
        String inputMode = null;
        if(args.length>0) inputMode = args[1];


        InputStream is = System.in;
        if (inputMode!=null) is = new FileInputStream(inputMode);

        //Set template for EvalVisitor
        switch(args[0]){
            case "mips":
                setMode(1);
                break;
            default:
                setMode(0);
                break;
        }//end mode

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

    public static int getMode(){
        return mode;
    }

    public static void setMode(int x){
        mode = x;
    }

}//end Main Class

