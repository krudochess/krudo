
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

// krudo package
package org.krudo;

//
import static org.krudo.Tool.*;
import java.util.LinkedHashMap;
import java.util.Map;

//
public final class TT 
{
    //
    public static final int
    A = 1,
    B = 2,
    E = 3;

    //
    private static int
    count = 0,
    queries = 0,
    success = 0;

    //
    private final static int
    STACK_SIZE = 131072,
    CACHE_SIZE = STACK_SIZE - 1;

    // stack with free availables transpos
    private final static Transpos[]
    STACK = new Transpos[STACK_SIZE];

    //
    private final static LinkedHashMap<Long, Transpos>
    CACHE = new LinkedHashMap<Long, Transpos>(CACHE_SIZE + 1, 1, false)
    {
        // check if cache is full and flush oldest item
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, Transpos> e)
        {
            // cache is full
            if (size() > CACHE_SIZE)
            {
                // return entry to stack
                TT.free(e.getValue());

                // return true than remove
                return true;
            }

            // return false not remove
            return false;
        }
    };

    // init trasposition table
    public static void init()
    {
        // fill stack with fresh new instances
        for (int i = 0; i < STACK_SIZE; i++)
        {
            STACK[i] = new Transpos();
            if (i == 0) {
                CACHE.put(0L, STACK[i]);
                CACHE.remove(0L);
            }
        }

        // set count to full-stack
        count = STACK_SIZE;
    }

    //
    public static Transpos pick()
    {
        //
        return STACK[--count];
    }

    //
    public static void add(
        long h,
        int d,
        int f,
        int v
    ) {
        //
        Transpos t;

        //
        if (has(h)) { t = get(h); } else { t = pick(); }

        //
        if (t.depth > d) { return; }

        //
        t.depth = d;
        t.flags = f;
        t.value = v;

        //
        add(h, t);
    }

    // add item into cache by hash
    public static void add(
        final long h,
        final Transpos t
    ) {
        // add on cache
        CACHE.put(h, t);
    }

    // check if trasposition table have value for hash
    public static boolean has(
        final long h, // hash key
        final int d
    ) {
        //
        queries++;

        //
        if (has(h) && get(h).depth >= d)
        {
            //
            success++;

            //
            return true;
        }

        //
        return false;
    }

    // check if trasposition table have value for hash
    public static boolean has(
        final long h // hash key
    ) {
        //
        return CACHE.containsKey(h);
    }

    //
    public final static int max(long h, int a, int b)
    {
        Transpos t = get(h);

        return b < t.value ? b : t.value;
    }

    //
    public final static int min(long h, int a, int b)
    {
        Transpos t = get(h);

        return a > t.value ? a : t.value;
    }

    // get item from cache by hash
    public final static Transpos get(
        final long h // hash key
    ) {
        // get from cache
        return CACHE.get(h);
    }

    // put tanspos to free available stack
    public static void free(
        final Transpos t // transpos to free
    ) {
        //
        t.depth = 0;
        t.flags = 0;
        t.value = 0;

        //
        STACK[count++] = t;
    }

    // remove element from cache
    public static void del(
        final long h
    ) {
        //
        if (!CACHE.containsKey(h)) { return; }

        //
        Transpos t = CACHE.get(h);

        //
        CACHE.remove(h);

        //
        free(t);
    }
}
