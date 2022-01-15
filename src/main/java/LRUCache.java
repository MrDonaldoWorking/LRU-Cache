import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {
    private final Map<K, Node<K, V>> data = new HashMap<>();
    private Node<K, V> head = null;
    private Node<K, V> tail = null;
    private final int capacity;

    public LRUCache(final int capacity) {
        assert capacity > 0 : "Cache capacity must be natural number";
        this.capacity = capacity;
    }

    private void remove(final Node<K, V> node) {
        if (node.getNext() == null) {
            tail = node.getPrev();
        } else {
            node.getNext().setPrev(node.getPrev());
        }
        if (node.getPrev() == null) {
            head = node.getNext();
        } else {
            node.getPrev().setNext(node.getNext());
        }

        assert node.getNext() == null || !node.equals(node.getNext().getPrev());
        assert node.getPrev() == null || !node.equals(node.getPrev().getNext());

        node.setNext(null);
        node.setPrev(null);
    }

    private void shift(final Node<K, V> node) {
        node.setNext(head);
        if (head != null) {
            head.setPrev(node);
        }
        head = node;

        if (data.size() == 1) {
            tail = node;
        }
        assert tail != null : "tail is null";
    }

    public void put(final K key, final V value) {
        if (data.containsKey(key)) {
            final Node<K, V> prevNode = data.get(key);
            remove(prevNode);
            assert prevNode.getPrev() == null && prevNode.getNext() == null : "prevNode not removed";
        } else if (data.size() == capacity) {
            if (tail != null) {
                data.remove(tail.getKey());
                assert data.size() < capacity : "size is greater than capacity";

                final Node<K, V> wasTail = new Node<>(tail);
                remove(wasTail);
                assert wasTail.getNext() == null && wasTail.getPrev() == null : "tail not removed";
            } else {
                throw new IllegalStateException("tail == null while size == capacity");
            }
        }
        final Node<K, V> node = new Node<>(key, value);
        data.put(key, node);
        shift(node);
        assert data.size() <= capacity : "size is greater than capacity";
    }

    public V get(final K key) {
        if (!data.containsKey(key)) {
            return null;
        }
        final Node<K, V> node = data.get(key);
        remove(node);
        shift(node);
        return node.getValue();
    }

    public int getCapacity() {
        return capacity;
    }

    public int size() {
        return data.size();
    }
}
