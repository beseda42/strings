import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс человека с ФИО, полом и датой рождения
 */
public class Person {
    /** Фамилия */
    private String surname;
    /** Имя */
    private String name;
    /** Отчество */
    private String patronymic;
    /** Дата рождения в формате dd.mm.yyyy или dd/mm/yyyy */
    private String birthdate;
    /** Пол */
    private String gender;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param full_name ФИО
     * @param birthdate Дата рождения в формате dd.mm.yyyy или dd/mm/yyyy
     * @throws IllegalArgumentException неверный формат ФИО или даты рождения
     * @see #set_full_name(String) Установка атрибутов ФИО
     * @see #set_gender_default() Установка атрибута пола
     * @see #set_birthdate(String) Установка атрибута даты рождения
     */
    public Person (String full_name, String birthdate) throws IllegalArgumentException{
        this.set_full_name(full_name);

        this.set_gender_default();

        this.set_birthdate(birthdate);
    }

    /**
     * Задает атрибуты фамилии, имени и отчества на основе ФИО
     *
     * @param full_name ФИО
     * @throws IllegalArgumentException Неверный формат ФИО
     */
    private void set_full_name(String full_name) throws IllegalArgumentException{
        String[] elems = full_name.trim().split("\\s+");
        if (elems.length > 3){throw new IllegalArgumentException ("Неверный формат ФИО. Больше трех элементов");}
        if (elems.length < 2){throw new IllegalArgumentException ("Неверный формат ФИО. Меньше двух элементов");}

        this.surname = elems[0];
        this.name = elems[1];
        if (elems.length == 3){this.patronymic = elems[2];}
        else{this.patronymic = null;}

    }

    /**
     * Задает объекту пол (атрибут gender) в соответствии с окончанием отчества
     */
    private void set_gender_default(){
        if (patronymic == null) {gender = "ОПРЕДЕЛИТЬ_НЕ_УДАЛОСЬ"; return;}

        String[] female_endings = {"овна", "евна", "ична", "инична"};

        if (patronymic.endsWith("ич")){
            gender = "М";
            return;
        }

        for (String ending : female_endings) {
            if (patronymic.endsWith(ending)){
                gender = "Ж";
                return;
            }
        }

        gender = "ОПРЕДЕЛИТЬ_НЕ_УДАЛОСЬ";
    }

    /**
     * Задает атрибут даты рождения
     *
     * @param birthdate Дата рождения в формате dd.mm.yyyy или dd/mm/yyyy
     * @throws IllegalArgumentException Неверный формат даты рождеения
     */
    private void set_birthdate(String birthdate) throws IllegalArgumentException{
        this.birthdate = birthdate.replace("/", ".");

        if (!isValidDate(this.birthdate)){throw new IllegalArgumentException ("Неверный формат даты рождения. Должно быть dd.mm.yyyy или dd/mm/yyyy");}
        if (LocalDate.parse(this.birthdate, DateTimeFormatter.ofPattern("dd.MM.yyyy")).isAfter(LocalDate.now())){throw new IllegalArgumentException("Дата ещё не наступила");}

    }

    /**
     * Проверка даты рождения на правильный формат dd.MM.yyyy
     *
     * @param date Дата
     * @return Соответствует ли дата формату dd.MM.yyyy
     */
    private static boolean isValidDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {LocalDate date_ = LocalDate.parse(date, formatter); return true;}
        catch (DateTimeParseException e) {return false;}
    }

    /**@return ФИО*/
    public String getFull_name(){return (this.surname + " " + this.name + " " + this.patronymic);}
    /**@return Фамилия*/
    public String getSurname() {return this.surname;}
    /**@return Имя*/
    public String getName() {return this.name;}
    /**@return Отчество*/
    public String getPatronymic() {return this.patronymic;}
    /**@return Дата рождения*/
    public String getBirthdate() {return this.birthdate;}
    /**@return Пол*/
    public String getGender() {return this.gender;}

    /**
     * @return инициалы вида <Фамилия И.О.>
     */
    public String getInitials() {
        if (patronymic == null) {return (this.surname + " " + name.charAt(0) + ".");}
        else{return (this.surname + " " + name.charAt(0) + "." + patronymic.charAt(0) + ".");}
    }

    /**
     * @return возраст вида <Число лет> с правильным окончанием
     */
    public String getAge(){
        Map<Integer, String> ageMap = new HashMap<>();
        ageMap.put(0, "лет");
        ageMap.put(1, "год");
        ageMap.put(2, "года");
        ageMap.put(3, "года");
        ageMap.put(4, "года");
        ageMap.put(5, "лет");
        ageMap.put(6, "лет");
        ageMap.put(7, "лет");
        ageMap.put(8, "лет");
        ageMap.put(9, "лет");
        ageMap.put(10, "лет");
        ageMap.put(11, "лет");
        ageMap.put(12, "лет");
        ageMap.put(13, "лет");
        ageMap.put(14, "лет");

        LocalDate date = LocalDate.parse(this.birthdate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        int age = (int) ((Instant.now().getEpochSecond() - date.atStartOfDay(ZoneOffset.UTC).toEpochSecond()) / 31557600);

        if (age < 15){return String.valueOf(age) + " " + ageMap.get(age);}
        else{return String.valueOf(age) + " " + ageMap.get(age%10);}
    }

}
