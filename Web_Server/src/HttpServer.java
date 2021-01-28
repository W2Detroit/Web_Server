import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) { //Создаем объект класса ServerSocket и назначаем ему порт
            System.out.println("Server started!"); //выводит, что сервер запущен

            while (true) {
                // ожидаем подключения
                Socket socket = serverSocket.accept(); //Создаем объект класса Socket, который налаживает связь сервера и клиента
                System.out.println("Client connected!");

                // для подключившегося клиента открываем потоки чтения и записи
                try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                     PrintWriter output = new PrintWriter(socket.getOutputStream())) { //вывод данных на консоль и считывание данных с нее

                    // ждем первой строки запроса
                    while (!input.ready()) ;

                    // считываем и печатаем все что было отправлено клиентом
                    System.out.println();
                    while (input.ready()) {
                        System.out.println(input.readLine());
                    }

                    // отправляем ответ
                    output.println("HTTP/1.1 200 OK");
                    output.println("Content-Type: text/html; charset=utf-8");
                    output.println();
                    output.println("<p>Я вас категорически приветствую!</p>");
                    output.flush();

                    // по окончанию выполнения блока try-with-resources потоки, 
                    // а вместе с ними и соединение будут закрыты
                    System.out.println("Client disconnected!");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}