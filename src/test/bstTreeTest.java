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
        long numberOfIterations = 1000000L;
        long mod = 2000L;
        Map<Long, Long> m = new HashMap<>();
        long sum = 0L;
        long size = 0L;
        long randomLong;
        while(numberOfIterations-- != 0L){
            Integer x = random.nextInt();
            switch ((x%4+4)%4){
                case 0:
                    randomLong = random.nextLong()%mod;
                    tree.add(randomLong);
                    if(m.get(randomLong) == null){
                        m.put(randomLong, 1L);
                    }else{
                        m.put(randomLong, m.get(randomLong) + 1);
                    }
                    sum += randomLong;
                    size++;
                    break;
                case 1:
                    randomLong = random.nextLong()%mod;
                    tree.erase(randomLong);
                    if(m.get(randomLong) != null){
                        size--;
                        sum -= randomLong;
                        if(m.get(randomLong) == 1){
                            m.remove(randomLong);
                        }else{
                            m.put(randomLong, m.get(randomLong) - 1);
                        }
                    }
                case 2:
                    assertEquals((Long) sum, tree.getSum());
                    break;
                case 3:
                    assertEquals(tree.getAverageValue(), new Double(((double)sum)/((double)size)));
                    break;
            }
            if(numberOfIterations%1000 == 0){
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
        Long numberOfIterations = 800000L;
        Long median = 10L;
        Long mod = 42034L;
        Random random = new Random();
        tree.add(median);
        while (numberOfIterations-- != 0){
            Long left = median + 1 + (random.nextLong()%mod + mod)%mod;
            Long right = median - 1 - (random.nextLong()%mod + mod)%mod;
            tree.add(left);
            tree.add(right);
        }
        assertEquals(median.doubleValue(), tree.getMedian().doubleValue());
    }

}
