package com.phasmidsoftware.dsaipg.adt.pq;

import com.phasmidsoftware.dsaipg.util.PrivateMethodTester;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Iterator;

import static org.junit.Assert.*;

public class PriorityQueueTest {

    private void setFields(PriorityQueue<?> pq, Object[] binHeap, int last) {
        try {
            Field binHeapField = PriorityQueue.class.getDeclaredField("binHeap");
            binHeapField.setAccessible(true);
            binHeapField.set(pq, binHeap);
            Field lastField = PriorityQueue.class.getDeclaredField("last");
            lastField.setAccessible(true);
            lastField.setInt(pq, last);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUnordered1() {
        String[] binHeap = new String[3];
        binHeap[1] = "A";
        binHeap[2] = "B";
        boolean max = false;
        PriorityQueue<String> pq = new PriorityQueue<>(2, 1, max, Comparator.comparing(String::toString), false, 2);
        setFields(pq, binHeap, 2);
        PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals(max, tester.invokePrivate("unordered", 1, 2));
    }

    @Test
    public void testUnordered2() {
        String[] binHeap = new String[3];
        binHeap[1] = "A";
        binHeap[2] = "B";
        boolean max = true;
        PriorityQueue<String> pq = new PriorityQueue<>(2, 1, max, Comparator.comparing(String::toString), false, 2);
        setFields(pq, binHeap, 2);
        PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals(max, tester.invokePrivate("unordered", 1, 2));
    }

    @Test
    public void testSwimUp0() {
        String[] binHeap = new String[3];
        String a = "A";
        String b = "B";
        binHeap[0] = a;
        binHeap[1] = b;
        PriorityQueue<String> pq = new PriorityQueue<>(3, 0, true, Comparator.comparing(String::toString), true, 2);
        setFields(pq, binHeap, 2);
        PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals(a, tester.invokePrivate("peek", 0));
        tester.invokePrivate("swimUp", 1);
        assertEquals(b, tester.invokePrivate("peek", 0));
    }

    @Test
    public void testSwimUp1() {
        String[] binHeap = new String[3];
        String a = "A";
        String b = "B";
        binHeap[1] = a;
        binHeap[2] = b;
        PriorityQueue<String> pq = new PriorityQueue<>(2, 1, true, Comparator.comparing(String::toString), false, 2);
        setFields(pq, binHeap, 2);
        PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals(a, tester.invokePrivate("peek", 1));
        tester.invokePrivate("swimUp", 2);
        assertEquals(b, tester.invokePrivate("peek", 1));
    }

    @Test
    public void testSwimUp2() {
        String[] binHeap = new String[5];
        binHeap[1] = "Z";
        binHeap[2] = "A";
        binHeap[3] = "B";
        binHeap[4] = "C";
        PriorityQueue<String> pq = new PriorityQueue<>(4, 1, true, Comparator.comparing(String::toString), false, 2);
        setFields(pq, binHeap, 4);
        PrivateMethodTester tester = new PrivateMethodTester(pq);
        tester.invokePrivate("swimUp", 4);
        assertEquals("C", tester.invokePrivate("peek", 2));
    }

    @Test
    public void testSwimUp3() {
        String[] binHeap = new String[5];
        binHeap[1] = "D";
        binHeap[2] = "C";
        binHeap[3] = "E";
        binHeap[4] = "B";
        PriorityQueue<String> pq = new PriorityQueue<>(4, 1, false, Comparator.comparing(String::toString), false, 2);
        setFields(pq, binHeap, 4);
        PrivateMethodTester tester = new PrivateMethodTester(pq);
        tester.invokePrivate("swimUp", 4);
        assertEquals("B", tester.invokePrivate("peek", 1));
    }

    @Test
    public void testSink0() {
        String[] binHeap = new String[4];
        String a = "A";
        String b = "B";
        String c = "C";
        binHeap[0] = a;
        binHeap[1] = b;
        binHeap[2] = c;
        PriorityQueue<String> pq = new PriorityQueue<>(4, 0, true, Comparator.comparing(String::toString), false, 2);
        setFields(pq, binHeap, 3);
        PrivateMethodTester tester = new PrivateMethodTester(pq);
        tester.invokePrivate("sink", 0);
        assertEquals(c, tester.invokePrivate("peek", 0));
        assertEquals(a, tester.invokePrivate("peek", 2));
    }

    @Test
    public void testSink1() {
        String[] binHeap = new String[4];
        String a = "A";
        String b = "B";
        String c = "C";
        binHeap[1] = a;
        binHeap[2] = b;
        binHeap[3] = c;
        PriorityQueue<String> pq = new PriorityQueue<>(3, 1, true, Comparator.comparing(String::toString), false, 2);
        setFields(pq, binHeap, 3);
        PrivateMethodTester tester = new PrivateMethodTester(pq);
        tester.invokePrivate("sink", 1);
        assertEquals(c, tester.invokePrivate("peek", 1));
        assertEquals(a, tester.invokePrivate("peek", 3));
    }

    @Test
    public void testGive1() {
        PriorityQueue<String> pq = new PriorityQueue<>(10, 1, true, Comparator.comparing(String::toString), false, 2);
        String key = "A";
        pq.give(key);
        assertEquals(1, pq.size());
        PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals(key, tester.invokePrivate("peek", 1));
    }

    @Test
    public void testGive2() {
        PriorityQueue<String> pq = new PriorityQueue<>(1, 1, true, Comparator.comparing(String::toString), false, 2);
        PrivateMethodTester tester = new PrivateMethodTester(pq);
        String key = "A";
        pq.give(null);
        assertEquals(1, pq.size());
        assertNull(tester.invokePrivate("peek", 1));
        pq.give(key);
        assertEquals(1, pq.size());
        assertEquals(key, tester.invokePrivate("peek", 1));
    }

    @Test
    public void testTake1() throws PQException {
        PriorityQueue<String> pq = new PriorityQueue<>(10, 1, true, Comparator.comparing(String::toString), false, 2);
        String key = "A";
        pq.give(key);
        assertEquals(key, pq.take());
        assertTrue(pq.isEmpty());
    }

    @Test
    public void testTake2() throws PQException {
        PriorityQueue<String> pq = new PriorityQueue<>(10, 1, true, Comparator.comparing(String::toString), false, 2);
        String a = "A";
        String b = "B";
        pq.give(a);
        pq.give(b);
        PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals(a, tester.invokePrivate("peek", 2));
        assertEquals(b, tester.invokePrivate("peek", 1));
        assertEquals(b, pq.take());
        assertEquals(a, pq.take());
        assertTrue(pq.isEmpty());
    }

    @Test(expected = PQException.class)
    public void testTake3() throws PQException {
        PriorityQueue<String> pq = new PriorityQueue<>(10, 1, true, Comparator.comparing(String::toString), false, 2);
        pq.give("A");
        pq.take();
        pq.take();
    }

    @Test
    public void isEmpty() {
        PriorityQueue<String> pq = new PriorityQueue<>(10, 1, false, Comparator.comparing(String::toString), false, 2);
        assertTrue(pq.isEmpty());
    }

    @Test
    public void size() throws PQException {
        PriorityQueue<String> pq = new PriorityQueue<>(10, 1, false, Comparator.comparing(String::toString), false, 2);
        assertEquals(0, pq.size());
        pq.give("A");
        assertEquals(1, pq.size());
        pq.take();
        assertEquals(0, pq.size());
    }

    @Test
    public void doTake01() throws PQException {
        String[] binHeap = new String[3];
        binHeap[0] = "A";
        binHeap[1] = "B";
        binHeap[2] = "C";
        PriorityQueue<String> pq = new PriorityQueue<>(3, 0, false, Comparator.comparing(String::toString), false, 2);
        setFields(pq, binHeap, 3);
        pq.doTake(pq::snake);
        PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals("B", tester.invokePrivate("peek", 0));
    }

    @Test
    public void doTake02() throws PQException {
        String[] binHeap = new String[3];
        binHeap[0] = "C";
        binHeap[1] = "A";
        binHeap[2] = "B";
        PriorityQueue<String> pq = new PriorityQueue<>(3, 0, true, Comparator.comparing(String::toString), false, 2);
        setFields(pq, binHeap, 3);
        pq.doTake(pq::sink);
        PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals("B", tester.invokePrivate("peek", 0));
    }

    @Test
    public void doTake11() throws PQException {
        String[] binHeap = new String[4];
        binHeap[1] = "A";
        binHeap[2] = "B";
        binHeap[3] = "C";
        PriorityQueue<String> pq = new PriorityQueue<>(3, 1, false, Comparator.comparing(String::toString), false, 2);
        setFields(pq, binHeap, 3);
        pq.doTake(pq::snake);
        PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals("B", tester.invokePrivate("peek", 1));
    }

    @Test
    public void doTake12() throws PQException {
        String[] binHeap = new String[4];
        binHeap[1] = "C";
        binHeap[2] = "A";
        binHeap[3] = "B";
        PriorityQueue<String> pq = new PriorityQueue<>(3, 1, true, Comparator.comparing(String::toString), false, 2);
        setFields(pq, binHeap, 3);
        pq.doTake(pq::sink);
        PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals("B", tester.invokePrivate("peek", 1));
    }

    @Test
    public void iterator0() {
        String[] binHeap = new String[3];
        binHeap[0] = "C";
        binHeap[1] = "B";
        binHeap[2] = "D";
        PriorityQueue<String> pq = new PriorityQueue<>(3, 0, true, Comparator.comparing(String::toString), false, 2);
        setFields(pq, binHeap, 3);
        assertEquals(3, pq.size());
        Iterator<String> iterator = pq.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(binHeap[0], iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(binHeap[1], iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(binHeap[2], iterator.next());
        assertFalse(iterator.hasNext());
        assertEquals(3, pq.size());
    }

    @Test
    public void iterator1() {
        String[] binHeap = new String[4];
        binHeap[1] = "C";
        binHeap[2] = "B";
        binHeap[3] = "D";
        PriorityQueue<String> pq = new PriorityQueue<>(3, 1, true, Comparator.comparing(String::toString), false, 2);
        setFields(pq, binHeap, 3);
        assertEquals(3, pq.size());
        Iterator<String> iterator = pq.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(binHeap[1], iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(binHeap[2], iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(binHeap[3], iterator.next());
        assertFalse(iterator.hasNext());
        assertEquals(3, pq.size());
    }

    @Test
    public void testGetMax() {
        PriorityQueue<String> pq = new PriorityQueue<>(10, 1, false, Comparator.comparing(String::toString), false, 2);
        PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals(false, tester.invokePrivate("getMax"));
    }
}
