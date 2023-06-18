public class Client {
    public static void main(String[] args) {
        Student s1 = new Student();
        s1.age = 10;
        s1.name = "A";
 
        Student s2 = new Student();
        s2.age = 20;
        s2.name = "B";
 
        swap(s1, s2);
 
        s1.display();
        s2.display();
    }
 
    private static void swap(Student s1, Student s2) {
         Student temp = s1;
         s1 = s2;
         s2 = temp;
    }
 }