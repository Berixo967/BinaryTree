package test;

import main.Node;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static junit.framework.TestCase.assertEquals;

public class NodeTest {

    private class Tuple<X, Y> {
        private final X first;
        private final Y second;

        private Tuple(X first, Y second) {
            this.first = first;
            this.second = second;
        }
    }

    private Tuple<Node, ArrayList<Long>> getRandomTree(long treeSize) {
        Random random = new Random();
        ArrayList<Node> list = new ArrayList<>();
        long randomValue = random.nextInt();
        ArrayList<Long> treeElements = new ArrayList<>();
        treeElements.add(randomValue);
        Node root = new Node(null, null, randomValue);
        list.add(root);
        treeSize--;

        while (treeSize != 0) {
            Node node;
            do {
                node = list.get((random.nextInt() % list.size() + list.size()) % list.size());
            } while (node.left != null && node.right != null);

            randomValue = random.nextInt();
            Node newNode = new Node(null, null, randomValue);
            list.add(newNode);
            treeElements.add(randomValue);
            if (node.left != null) {
                node.right = newNode;
            } else if (node.right != null) {
                node.left = newNode;
            } else {
                if (random.nextInt() % 2 == 0) {
                    node.left = newNode;
                } else {
                    node.right = newNode;
                }
            }
            treeSize--;
        }

        return new Tuple<>(root, treeElements);
    }

    @Test
    public void testSum() {
        Node node = new Node(null, null, 5L);
        assertEquals(new Long(5), node.sum());
        node.left = new Node(null, null, 10L);
        node.right = new Node(null, null, 5L);
        assertEquals(new Long(20), node.sum());
    }

    @Test
    public void testSumSmallRandom() {
        Long treeSize = 10L;
        Tuple<Node, ArrayList<Long>> x = getRandomTree(treeSize);
        Long expected = x.second.stream().mapToLong(Long::longValue).sum();
        assertEquals(expected, x.first.sum());
    }

    @Test
    public void testSumBigRandom() {
        Long treeSize = 1000000L;
        Tuple<Node, ArrayList<Long>> x = getRandomTree(treeSize);
        Long expected = x.second.stream().mapToLong(Long::longValue).sum();
        assertEquals(expected, x.first.sum());
    }

    @Test
    public void testAvg() {
        Node node = new Node(null, null, 5L);
        assertEquals(5D, node.getAvg());
        node.left = new Node(null, null, 7L);
        node.right = new Node(null, null, 2L);
        assertEquals((5D + 7D + 2D) / 3D, node.getAvg());
    }

    @Test
    public void testAvgSmallRandom() {
        Long treeSize = 1000L;
        Tuple<Node, ArrayList<Long>> x = getRandomTree(treeSize);
        Long sum = x.second.stream().mapToLong(Long::longValue).sum();
        assertEquals((sum.doubleValue() / treeSize.doubleValue()), x.first.getAvg());
    }

    @Test
    public void testAvgBigRandom() {
        Long treeSize = 1000001L;
        Tuple<Node, ArrayList<Long>> x = getRandomTree(treeSize);
        Long sum = x.second.stream().mapToLong(Long::longValue).sum();
        assertEquals(sum.doubleValue() / treeSize.doubleValue(), x.first.getAvg());
    }

    @Test
    public void testMedianOdd() {
        Node node = new Node(null, null, 5);
        node.left = new Node(null, null, 7);
        node.right = new Node(null, null, 6);
        assertEquals(6D, node.getMedian());
    }

    @Test
    public void testMedianEven() {
        Node node = new Node(null, null, 3);
        node.left = new Node(null, null, 6);
        node.right = new Node(null, null, 7);
        node.left.left = new Node(null, null, 9);
        assertEquals((6D + 7D) / 2D, node.getMedian());
    }

    @Test
    public void testMedianEvenSmallRandom() {
        Integer treeSize = 1000;
        Tuple<Node, ArrayList<Long>> x = getRandomTree(treeSize);
        Collections.sort(x.second);
        Double expected = (x.second.get(treeSize / 2 - 1).doubleValue() + x.second.get(treeSize / 2).doubleValue()) / 2D;
        assertEquals(expected, x.first.getMedian());
    }

    @Test
    public void testMedianEvenBigRandom() {
        Integer treeSize = 1000000;
        Tuple<Node, ArrayList<Long>> x = getRandomTree(treeSize);
        Collections.sort(x.second);
        Double expected = (x.second.get(treeSize / 2 - 1).doubleValue() + x.second.get(treeSize / 2).doubleValue()) / 2D;
        assertEquals(expected, x.first.getMedian());
    }

    @Test
    public void testMedianOddSmallRandom() {
        Integer treeSize = 1001;
        Tuple<Node, ArrayList<Long>> x = getRandomTree(treeSize);
        Collections.sort(x.second);
        Double expected = x.second.get(treeSize / 2).doubleValue();
        assertEquals(expected, x.first.getMedian());
    }

    @Test
    public void testMedianOddBigRandom() {
        Integer treeSize = 1000001;
        Tuple<Node, ArrayList<Long>> x = getRandomTree(treeSize);
        Collections.sort(x.second);
        Double expected = x.second.get(treeSize / 2).doubleValue();
        assertEquals(expected, x.first.getMedian());
    }
}
