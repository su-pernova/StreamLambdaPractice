import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamCollectors {

    public static void main(String[] args) {
        List<Person> personList = List.of(
                new Person("A", 30),
                new Person("B", 30),
                new Person("B", 50),
                new Person("D", 40)
        );

        // Collectors.summarizingInt
            // IntSummaryStatistics 클래스 내부 메소드
            // Int 뿐만 아니라 Double, Long 타입도 지원한다
            // 요런 builtin 함수들로 min, max 등의 통계값을 스트림에서 한번에 처리할 수 있음
        long sumSum = personList.stream().collect(Collectors.summarizingInt(Person::getAge)).getSum();
        double sumAverage = personList.stream().collect(Collectors.summarizingInt(Person::getAge)).getAverage();
        long sumCount = personList.stream().collect(Collectors.summarizingInt(Person::getAge)).getCount();
        long sumMax = personList.stream().collect(Collectors.summarizingInt(Person::getAge)).getMax();
        long sumMin = personList.stream().collect(Collectors.summarizingInt(Person::getAge)).getMin();
        System.out.println("< Collectors.summarizingInt >");
        System.out.println("sum : " + sumSum);
        System.out.println("average : " + sumAverage);
        System.out.println("count : " + sumCount);
        System.out.println("max : " + sumMax);
        System.out.println("min : " + sumMin);

        // Collectors.summingInt
        Integer summingIntSum = personList.stream().collect(Collectors.summingInt(Person::getAge));
        int mapToIntSum = personList.stream().mapToInt(Person::getAge).sum();
        System.out.println("\n< Collectors.summingInt >");
        System.out.println(summingIntSum);
        System.out.println("(mapToIntSum : " + mapToIntSum + ")");

        // Collectors.averagingInt
        Double averagingInt = personList.stream().collect(Collectors.averagingInt(Person::getAge));
        System.out.println("\n< Collectors.averagingInt >");
        System.out.println(averagingInt);

        // Collectors.groupingBy
            //  파라미터로 그룹핑 할 기준을 정해주면 해당 기준으로 데이터를 그룹핑
        Map<String, List<Person>> collectByName = personList.stream().collect(Collectors.groupingBy(Person::getName));
        Map<Integer, List<Person>> collectByAge = personList.stream().collect(Collectors.groupingBy(Person::getAge));
        System.out.println("\n< Collectors.groupingBy >");
        System.out.println(collectByName);
        System.out.println(collectByAge);

        // Collectors.partitioningBy
        // 파라미터로 Predicate 을 받고 해당 조건의 True/False 결과 기준 데이터를 두 그룹으로 나눈다.
        Map<Boolean, List<Person>> partitioningByName = personList.stream()
                .collect(Collectors.partitioningBy(person -> person.getName().equals("B")));
        Map<Boolean, List<Person>> partitioningByAge = personList.stream()
                .collect(Collectors.partitioningBy(person -> person.getAge() > 30));
        System.out.println("\n< Collectors.partitioningBy >");
        System.out.println(partitioningByName);
        System.out.println(partitioningByAge);
    }

}
