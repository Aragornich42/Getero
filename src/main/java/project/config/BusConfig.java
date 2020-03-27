package project.config;

        import com.fasterxml.jackson.databind.ObjectMapper;
        import net.progruzovik.bus.dao.EntityDao;
        import net.progruzovik.bus.dao.EntityJdbc;
        import net.progruzovik.bus.dao.InstanceDao;
        import net.progruzovik.bus.dao.InstanceJdbc;
        import net.progruzovik.bus.message.*;
        import net.progruzovik.bus.replication.ReplicationService;
        import net.progruzovik.bus.replication.Replicator;
        import net.progruzovik.bus.util.CharBufferNameConverter;
        import net.progruzovik.bus.util.EntityNameConverter;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.context.annotation.PropertySource;
        import org.springframework.jdbc.core.JdbcTemplate;
        import org.springframework.scheduling.annotation.EnableScheduling;
        import org.springframework.web.client.RestTemplate;

//Класс, конфигурирующий общение с шиной
@Configuration
@EnableScheduling
@PropertySource("classpath:bus.properties")
public class BusConfig {
    //Извлекает значение адреса локальной БД
    @Value("${bus.instance.address}")
    private String address;
    //Извлекает значение адреса удаленной БД
    @Value("${bus.dean.address}")
    private String deanAddress;
    //URL для интеграции значений
    @Value("${bus.integration.url}")
    private String integrationUrl;

    //Возвращает сервис, отвечающий за репликацию данных в шине
    @Bean
    public Replicator replicator(ObjectMapper mapper, Writer writer, InstanceDao
            instanceDao, EntityDao entityDao) {
        return new ReplicationService(mapper, writer, instanceDao, entityDao);
    }

    //Возвращает класс, отвечающий за REST-обращения к шине
    @Bean
    public RestReceiver restReceiver(RestTemplate restTemplate, BusHandler
            busHandler) {
        return new BusRestReceiver(integrationUrl, restTemplate, busHandler);
    }

    //Заголовок сообщений для шины
    @Bean
    public BusHandler busHandler(ObjectMapper mapper, Writer writer, InstanceDao
            instanceDao, EntityDao entityDao) {
        return new MessageHandler(mapper, writer, instanceDao, entityDao,
                deanAddress);
    }

    //Возвращает класс, отвечающий за запись данных
    @Bean
    public Writer writer(ObjectMapper mapper, MessageSender messageSender,
                         InstanceDao instanceDao) {
        return new MessageWriter(mapper, messageSender, instanceDao);
    }

    //Шаблон REST-вызовов
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    //Класс, отвечающий за посылку сообщений
    @Bean
    public MessageSender messageSender(RestTemplate restTemplate) {
        return new RestSender(address, integrationUrl, restTemplate);
    }

    //Конвертация названия сущности
    @Bean
    public EntityNameConverter entityNameConverter() {
        return new CharBufferNameConverter();
    }

    //DAO для локальной БД
    @Bean
    public InstanceDao instanceDao(EntityNameConverter entityNameConverter,
                                   JdbcTemplate jdbcTemplate) {
        return new InstanceJdbc(entityNameConverter, jdbcTemplate);
    }

    //DAO для шины
    @Bean
    public EntityDao entityDao(EntityNameConverter entityNameConverter, JdbcTemplate
            jdbcTemplate) {
        return new EntityJdbc(entityNameConverter, jdbcTemplate);
    }
}
