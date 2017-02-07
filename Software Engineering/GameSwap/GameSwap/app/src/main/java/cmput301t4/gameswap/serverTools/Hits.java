package cmput301t4.gameswap.serverTools;

import java.util.Collection;

/**
 * Taken from https://github.com/rayzhangcl/ESDemo/tree/master/ESDemo on November 20, 2015
 */
public class Hits<T> {
    int total;
    double max_score;
    Collection<ElasticSearchResponse<T>> hits;
    public Collection<ElasticSearchResponse<T>> getHits() {
        return hits;
    }
    public String toString() {
        return (super.toString()+","+total+","+max_score+","+hits);
    }
}
