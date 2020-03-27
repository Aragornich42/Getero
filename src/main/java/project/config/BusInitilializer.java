package project.config;

import net.progruzovik.bus.replication.Replicator;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import project.dao.StudentJdbc;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

//Класс, инициализирующий общение с шиной
@Component
@DependsOn("busHandler")
public class BusInitilializer {
    private final Replicator replicator;
    private final StudentJdbc studentJdbc;
    public BusInitilializer(Replicator replicator, StudentJdbc studentJdbc) {
        this.replicator = replicator;
        this.studentJdbc = studentJdbc;
    }
    //Инициализация общения с шиной через POST-запрос путем связывания с локальной
    //БД, хранящей сведения о студентах
    @PostConstruct
    public void init() throws IOException {
        initEntity("student", studentJdbc.GetAllLocal());
    }
    private <T> void initEntity(String name, List<T> data) throws IOException {
        replicator.initializeEntity(name);
        for (T row : data) {
            replicator.addRow(name, row);
        }
    }
}
