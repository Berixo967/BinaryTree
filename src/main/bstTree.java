package main;

public class bstTree {

    private Node guard;

    public bstTree() {
        this.guard = new Node(null, null, null, 0L, 0L, 0L, 0L);
    }

    public void add(Long x){
        if(guard.left == null){
            guard.left = new Node(null, null, guard, x, x, 1L, 1L);
        }else{
            guard.left.add(x);
        }
    }

    public void erase(Long x){
        if(guard.left != null){
            guard.left.erase(x);
        }
    }

    public Double getMedian(){
        if(guard.left != null)
            return guard.left.getMedian();
        return 0D;
    }

    public Long getSum(){
        if(guard.left != null)
            return guard.left.getSum();
        return 0L;
    }

    public Double getAverageValue(){
        if(guard.left != null)
            return guard.left.getAverageValue();
        return 0D;
    }

    private class Node {
        private Node left;
        private Node right;
        private Node parent;
        private Long data;
        private Long sum;
        private Long size;
        private Long amount;

        private Node(Node left, Node right, Node parent, Long data, Long sum, Long size, Long amount) {
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.data = data;
            this.sum = sum;
            this.size = size;
            this.amount = amount;
        }

        public Node(){

        }

        private Node find(Long x){
            Node result = this;
            while(result != null && !result.data.equals(x)){
                if(x < result.data){
                    result = result.left;
                }else{
                    result = result.right;
                }
            }
            return result;
        }

        private void add(Long x){
            Node node = this;
            while(!node.data.equals(x)) {
                node.size++;
                node.sum += x;
                if (x < node.data) {
                    if(node.left == null){
                        node.left = new Node(null, null, node, x, x, 1L, 1L);
                        return;
                    }
                    node = node.left;
                } else {
                    if(node.right == null){
                        node.right = new Node(null, null, node, x, x, 1L, 1L);
                        return;
                    }
                    node = node.right;
                }
            }
            node.size++;
            node.amount++;
            node.sum += x;
        }

        private void fix(Long x){
            Node node = this;
            while(node != null){
                node.size--;
                node.sum -= x;
                node = node.parent;
            }
        }

        private void erase(Long x) {
            Node toDestroy = find(x);
            if (toDestroy != null) {
                toDestroy.fix(x);
                toDestroy.amount--;
                if (toDestroy.amount > 0) {
                    return;
                }

                if (toDestroy.left == null) {
                    if (toDestroy.right == null) {
                        if (toDestroy.parent.left == toDestroy) {
                            toDestroy.parent.left = null;
                        } else {
                            toDestroy.parent.right = null;
                        }
                    } else {
                        toDestroy.right.parent = toDestroy.parent;
                        if (toDestroy.parent.left == toDestroy) {
                            toDestroy.parent.left = toDestroy.right;
                        } else {
                            toDestroy.parent.right = toDestroy.right;
                        }
                    }
                } else {
                    if (toDestroy.right == null) {
                        toDestroy.left.parent = toDestroy.parent;
                        if (toDestroy.parent.left == toDestroy) {
                            toDestroy.parent.left = toDestroy.left;
                        } else {
                            toDestroy.parent.right = toDestroy.left;
                        }
                    } else {
                        Node successor = toDestroy.right;
                        while (successor.left != null) {
                            successor = successor.left;
                        }
                        toDestroy.left.parent = successor;
                        successor.left = toDestroy.left;
                        if (toDestroy.parent.left == toDestroy) {
                            toDestroy.parent.left = successor;
                        } else {
                            toDestroy.parent.right = successor;
                        }

                        if (successor != toDestroy.right) {
                            {
                                Node y = successor.parent;
                                while (y != toDestroy) {
                                    y.size--;
                                    y.sum -= x;
                                    y = y.parent;
                                }
                                if (successor.parent.left == successor) {
                                    successor.parent.left = successor.right;
                                } else {
                                    successor.parent.right = successor.right;
                                }
                                if (successor.right != null) {
                                    successor.right.parent = successor.parent;
                                }
                                successor.right = toDestroy.right;
                            }
                            successor.parent = toDestroy.parent;
                            successor.sum = successor.left.sum + successor.right.sum + successor.amount;
                            successor.size = successor.left.size + successor.right.sum + successor.sum;
                        }
                    }
                }
            }
        }

        private Long getSum(){
            return sum;
        }

        private Double getAverageValue(){
            return sum.doubleValue()/size.doubleValue();
        }

        private Double getElement(Long k) throws IllegalArgumentException{
            if(size < k){
                throw new IllegalArgumentException("Tree has only " + size + "values");
            }
            if(left != null) {
                if(left.size >= k){
                    return left.getElement(k);
                }else{
                    k -= left.size;
                    if(k <= amount){
                        return data.doubleValue();
                    }else{
                        return right.getElement(k - amount);
                    }
                }
            }else{
                if(k <= amount){
                    return data.doubleValue();
                }else{
                    return right.getElement(k - amount);
                }
            }
        }

        private Double getMedian(){
            if(size % 2 == 1)
                return getElement((size+1)/2);
            return (getElement(size/2) + getElement(size/2 + 1))/2D;
        }
    }
}
