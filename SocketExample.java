package tuan8;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

class Student implements Serializable{
    String name;
    int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" + "name=" + name + ", age=" + age + '}';
    }
    
    
}

public class SocketExample {
    public static class Listener implements Runnable{
        @Override
        public void run() {
            try {
                ServerSocket ss = new ServerSocket(8888);
                Socket socket = ss.accept();
                Student student = new Student("Nguyen Van A ", 25);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(student);
                oos.close();
                socket.close();
                ss.close();
            } catch (Exception e) {
            }
        }
        
    }
    public static void main(String[] args) {
        new Thread(new SocketExample.Listener()).start();
        try {
            Socket socket = new Socket("localhost", 8888);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Student studentReceive = (Student) ois.readObject();
            System.out.println("Receive : " + studentReceive.toString());
            ois.close();
            socket.close();
        } catch (Exception e) {
        }
    }
    
}
