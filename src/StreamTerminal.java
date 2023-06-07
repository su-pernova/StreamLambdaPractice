import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StreamTerminal {

    public static void main(String[] args) {
        List<String> stringList = List.of("B", "D", "C", "A");
        List<Integer> duplicateList = List.of(1,1,1,2,2,2,3,3,3);
        List<Person> personList = List.of(
                new Person("A", 30),
                new Person("B", 20),
                new Person("B", 50),
                new Person("D", 40)
        );

        // Stream max, min
            // primitive type : max()에 파라미터가 없고 getAsInt()를 이용해 반환 받을 수 있다.
                // mapToInt 를 해주는 이유 : map 반환 타입 = Stream<Integer> / mapToInt 반환 타입 = IntStream
            // reference type : max()에 Comparator 가 파라미터로 들어간다.
        int maxIntAge = personList.stream()
                .mapToInt(Person::getAge)
                .max().getAsInt();
        int maxIntegerAge = personList.stream()
                .map(Person::getAge)
                .max(Integer::compare)
                .get();
        System.out.println("< Stream max >");
        System.out.printf("%d, %d\n", maxIntAge, maxIntegerAge);

        // Stream average
        double averageAge = personList.stream()
                .mapToInt(Person::getAge)
                .average()
                .getAsDouble();
        System.out.println("\n< Stream average >");
        System.out.println(averageAge);

        // Stream count
        long countFilteredAge = personList.stream()
                .map(Person::getAge)
                .filter(age -> age > 30)
                .count();
        System.out.println("\n< Stream count >");
        System.out.println(countFilteredAge);

        // Stream sum
            // mapToInt 로만 sum 사용 가능
            // map 을 쓰면 .sum 함수 옵션 사용이 불가
        int ageSum = personList.stream()
                .mapToInt(Person::getAge)
                .sum();
        System.out.println("\n< Stream sum >");
        System.out.println(ageSum);

        // Stream reduce
            // 종이접기! 라고 생각하면 쉽다.
            // 30, 40, 50 -> 70, 50 -> 120
        int result1 = personList.stream()
                .map(Person::getAge)
                .filter(age -> age >20)
                .reduce(Integer::sum).get();
        System.out.println("\n< Stream reduce >");
        System.out.println(result1);

            // 초기 시작이 10, 30, 40, 50 가 된다고 생각하면 됨
            // identity : initial value + default result if stream is empty
        int result2 = personList.stream()
                .map(Person::getAge)
                .filter(age -> age >20)
                .reduce(10, Integer::sum);
        System.out.println(result2);

            // A, B, C, D -> A!B, C, D -> A!B!C, D -> A!B!C!D
        String result3 = personList.stream()
                .map(Person::getName)
                .reduce((a, b) -> String.join("!", a, b)).get();
        System.out.println(result3);

        // Stream match
            // anyMatch : 조건을 충족하는 요소가 하나라도 있는 경우 true
            // allMatch : 모든 요소가 조건을 충족하는 경우 true
            // noneMatch : 모든 요소가 조건을 충족하지 않는 경우 true
        boolean anyMatch = personList.stream().anyMatch(person -> person.getName().equals("A"));
        boolean allMatch = personList.stream().allMatch(person -> person.getAge() >= 10);
        boolean noneMatch = personList.stream().noneMatch(person -> person.getName().equals("A"));
        System.out.println("\n< Stream match >");
        System.out.printf("anyMatch : %s\n", anyMatch);
        System.out.printf("allMatch : %s\n", allMatch);
        System.out.printf("noneMatch : %s\n", noneMatch);

            // Stream anyMatch : true 가 되는 조건을 만나면 즉시 break 됨 (사용시 주의할 것)
        System.out.println("- - - - - - - - - - -");
        System.out.println("[count]"); // stringList 의 모든 요소를 순회한다.
        stringList.stream().filter(StreamTerminal::circuitTest).count();
        System.out.println("- - - - - - - - - - -");
        System.out.println("[anyMatch]"); // stringList 의 요소 중 조건을 만족하는 요소를 만나면 순회를 중단한다.
        stringList.stream().anyMatch(StreamTerminal::circuitTest);
        System.out.println("- - - - - - - - - - -");
        System.out.println("[filter + anyMatch]"); // filter 와 함께 쓰여도 마찬가지
        duplicateList.stream().filter(i -> i < 2).anyMatch(StreamTerminal::circuitTest);

        // Stream collect
            // Collectors.toList(), toMap(), toSet() : 스트림 연산 결과를 List, Map, Set 으로 변환
        List<String> nameList = personList.stream()
                .map(Person::getName)
                .collect(Collectors.toList());
        System.out.println("\n< Stream collect >");
        System.out.println(nameList);
        Set<String> nameSet = personList.stream()
                .map(Person::getName)
                .collect(Collectors.toSet());
        System.out.println(nameSet);
            // 키가 중복인 경우 에러가 발생하므로, 람다식을 이용해 중복된 키의 이전 값을 새 값으로 업데이트
        Map<String, Integer> nameMap = personList.stream()
                .collect(Collectors.toMap(Person::getName, Person::getAge, (oldValue, newValue) -> newValue));
        System.out.println(nameMap);

        // Stream foreach
            // 연산한 결과를 순회
        System.out.println("\n< Stream foreach >");
        personList.stream()
                .filter(person -> person.getAge() > 30)
                .forEach(person -> System.out.println(person.getName()));
    }

    static boolean circuitTest(String a) {
        System.out.println("executed" + a);
        return true;
    }

    static boolean circuitTest(int a) {
        System.out.println("executed" + a);
        return true;
    }

}
