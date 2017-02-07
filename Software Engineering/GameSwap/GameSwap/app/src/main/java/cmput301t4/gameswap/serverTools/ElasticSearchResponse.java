package cmput301t4.gameswap.serverTools;

/**
 * Taken from https://github.com/rayzhangcl/ESDemo/tree/master/ESDemo on November 20, 2015
 */
public class ElasticSearchResponse<T> {
    String _index;
    String _type;
    String _id;
    int _version;
    boolean exists;
    T _source;
    double max_score;

    public T getSource() {
        return _source;
    }
}