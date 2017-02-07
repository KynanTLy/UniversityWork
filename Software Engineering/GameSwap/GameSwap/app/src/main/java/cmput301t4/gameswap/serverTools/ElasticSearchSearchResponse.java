package cmput301t4.gameswap.serverTools;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Taken from https://github.com/rayzhangcl/ESDemo/tree/master/ESDemo on November 20, 2015
 */
public class ElasticSearchSearchResponse<T> {
    int took;
    boolean timed_out;
    transient Object _shards;
    Hits<T> hits;
    boolean exists;

    public Collection<ElasticSearchResponse<T>> getHits() {
        try {
            return hits.getHits();
        } catch(NullPointerException e){
            throw new RuntimeException();
        }
    }

    public Collection<T> getSources() {
        Collection<T> out = new ArrayList<T>();
        for (ElasticSearchResponse<T> essrt : getHits()) {
            out.add( essrt.getSource() );
        }
        return out;
    }

    public String toString() {
        return (super.toString() + ":" + took + "," + _shards + "," + exists + ","  + hits);
    }
}