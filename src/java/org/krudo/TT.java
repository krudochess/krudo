
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

// krudo package
package org.krudo;

import static org.krudo.Constants.*;

//
import static org.krudo.Decode.m2s;
import static org.krudo.Tool.*;
import static org.krudo.Tool.has;

import java.util.LinkedHashMap;
import java.util.Map;

//
public final class TT 
{
    //
    public static int
    count = 0,
    queries = 0,
    success = 0;

    //
    private final static int
    STACK_SIZE = 3531072,
    CACHE_SIZE = STACK_SIZE - 1000;

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
    public static Transpos pick_max(
        long h,
        int d,
        int a,
        int b
    ) {
        //
        Transpos t = pick();

        //
        t.mm = t.MAX;
        t.cut_loop = Transpos.EVAL;
        t.d = d;
        t.a = a;
        t.b = b;

        //
        return t;
    }

    //
    public static Transpos pick_min(
        long h,
        int d,
        int a,
        int b
    ) {
        //
        Transpos t = pick();

        //
        t.mm = Transpos.MIN;
        t.cut_loop = Transpos.EVAL;
        t.d = d;
        t.a = a;
        t.b = b;

        //
        return t;
    }

    // check if trasposition table have value for hash
    public static boolean test_max(
        final long h, // hash key
        final int d,
        final int a,
        final int b
    ) {
//
        queries++;

        //
        if (has(h))
        {
            //
            Transpos t = get(h);

            //
            if (t.d < d) { return false; }

            //
            if (t.cut_save == Transpos.SOFT
                || (t.cut_save == Transpos.HARD && a >= t.a)
                || (t.cut_save == Transpos.EVAL && b <= t.b)
            ) {
                //
                success++;

                //
                return true;
            }

            /*
            print("1>",
                    "d="+t.d,
                    "a="+t.a,
                    "b="+t.b,
                    "mm="+t.mm,
                    "cut="+t.cut_save,
                    "move="+m2s(t.s,t.v,t.k), "score="+t.score);
            print("2>", "d="+d, "a="+a, "b="+b, "mm=max");
            stop = true;
            stop_d = d;
            */

            //
            return false;
        }

        //
        return false;
    }

    // check if trasposition table have value for hash
    public static boolean test_min(
        final long h, // hash key
        final int d,
        final int a,
        final int b
    ) {
        //
        queries++;

        //
        if (has(h))
        {
            //
            Transpos t = get(h);

            //
            if (t.d < d) { return false; }

            //
            if (t.cut_save == Transpos.SOFT
                    || (t.cut_save == Transpos.HARD && a >= t.a)
                    || (t.cut_save == Transpos.EVAL && b <= t.b)) {


                //
                success++;

                return true;
            }


            /*
            print("1>",
                    "d="+t.d,
                    "a="+t.a,
                    "b="+t.b,
                    "mm="+t.mm,
                    "cut="+t.cut_save,
                    "move="+m2s(t.s,t.v,t.k), "score="+t.score);
            print("2>", "d="+d, "a="+a, "b="+b, "mm=min");
            stop = true;
            stop_d = d;
            */

            //
            return false;
        }

        //
        return false;
    }

    //
    public final static void save(long h, Transpos t, int s0)
    {
        //
        if (has(h))
        {
            //
            if (get(h).d > t.d) { return; }

            //
            del(h);
        }

        //
        t.cut_save = t.cut_loop;
        t.score = s0;

        //
        add(h, t);

        /*
        if (TT.stop) {
            print("3>",
                    "d="+t.d, "a="+t.a, "b="+t.b, "mm="+t.mm,
                    "cut="+ t.cut_save,
                    "move="+m2s(t.s,t.v,t.k),
                    "score="+t.score);
            print("--");
            exit();
            TT.stop = false;
        }*/
    }

    //
    public final static int eval_max(long h, int a, int b, PV pv)
    {
        //
        Transpos t = get(h);

        //
        if (t.cut_save == Transpos.SOFT) { pv.cat(t.s, t.v, t.k); return t.score; }

        if (t.cut_save == Transpos.HARD) { return t.b; }

        if (t.cut_save == Transpos.EVAL) { return a; }

        //
        return t.score;
    }

    //
    public final static int eval_min(long h, int a, int b, PV pv)
    {
        //
        Transpos t = get(h);

        //
        if (t.cut_save == Transpos.SOFT) { pv.cat(t.s, t.v, t.k); return t.score; }

        if (t.cut_save == Transpos.HARD) { return t.a; }

        if (t.cut_save == Transpos.EVAL) { return b; }

        //
        return t.score;
    }

    // check if trasposition table have value for hash
    public static boolean has(
        final long h // hash key
    ) {
        //
        return CACHE.containsKey(h);
    }

    // add item into cache by hash
    public static void add(
        final long h,
        final Transpos t
    ) {
        // add on cache
        CACHE.put(h, t);
    }

    // get item from cache by hash
    public final static Transpos get(
        final long h // hash key
    ) {
        // get from cache
        return CACHE.get(h);
    }

    //
    public static Transpos pin(long h)
    {
        //
        if (has(h)) { return get(h); }

        //
        Transpos t = pick();

        //
        add(h, t);

        //
        return t;
    }

    //
    public static Transpos pick()
    {
        //
        return STACK[--count];
    }

    // put tanspos to free available stack
    public static void free(
        final Transpos t // transpos to free
    ) {
        //
        t.d = 0;

        //
        STACK[count++] = t;
    }

    // remove element from cache
    public static void del(
        final long h
    ) {
        //
        Transpos t = CACHE.get(h);

        //
        CACHE.remove(h);

        //
        free(t);
    }
}
