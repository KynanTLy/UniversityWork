/**
 * Created by tianzhi on 10/23/16.
 */
public class Helper {

    //Formats the numbers correctly
    public static String formatNumber(String s){
        String temp = s.replace("_", "");
        return temp.replace("+", "");
    }

    public static  String convertType(String s){
        String vectorType = null;
        if (s.contains("_")) {
            vectorType = s.split("_")[1];
        }

        s = s.split("_")[0];
        switch (s){
            case "integer":
                return "i32";
            case "int":
                return "i32";
            case "string":
                return "i8*";
            case "real":
                 return "float";
            case "character":
                return "i8";
            case "boolean":
                return "i8";
            case "void":
                return "void";
            case "vector":
                switch(vectorType) {
                    case "integer":
                        return "i32*";
                    case "real":
                        return "float*";
                    case "character":
                        return "i8*";
                    case "boolean":
                        return "i8*";
                }

            default:
                throw new Error("Undefined type");
        }
    }//end convertType

    //Assign the input value a type
    public static int assignType(String s){
        switch (s){
            case "integer":
                return 0;
            case "real":
                return 1;
            case "boolean":
                return 2;
            case "character":
                return 3;
            case "tuple":
                return 4;
            default:
                throw new Error("Undefined type");

        }
    }

    // Takes a valid char sequence like 'c' or '\n'
    // return char c, or char \n
    public static char getEscapedChars(String s) {
        if (s.charAt(1) == '\\') {
            switch(s.charAt(2)) {
                //case 'a':
                 //   return '\a';
                case 'b':
                    return '\b';
                case 'n':
                    return '\n';
                case 'r':
                    return '\r';
                case 't':
                    return '\t';
                case '\\':
                    return '\\';
                case '’':
                    return '’';
                case '\"':
                    return '\"';
                case '0':
                    return '\0';
                default:
                    throw new Error("Undefined type");
            }
        }
        return s.charAt(1);
    }


}
