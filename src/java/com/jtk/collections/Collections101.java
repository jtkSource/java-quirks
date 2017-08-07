package com.jtk.collections;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by jubin on 5/7/2017.
 */
public class Collections101 {
    public static void main(String[] args) throws InterruptedException {
        //collectionsMethod();
        //KeySetView();
        //singletonCollection();
        //concurrentIterator();
        //splitIterator();
        //queue();

        //priorityQueue();
        //dequeue();

        //transferQueue();

        //concurrentHashMap();


        //comparatorOverEquals();

        //splitIteratorNonThreadSafe();

        splititeratorThreadSafe();

    }

    private static void splititeratorThreadSafe() {
        List<Integer> integers = new ArrayList<>();

        IntStream.range(0,50).forEach(value -> integers.add(value));
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CompletionService<Integer> completionService = new ExecutorCompletionService(executorService);
        Spliterator<Integer> spliterator = integers.spliterator();
        List<Spliterator> spliterators = new ArrayList<>();
        IntStream.range(0,5).forEach(value -> spliterators.add(spliterator.trySplit()));

        for (int i = 0; i < 5; i++) {
            Spliterator<Integer> spliterator1 = spliterators.get(i);
            completionService
                    .submit(() ->spliterator1
                            .forEachRemaining(integer -> System.out.println("Thread "+ Thread.currentThread().getName()+ " integer = " + integer)), 1);
        }
        for (int i = 0; i < 5; i++) {
            completionService.poll();
        }
        executorService.shutdownNow();
    }

    private static void splitIteratorNonThreadSafe() {
        List<Integer> integers = new ArrayList<>();
        IntStream.range(0,50).forEach(value -> integers.add(value));
        Spliterator<Integer> spliterator = integers.spliterator();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CompletionService<Integer> completionService = new ExecutorCompletionService(executorService);
        for (int i = 0; i < 5; i++) {
            completionService
                    .submit(() -> spliterator
                            .trySplit()
                            .forEachRemaining(integer -> System.out.println("Thread "+ Thread.currentThread().getName()+ " integer = " + integer)), 1);

        }
        for (int i = 0; i < 5; i++) {
            completionService.poll();
        }
        executorService.shutdownNow();
    }

    private static void comparatorOverEquals() {
        Set<Person> people = new TreeSet<>(Comparator.comparing(Person::getName));
        people.add(new Person("jubin", 1));
        people.add(new Person("jubin", 2));
        //people.add(null);
        System.out.println("people = " + people);
        Map<Person, String> personStringMap = new TreeMap<>(Comparator.comparing(Person::getName));

        personStringMap.put(new Person("jubin", 1), "jubin");
        personStringMap.put(new Person("jubin", 2), "jubin");
        System.out.println("personStringMap = " + personStringMap);
    }


    private static void concurrentHashMap() throws InterruptedException {
        ConcurrentHashMap<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();

        for (int i = 0; i <= 10; i++) {
            concurrentHashMap.put(i, UUID.randomUUID().toString());
        }
        System.out.println("concurrentHashMap = " + concurrentHashMap);
        Thread t1 = new Thread(() -> {
            Iterator<Integer> it = concurrentHashMap.keySet().iterator();
            while (it.hasNext()) {
                System.out.println("p it.next() = " + it.next());
            }
        });
        Thread t2 = new Thread(() -> {
            Iterator<Integer> it = concurrentHashMap.keySet().iterator();
            while (it.hasNext()) {
                System.out.println("c it.next() = " + it.next());
                it.remove();
            }
        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("concurrentHashMap = " + concurrentHashMap);
    }

    private static void transferQueue() {
        final TransferQueue<Person> personTransferQueue = new LinkedTransferQueue<>();

        Thread producer = new Thread(() -> {
            Random rand = new Random();
            AtomicInteger sequence = new AtomicInteger(1);
            while (true) {
                int sleepTime = rand.nextInt(5) + 1;
                try {
                    Thread.sleep(sleepTime * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int num = sequence.incrementAndGet();
                if (num % 2 == 0) {
                    try {
                        System.out.format("%s: Enqueuing: %d%n", "Producer", num);
                        personTransferQueue.put(new Person("S", num));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.format("%s: Handing off: %d%n", "Producer", num);
                    System.out.format("%s: has a waiting consumer: %b%n", "Producer", personTransferQueue.hasWaitingConsumer());
                    try {
                        personTransferQueue.transfer(new Person("C", num)); // A hand off
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread consumer = new Thread(() -> {
            Random rand = new Random();
            while (true) {
                int sleepTime = rand.nextInt(5) + 1;
                try {
                    Thread.sleep(sleepTime * 1000);
                    Person p = personTransferQueue.take();
                    System.out.println("person = " + p);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producer.start();
        consumer.start();
    }

    private static void dequeue() {
        Deque<Person> personDeque = new ArrayDeque<>(2);
        personDeque.addFirst(new Person("Jubin", 3));
        personDeque.addFirst(new Person("Feba", 43));
        personDeque.addLast(new Person("Ben", 19));
        System.out.println("personDeque = " + personDeque);
    }

    private static void priorityQueue() {
        Queue<Person> people = new PriorityQueue<>(Comparator.comparing(Person::getKey));
        people.offer(new Person("Jubin", 100));
        people.offer(new Person("Feba", 20));
        people.offer(new Person("Ben", 300));

        System.out.println("people = " + people);
        System.out.println("people.poll() = " + people.poll());
        System.out.println("people.poll() = " + people.poll());
        System.out.println("people.poll() = " + people.poll());
        System.out.println("people.poll() = " + people.poll());

        Queue<PersonComparable> personComparables = new PriorityQueue<>();
        personComparables.add(new PersonComparable("Jubin", 100));
        personComparables.add(new PersonComparable("Feba", 20));
        personComparables.add(new PersonComparable("Ben", 300));

        System.out.println("personComparables = " + personComparables);
        System.out.println("personComparables.poll() = " + personComparables.poll());
        System.out.println("personComparables.poll() = " + personComparables.poll());
        System.out.println("personComparables.poll() = " + personComparables.poll());
        System.out.println("personComparables.poll() = " + personComparables.poll());
    }

    private static void queue() {
        Queue<String> stringQueue = new ArrayBlockingQueue<String>(10);
        stringQueue.offer("Hello");// add from the tail
        stringQueue.offer("Jubin");
        System.out.println("stringQueue.element() = " + stringQueue.peek()); //element
        System.out.println("stringQueue = " + stringQueue);
        System.out.println("stringQueue.poll() = " + stringQueue.poll()); // remove
        System.out.println("stringQueue = " + stringQueue);
    }

    private static void splitIterator() {
        NavigableSet<Integer> ns = new TreeSet<>();
        ns.add(1);
        ns.add(2);
        ns.add(3);
        ns.add(4);
        ns.add(5);
        ns.add(6);
        ns.add(7);
        System.out.println("ns = " + ns);
        System.out.println("ns.descendingSet() = " + ns.descendingSet());
        System.out.println("ns.headSet(3,true) = " + ns.headSet(3, true));
        System.out.println("ns.tailSet(3,false) = " + ns.tailSet(3, false));
        System.out.println("ns.subSet(2,false,4,true) = " + ns.subSet(2, false, 4, true));

        System.out.println("ns.pollFirst() = " + ns.pollFirst());
        System.out.println("ns = " + ns);
        Spliterator<Integer> spliterator = ns.spliterator();
        System.out.println("spliterator.characteristics() = " + spliterator.characteristics());
        System.out.println("spliterator.estimateSize() = " + spliterator.estimateSize());
        Spliterator<Integer> spliterator1 = spliterator.trySplit();
        System.out.println("spliterator.estimateSize() = " + spliterator.estimateSize());
        System.out.println("spliterator1.estimateSize() = " + spliterator1.estimateSize());
    }

    private static void concurrentIterator() throws InterruptedException {
        List<Integer> integers = IntStream.range(0, 5).mapToObj(Integer::new).collect(Collectors.toList());
        Thread t1 = new Thread(() -> {
            Iterator<Integer> it = integers.iterator();
            while (it.hasNext()) {
                System.out.println("it.next() = " + it.next());
            }
        });
        Thread t2 = new Thread(() -> {
            Iterator<Integer> it = integers.iterator();
            while (it.hasNext()) {
                System.out.println("it.next() = " + it.next());
                it.remove();
            }
        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("integers = " + integers);
    }

    private static void singletonCollection() {
        List<String> singletonList = Collections.singletonList("Hello");
        System.out.println("singletonList.add(\"ddd\") = " + singletonList.add("ddd"));
    }

    private static void KeySetView() {
        ConcurrentHashMap<String, Boolean> conMap = new ConcurrentHashMap<String, Boolean>();
        Set<String> users = Collections.newSetFromMap(conMap);
        System.out.println("Users: " + users);
        users.add("Jon");//results in adding to the conMap instance
        users.add("Tyron");
        System.out.println("Users: " + users);
        System.out.println("conMap = " + conMap);
        conMap.put("Jubin", Boolean.FALSE);// results in adding to the users set also
        System.out.println("Users: " + users);
        System.out.println("conMap = " + conMap);


        ConcurrentHashMap.KeySetView<String, Boolean> keySetView = ConcurrentHashMap.newKeySet();
        keySetView.add("Feba");

        System.out.println("keySetView = " + keySetView);
        System.out.println("keySetView.getMap() = " + keySetView.getMap());

        keySetView.getMap().put("BeN", Boolean.TRUE);
        System.out.println("keySetView = " + keySetView);
        System.out.println("keySetView.getMap() = " + keySetView.getMap());
    }

    private static void collectionsMethod() {
        List<String> list = new ArrayList<>();
        list.add("John");
        list.add("Richard");
        list.add("Donna");
        list.add("Ken");
        System.out.println("List: " + list);  // Shuffle        
        Collections.shuffle(list);
        System.out.println("After Shuffling: " + list);  // Reverse the list        
        Collections.reverse(list);
        System.out.println("After Reversing: " + list);  // Swap elements at indexes 1 and 3
        Collections.swap(list, 1, 3);
        System.out.println("After Swapping (1 and 3): " + list);  // Rotate elements by 2
        Collections.rotate(list, 2);
        System.out.println("After Rotating by 2: " + list);
    }

    static class Person {
        public String name;
        public int key;

        public Person(String name, int key) {
            this.name = name;
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public int getKey() {
            return key;
        }

        @Override
        public String toString() {
            return this.name + " " + this.key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return key == person.key;
        }

        @Override
        public int hashCode() {
            return key;
        }
    }

    static class PersonComparable implements Comparable<PersonComparable> {
        public String name;
        public int key;

        public PersonComparable(String name, int key) {
            this.name = name;
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public int getKey() {
            return key;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public int compareTo(PersonComparable o) {
            if (this.getKey() > o.getKey())
                return 1;
            else if (this.getKey() < o.getKey())
                return -1;

            return 0;
        }
    }
}
