package main;

import java.util.ArrayList;
import java.util.Collections;

public class Node {

    private class Pair{
        public Long first;
        public Long second;
        public Pair add(Pair pair){
            first += pair.first;
            second += pair.second;
            return this;
        }

        public Pair(Long first, Long second) {
            this.first = first;
            this.second = second;
        }
    }

    public Node left;
    public Node right;
    public long data;

    public Node(Node left, Node right, long data) {
        this.left = left;
        this.right = right;
        this.data = data;
    }

    public Node() {
    }

    public Long sum(){
        Long result = data;
        if(left != null)
            result += left.sum();
        if(right != null)
            result += right.sum();
        return result;
    }

    private Pair computeSumAndSize(){
        Pair result = new Pair(data, 1L);
        if(left != null)
            result.add(left.computeSumAndSize());
        if(right != null)
            result.add(right.computeSumAndSize());
        return result;
    }

    public Double getAvg(){
        Pair sumAndSize = computeSumAndSize();
        return sumAndSize.first.doubleValue()/sumAndSize.second.doubleValue();
    }

    private void getElements(ArrayList<Long> list){
        list.add(data);
        if(left != null)
            left.getElements(list);
        if(right != null)
            right.getElements(list);
    }

    public Double getMedian(){
        ArrayList<Long> elements = new ArrayList<>();
        getElements(elements);
        Collections.sort(elements);
        if(elements.size() % 2 == 1){
            return elements.get(elements.size()/2).doubleValue();
        }else{
            return (elements.get(elements.size()/2).doubleValue() + elements.get(elements.size()/2-1).doubleValue())/2;
        }
    }
}
