package SymbolManager;

/**
 * Created by tianzhi on 10/30/16.
 */
public class TupleArg {

    /**
     * A Tuple Argument Object has a [String type], may have [String id]
     */

    String type;
    String id = "";

    public TupleArg(String type_) {
        type = type_;
    }

    public TupleArg(String type_, String id_) {
        type = type_;
        id = id_;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}
