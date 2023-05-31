public class Person {
    private String name;
    private int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    String getName() { return this.name; }
    int getAge() { return this.age; }
}
