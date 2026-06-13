package application;

import dao.ConsultaDao;
import entities.Consulta;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ConsultaDao dao = new ConsultaDao();

        dao.deleteById(13);

        System.out.println("Consulta deletada!");
    }
}