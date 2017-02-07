package SymbolManager;

/**
 * Created by tianzhi on 11/1/16.
 */
public class TupleField {

    /**
     * Tuple Field Object has [Integer index], [String type]
     * index is the index in the struct
     */

    Integer index;
    String type;
    public TupleField(Integer index_, String type_) {
        index = index_;
        type = type_;
    }
}
