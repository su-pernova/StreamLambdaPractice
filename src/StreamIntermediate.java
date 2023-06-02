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
                .map(Person::getName)
                .collect(Collectors.toList());
        System.out.println("\n< Stream map >");
        System.out.println(nameList);

            // person class 의 age > 30 인 인스턴스의 name 필드만 모아 List 생성
        List<String> filteredNameList = personList.stream()
                .filter(person -> person.getAge() < 30)
                // Person::getName
                .map(Person::getName)
                .collect(Collectors.toList());
        System.out.println(filteredNameList);

            // person class 의 age 필드만 모아 Long List 생성
        List<Long> longAgeList = personList.stream()
                .mapToLong(Person::getAge)
                .boxed()
                .collect(Collectors.toList());
        System.out.println(longAgeList);

        // Stream flatmap : 2차원 배열, 리스트 같은 요소를 단일 스트림으로 변환
        List<String> flatmapList = personList.stream()
                .map(person -> person.getName().split(""))
                .flatMap(Arrays::stream) // splitList 내부 요소를 다시 stream 으로 변환
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
                .peek(peekStringList1::add)
                .map(string -> string + "2")
                .collect(Collectors.toList());
        System.out.println("\n< Stream peek >");
        System.out.println(peekStringList1);
        System.out.println(peekStringList2);

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
