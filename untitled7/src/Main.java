import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.function.IntPredicate;
import java.util.function.BiFunction;
import java.util.function.Function;
public class Main {
    public static void main(String[] args) {
        System.out.println("Выберите задание (1-4):");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                // 1
                zadanie1();
                System.out.println("Powered by ak1ne");
                break;
            case 2:
                // 2
                zadanie2();
                System.out.println("Powered by ak1ne");
                break;
            case 3:
                // 3
                zadanie3();
                System.out.println("Powered by ak1ne");
                break;
            case 4:
                // 4
                zadanie4();
                System.out.println("Powered by ak1ne");
                break;
            default:
                System.out.println("Нет такого задания");
        }
    }
    static void zadanie1() {
        IntPredicate isLeap = year -> (year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0));
        BiFunction<LocalDate, LocalDate, Long> daysBetween = (d1, d2) -> ChronoUnit.DAYS.between(d1, d2);
        BiFunction<LocalDate, LocalDate, Long> fullWeeksBetween = (d1, d2) -> ChronoUnit.DAYS.between(d1, d2) / 7;
        Function<LocalDate, DayOfWeek> dayOfWeek = d -> d.getDayOfWeek();
        int year = 2025;
        System.out.println("Год " + year + (isLeap.test(year) ? " високосный" : " не високосный :("));
        LocalDate date1 = LocalDate.of(2025, 1, 1);
        LocalDate date2 = LocalDate.of(2025, 1, 6);
        System.out.println("Количество дней между " + date1 + " и " + date2 + ": " + daysBetween.apply(date1, date2));
        System.out.println("Количество полных недель между " + date1 + " и " + date2 + ": " + fullWeeksBetween.apply(date1, date2));
        LocalDate date3 = LocalDate.of(1984, 6, 8);
        DayOfWeek dw = dayOfWeek.apply(date3);
        String dayName = switch (dw) {
            case MONDAY -> "понедельник";
            case TUESDAY -> "вторник";
            case WEDNESDAY -> "среда";
            case THURSDAY -> "четверг";
            case FRIDAY -> "пятница";
            case SATURDAY -> "суббота";
            case SUNDAY -> "воскресенье";
        };
        System.out.println("День недели для " + date3 + ": " + dayName);
    }
    static void zadanie2() {
        Four<Integer> maxOfFour = (a, b, c, d) -> {
            Integer m = a;
            if (b.compareTo(m) > 0) m = b;
            if (c.compareTo(m) > 0) m = c;
            if (d.compareTo(m) > 0) m = d;
            return m;
        };
        Four<Integer> minOfFour = (a, b, c, d) -> {
            Integer m = a;
            if (b.compareTo(m) < 0) m = b;
            if (c.compareTo(m) < 0) m = c;
            if (d.compareTo(m) < 0) m = d;
            return m;
        };
        int a = 15, b = 5, c = 10, d = 92;
        System.out.println("Максимум из (" + a + "," + b + "," + c + "," + d + "): " + maxOfFour.op(a, b, c, d));
        System.out.println("Минимум из (" + a + "," + b + "," + c + "," + d + "): " + minOfFour.op(a, b, c, d));
    }
    static void zadanie3() {
        int[] arr = { -3, -1, 0, 4, 3, 11, 15, 2, -2, 7 };
        ArraySummator sumIf = (array, cond) -> {
            int s = 0;
            for (int x : array) {
                if (cond.test(x)) s += x;
            }
            return s;
        };
        IntCondition equalsFive = x -> x == 5;
        IntCondition notInRange = x -> x < 2 || x > 10;
        IntCondition isPositive = x -> x > 0;
        IntCondition isNegative = x -> x < 0;
        System.out.println("Сумма равных 5: " + sumIf.sum(arr, equalsFive));
        System.out.println("Сумма чисел вне диапазона [2..10]: " + sumIf.sum(arr, notInRange));
        System.out.println("Сумма положительных: " + sumIf.sum(arr, isPositive));
        System.out.println("Сумма отрицательных: " + sumIf.sum(arr, isNegative));
    }
    static void zadanie4() {
        Catalog catalog = new Catalog();
        catalog.testInit();
        catalog.printAll();
        System.out.println("\nДобавим конкретную книгу:");
        catalog.addSpecific("Book");
        catalog.printAll();
        System.out.println("\nДобавим случайный объект:");
        catalog.addRandom();
        catalog.printAll();
        System.out.println("\nУдалим объект по названию (Book #1):");
        catalog.removeByName("Book #1");
        catalog.printAll();
        System.out.println("\nПоиск по названию (Gazette #1):");
        catalog.searchByName("Gazette #1");
        System.out.println("\nПоиск по автору (Author2):");
        catalog.searchByAuthor("Author2");
    }
    interface Four<T extends Comparable<T>> {
        T op(T a, T b, T c, T d);
    }
    interface IntCondition {
        boolean test(int x);
    }
    interface ArraySummator {
        int sum(int[] array, IntCondition cond);
    }
    interface Publication {
        String getName();
        void print();
    }
    static class Book implements Publication {
        String author;
        String name;
        String genre;
        int pages;
        Book(String author, String name, String genre, int pages) {
            this.author = author;
            this.name = name;
            this.genre = genre;
            this.pages = pages;
        }
        public String getName() { return name; }
        public void print() {
            System.out.println("Книга: " + name + ", Автор: " + author + ", Жанр: " + genre + ", Страниц: " + pages);
        }
        public String getAuthor() { return author; }
    }
    static class Newspaper implements Publication {
        String name;
        LocalDate date;
        List<String> titles;
        Newspaper(String name, LocalDate date, List<String> titles) {
            this.name = name;
            this.date = date;
            this.titles = titles;
        }
        public String getName() { return name; }
        public void print() {
            System.out.println("Газета: " + name + ", Дата: " + date + ", Заголовки: " + titles);
        }
    }
    static class Almanac implements Publication {
        String name;
        List<Book> books;
        Almanac(String name, List<Book> books) {
            this.name = name;
            this.books = books;
        }
        public String getName() { return name; }
        public void print() {
            System.out.println("Альманах: " + name + ", Книги внутри:");
            for (Book b : books) {
                b.print();
            }
        }
    }
    static class Catalog {
        List<Publication> items = new ArrayList<>();
        void testInit() {
            items.add(new Book("Author1", "Book #1", "Роман", 200));
            items.add(new Book("Author2", "Book #2", "Фантастика", 300));
            List<String> gazTitles = new ArrayList<>();
            gazTitles.add("Заголовок1");
            gazTitles.add("Заголовок2");
            items.add(new Newspaper("Gazette #1", LocalDate.of(2023, 1, 1), gazTitles));
            List<Book> almanacBooks = new ArrayList<>();
            almanacBooks.add(new Book("Author3", "SubBook", "Поэзия", 100));
            items.add(new Almanac("Almanac #1", almanacBooks));
        }
        void addSpecific(String type) {
            if (type.equalsIgnoreCase("Book")) {
                items.add(new Book("NewAuthor", "NewBook", "Жанр", 150));
            } else if (type.equalsIgnoreCase("Newspaper")) {
                List<String> t = new ArrayList<>();
                t.add("Свежий заголовок");
                items.add(new Newspaper("NewGazette", LocalDate.now(), t));
            } else {
                List<Book> ab = new ArrayList<>();
                ab.add(new Book("NewAuthorA", "NewBookA", "ЖанрA", 200));
                items.add(new Almanac("NewAlmanac", ab));
            }
        }
        void addRandom() {
            Random r = new Random();
            int x = r.nextInt(3);
            if (x == 0) {
                addSpecific("Book");
            } else if (x == 1) {
                addSpecific("Newspaper");
            } else {
                addSpecific("Almanac");
            }
        }
        void removeByName(String name) {
            items.removeIf(i -> i.getName().equals(name));
        }
        void printAll() {
            System.out.println("Текущий каталог:");
            for (Publication p : items) {
                p.print();
            }
        }
        void searchByName(String n) {
            for (Publication p : items) {
                if (p.getName().equals(n)) {
                    p.print();
                }
            }
        }
        void searchByAuthor(String a) {
            for (Publication p : items) {
                if (p instanceof Book) {
                    Book b = (Book) p;
                    if (b.getAuthor().equals(a)) {
                        b.print();
                    }
                }
            }
        }
    }
}
