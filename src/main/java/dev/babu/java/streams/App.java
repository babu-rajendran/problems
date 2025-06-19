package dev.babu.java.streams;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {

    static List<Integer> listOfIntegers = List.of(4, 6, 7, 3, 8);
    static List<String> listOfStrings = List.of("apple", "banana", "kiwi", "mango", "orange");
    static List<Employee> listOfEmployees = List.of(
            new Employee(4, "Alan", "DEV", 5000.00),
            new Employee(5, "Bob", "QA", 4000.00),
            new Employee(6, "Carla", "DEV", 6000.00),
            new Employee(7, "David", "DevOps", 5500.00));

    static double calculateAverage() {
        return listOfIntegers.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
    }

    static int calculateEvenSum() {
        return listOfIntegers.stream().filter(n -> ((n % 2) == 0)).mapToInt(Integer::intValue).sum();
    }

    static int calculateOddSum() {
        return listOfIntegers.stream().filter(n -> ((n % 2) != 0)).mapToInt(Integer::intValue).sum();
    }

    static List<String> convertToUpper() {
        return listOfStrings.stream().map(String::toUpperCase).toList();
    }

    // Given a list of Employees, return List of names of the employees.
    static List<String> getListOfEmployeeNames() {
        return listOfEmployees.stream().map(Employee::getName).toList();
    }

    // Given a list of employees, return as map with employee id as key and employee as value.
    static Map<Integer, Employee> getMapOfEmployeesById() {
        return listOfEmployees.stream().collect(Collectors.toMap(Employee::getId, employee -> employee));
    }

    // Given a list of employees, return Employees by department name
    static Map<String, List<Employee >> getMapOfEmployeesByDepartment() {
        return listOfEmployees.stream().collect(Collectors.groupingBy(Employee::getDept));
    }

    // Given a list of employees, return list of Employee names by department name
    static Map<String, List<String>> getMapOfEmployeeNamesByDepartment() {
        return listOfEmployees.stream().collect(
                Collectors.groupingBy(Employee::getDept,
                        Collectors.mapping(Employee::getName, Collectors.toList())));
    }

    // Given a list of employees, print Department wise average salary
    static Map<String, Double> getAverageSalaryByDepartment() {
        return listOfEmployees.stream().collect(
                Collectors.groupingBy(Employee::getDept, Collectors.averagingDouble(Employee::getSalary)));
    }

    public static void main(String[] args) {
        System.out.println("Average value of the integers: " + calculateAverage());
        System.out.println("Fruits in upper case: " + convertToUpper());
        System.out.println("Even Sum: " + calculateEvenSum());
        System.out.println("Odd Sum: " + calculateOddSum());
        System.out.println("Employee names: " + getListOfEmployeeNames());
        System.out.println("Employee Map by id: " + getMapOfEmployeesById());
        System.out.println("Employees grouped by department: " + getMapOfEmployeesByDepartment());
        System.out.println("Employee names grouped by department: " + getMapOfEmployeeNamesByDepartment());
        System.out.println("Average salary by department: " + getAverageSalaryByDepartment());
    }
}
