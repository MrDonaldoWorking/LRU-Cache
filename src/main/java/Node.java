public class Node<K, V> {
    private final K key;
    private final V value;

    private Node<K, V> next = null;
    private Node<K, V> prev = null;

    public Node(final K key, final V value) {
        this.key = key;
        this.value = value;
    }

    public Node(final Node<K, V> node) {
        this.key = node.getKey();
        this.value = node.getValue();
        this.next = node.getNext();
        this.prev = node.getPrev();
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public Node<K, V> getNext() {
        return next;
    }

    public void setNext(final Node<K, V> next) {
        this.next = next;
    }

    public Node<K, V> getPrev() {
        return prev;
    }

    public void setPrev(final Node<K, V> prev) {
        this.prev = prev;
    }
}
