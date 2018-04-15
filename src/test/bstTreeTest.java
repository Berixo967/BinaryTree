package test;

import main.bstTree;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class bstTreeTest {
    private bstTree tree;

    @Before
    public void setUp(){
        tree = new bstTree();
    }

    @Test
    public void testRandomBig(){
        Random random = new Random();
        Long z = 1000000L;
        Long mod = 2000L;
        Map<Long, Long> m = new HashMap<>();
        Long sum = 0L;
        Long size = 0L;
        Long r;
        while(z-- != 0L){
            Integer x = random.nextInt();
            switch (x&4){
                case 0:
                    r = random.nextLong()%mod;
                    tree.add(r);
                    if(m.get(r) == null){
                        m.put(r, 1L);
                    }else{
                        m.put(r, m.get(r) + 1);
                    }
                    sum += r;
                    size++;
                    break;
                case 1:
                    r = random.nextLong()%mod;
                    tree.erase(r);
                    if(m.get(r) != null){
                        size--;
                        sum -= r;
                        m.put(r, m.get(r) - 1);
                    }
                    break;
                case 2:
                    assertEquals(tree.getSum(), sum);
                    break;
                case 3:
                    assertEquals(tree.getAverageValue(), new Double(sum.doubleValue()/size.doubleValue()));
                    break;
            }
            if(z%1000 == 0){
                ArrayList<Long> list = new ArrayList<>();
                for (Map.Entry<Long, Long> entry : m.entrySet())
                {
                    Long t = entry.getValue();
                    while(t-- != 0){
                        list.add(entry.getKey());
                    }
                }
                Collections.sort(list);
                if(list.size() % 2 == 0){
                    Double expected = (list.get(list.size()/2).doubleValue() + list.get(list.size()/2-1).doubleValue())/2D;
                    assertEquals(expected, tree.getMedian());
                }else{
                    Double expected = list.get(list.size()/2).doubleValue();
                    assertEquals(expected, tree.getMedian());
                }
            }
        }
    }

    @Test
    public void testMedianOdd(){
        Long z = 800000L;
        Long median = 10L;
        Long mod = 42034L;
        Random random = new Random();
        tree.add(median);
        while (z-- != 0){
            Long left = median + 1 + (random.nextLong()%mod + mod)%mod;
            Long right = median - 1 - (random.nextLong()%mod + mod)%mod;
            tree.add(left);
            tree.add(right);
        }
        assertEquals(median.doubleValue(), tree.getMedian().doubleValue());
    }

}
