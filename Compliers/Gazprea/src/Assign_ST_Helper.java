import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 * Created by kynan on 10/23/16.
 */
public class Assign_ST_Helper{

    public static ST loadVar(String type, int RegCounter, String scopedName){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st;
        String vecType = null;
        if (type.split("_")[0].equals("vector")) {
            vecType = type.split("_")[1];
            type = "vector";
        }
        if (type.split("_")[0].equals("interval")) {
            type = "interval";
        }
        switch(type) {
            // int
            case "integer":
                st = template.getInstanceOf("LoadIntVar");
                st.add("regC", RegCounter);

                st.add("name", scopedName);
                return st;

            // real
            case "real":
                st = template.getInstanceOf("LoadRealVar");
                st.add("regC", RegCounter);

                st.add("name", scopedName);
                return st;

            case "boolean":
                st = template.getInstanceOf("LoadBoolVar");
                st.add("regC", RegCounter);

                st.add("name", scopedName);
                return st;

            case "character":
                st = template.getInstanceOf("LoadCharVar");
                st.add("regC", RegCounter);

                st.add("name", scopedName);
                return st;

            case "vector":
                switch(vecType) {
                    case "null":
                        st = template.getInstanceOf("LoadIntegerVector");
                        break;
                    case "integer":
                        st = template.getInstanceOf("LoadIntegerVector");
                        break;
                    case "real":
                        st = template.getInstanceOf("LoadRealVector");
                        break;
                    case "character":
                        st = template.getInstanceOf("LoadCharacterVector");
                        break;
                    case "boolean":
                        st = template.getInstanceOf("LoadBooleanVector");
                        break;
                    default:
                        System.out.println("unknown type");
                        st = template.getInstanceOf("LoadIntegerVector");
                        break;
                }
                st.add("regC", RegCounter);
                st.add("name", scopedName);
                return st;
            case "interval":
                st = template.getInstanceOf("LoadIntegerVector");
                st.add("regC", RegCounter);
                st.add("name", scopedName);
                return st;
            default:
                throw new Error("Undefined type");
        }
    }

    //=================DECLARATION AND ASSIGNMENT OF VARIABLES TEMPLATE========//
    public static ST declareINT(String name){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("DeclareInt");
        st.add("name", name);
        return st;
    }

    public static ST assignINT(String name, int regC){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("AssignInt");
        st.add("name", name);
        st.add("regC", regC);
        return st;
    }

    public static ST declareREAL(String name){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("DeclareReal");
        st.add("name", name);
        return st;
    }
    public static ST assignREAL(String name, int regC){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("AssignReal");
        st.add("name", name);
        st.add("regC", regC);
        return st;
    }

    public static ST declareBOOL(String name){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("DeclareBool");
        st.add("name", name);
        return st;
    }

    public static ST assignBOOL(String name, int regC){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("AssignBool");
        st.add("name", name);
        st.add("regC", regC);
        return st;
    }

    public static ST assignIntVec(String name, int regC){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("AssignIntVector");
        st.add("name", name);
        st.add("regC", regC);
        return st;
    }

    public static ST assignRealVec(String name, int regC){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("AssignRealVector");
        st.add("name", name);
        st.add("regC", regC);
        return st;
    }

    public static ST assignCharVec(String name, int regC){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("AssignCharVector");
        st.add("name", name);
        st.add("regC", regC);
        return st;
    }

    public static ST assignBoolVec(String name, int regC){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("AssignBoolVector");
        st.add("name", name);
        st.add("regC", regC);
        return st;
    }

    // Load the i8 ascii value for the single character string character into regC
    public static ST loadIntForChar(String characterString, int regC) {
        char charval = Helper.getEscapedChars(characterString);

        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("AtomChar");
        st.add("val", (int)charval);
        st.add("regC", regC);
        return st;
    }

    public static ST declareCHAR(String name){
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("DeclareChar");
        st.add("name", name);
        return st;
    }

    // Assign the i8 value in regC to the char variable denoted by name
    public static ST assignCHAR(String name, int regC) {
        STGroup template = new STGroupFile("src/LLVM.stg");
        ST st = template.getInstanceOf("AssignChar");
        st.add("name", name);
        st.add("regC", regC);
        return st;
    }

    //============END DECLARATION AND ASSIGNMENT OF VARIABLES TEMPLATE========//

}
