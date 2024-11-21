import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Ввод данных с клавиатуры
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите Фамилию, Имя и Отчество по-русски: ");
        String full_name = sc.nextLine();
        System.out.print("Введите дату рождения (dd.mm.yyyy или dd/mm/yyyy): ");
        String birthdate = sc.nextLine();
        //Создание объекта
        Person a = new Person(full_name, birthdate);
        //Вывод
        System.out.println(a.getInitials());
        System.out.println(a.getGender());
        System.out.println(a.getAge());
    }
}
