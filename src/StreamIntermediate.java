import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamIntermediate {

    public static void main(String[] args) {

        List<String> stringList = List.of("B", "D", "C", "A");
        List<Person> personList = List.of(
                new Person("Ten", 10),
                new Person("Twenty", 20),
                new Person("Thirty", 30)
        );
        List<Integer> duplicateList = List.of(1,1,1,2,2,2,3,3,3);
        int[] primitiveIntList = {1,2,3,4,5,6,7,8,9};

        // Stream sorted : 정렬
        List<String> sortedList = stringList.stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("< Stream sorted >");
        System.out.println(sortedList);

        // Stream filter : 조건에 맞는 값만 처리
        List<String> filteredList = stringList.stream()
                .filter(string -> string.equals("A"))
                .collect(Collectors.toList());
        System.out.println("\n< Stream filter >");
        System.out.println(filteredList);

        // Stream map : 스트림 내부 요소들을 새로운 형태에 매핑
            // person class 의 name 필드만 모아 List 생성
        List<String> nameList = personList.stream()
                .map(person -> person.getName())
                .collect(Collectors.toList());
        System.out.println("\n< Stream map >");
        System.out.println(nameList);

            // person class 의 age > 30 인 인스턴스의 name 필드만 모아 List 생성
        List<String> filteredNameList = personList.stream()
                .filter(person -> person.getAge() < 30)
                // Person::getName
                .map(person -> person.getName())
                .collect(Collectors.toList());
        System.out.println(filteredNameList);

            // person class 의 age 필드만 모아 Long List 생성
        List<Long> longAgeList = personList.stream()
                .mapToLong(person -> person.getAge())
                .boxed()
                .collect(Collectors.toList());
        System.out.println(longAgeList);

        // Stream flatmap : 2차원 배열, 리스트 같은 요소를 단일 스트림으로 변환
        List<String> flatmapList = personList.stream()
                .map(person -> person.getName().split(""))
                .flatMap(splitList -> Arrays.stream(splitList)) // splitList 내부 요소를 다시 stream 으로 변환
                .collect(Collectors.toList());
        System.out.println("\n< Stream flatmap >");
        System.out.println(flatmapList);

        // Stream distinct : 중복 요소 제거
        List<Integer> removeDuplicateList = duplicateList.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("\n< Stream distinct >");
        System.out.println(removeDuplicateList);

        // Stream limit : 가장 앞 요소부터 지정한 개수까지
        List<String> limitStringList = stringList.stream()
                .sorted()
                .limit(2)
                .collect(Collectors.toList());
        System.out.println("\n< Stream limit >");
        System.out.println(limitStringList);

        // Stream skip : 지정한 개수만큼 뛰어넘은 요소부터
        List<String> skipStringList = stringList.stream()
                .sorted()
                .skip(2)
                .collect(Collectors.toList());
        System.out.println("\n< Stream skip >");
        System.out.println(skipStringList);

        // Stream peek : 지금까지 연산된 요소들로 로직 수행 가능
        List<String> peekStringList1 = new ArrayList<>();
        List<String> peekStringList2 = stringList.stream()
                .map(string -> string + "1")
                .peek(string -> peekStringList1.add(string))
                .map(string -> string + "2")
                .collect(Collectors.toList());
        System.out.println("\n< Stream peek >");
        System.out.println(peekStringList1);
        System.out.println(peekStringList2);

        // Stream anyMatch : true 가 되는 조건을 만나면 즉시 break 됨 (사용시 주의할 것)
        System.out.println("\n< count >"); // stringList 의 모든 요소를 순회한다.
        stringList.stream().filter(StreamIntermediate::circuitTest).count();
        System.out.println("- - - - - - - - - - -");
        System.out.println("< anyMatch1 >"); // stringList 의 요소 중 조건을 만족하는 요소를 만나면 순회를 중단한다.
        stringList.stream().anyMatch(StreamIntermediate::circuitTest);
        System.out.println("- - - - - - - - - - -");
        System.out.println("< anyMatch2 >"); // filter 와 함께 쓰여도 마찬가지
        duplicateList.stream().filter(i -> i < 2).anyMatch(StreamIntermediate::circuitTest);

        // Stream boxed
        List<Integer> boxedList = Arrays.stream(primitiveIntList)
                .boxed()
                .collect(Collectors.toList());
        System.out.println("\n< Stream boxed >");
        System.out.println(boxedList);
    }

    static boolean circuitTest(String a) {
        System.out.println("executed");
        return true;
    }

    static boolean circuitTest(int a) {
        System.out.println("executed");
        return true;
    }

}
