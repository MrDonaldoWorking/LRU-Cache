import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LRUCacheTest {
    @Test
    public void singleton() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(1);
        assertEquals(0, cache.size());
        assertEquals(1, cache.getCapacity());

        cache.put(114, 514);
        assertEquals(1, cache.size());
        assertEquals(1, cache.getCapacity());
        assertEquals(514, cache.get(114).intValue());

        cache.put(19, 19);
        assertEquals(1, cache.size());
        assertEquals(1, cache.getCapacity());
        assertEquals(19, cache.get(19).intValue());
        assertNull(cache.get(114));
    }

    @Test
    public void replace() {
        LRUCache<String, Integer> cache = new LRUCache<>(2);
        assertEquals(0, cache.size());
        assertEquals(2, cache.getCapacity());

        cache.put("aboba", 141);
        assertEquals(1, cache.size());
        assertEquals(2, cache.getCapacity());
        assertEquals(141, cache.get("aboba").intValue());

        cache.put("aboba", 142);
        assertEquals(1, cache.size());
        assertEquals(2, cache.getCapacity());
        assertEquals(142, cache.get("aboba").intValue());

        cache.put("abiba", 86);
        assertEquals(2, cache.size());
        assertEquals(2, cache.getCapacity());
        assertEquals(86, cache.get("abiba").intValue());
        assertEquals(142, cache.get("aboba").intValue());
    }

    @Test
    public void randomTest() {
        final int bound = 50;
        final LRUCache<Integer, Integer> actual = new LRUCache<>(30);
        final EasyLRUCache<Integer, Integer> expected = new EasyLRUCache<>(30);

        final Random rand = new Random();
        for (int tests = 0; tests < 100_000; ++tests) {
            final int type = rand.nextInt(2);
            switch (type) {
                case 0:
                    final int key = rand.nextInt(bound);
                    final int value = rand.nextInt();
                    actual.put(key, value);
                    expected.put(key, value);
                    break;

                case 1:
                    final int keyToGet = rand.nextInt(bound);
                    assertEquals(expected.get(keyToGet), actual.get(keyToGet));
                    break;
            }

            assertEquals(expected.size(), actual.size());
            if (tests % 17 == 8) {
                for (int key = 0; key < bound; ++key) {
                    assertEquals(expected.get(key), actual.get(key));
                }
            }
        }
    }
}
