package azaila.community.config;

import azaila.community.enums.EstadoEvento;
import azaila.community.enums.Dieta;
import azaila.community.enums.PrioridadDiscapacidad;
import azaila.community.model.*;
import azaila.community.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class Data implements CommandLineRunner {

    private final OrganizadorRepository organizadorRepository;
    private final ParticipanteRepository participanteRepository;
    private final StaffRepository staffRepository;
    private final EventoRepository eventoRepository;

    public Data(OrganizadorRepository organizadorRepository, ParticipanteRepository participanteRepository,
                StaffRepository staffRepository, EventoRepository eventoRepository) {
        this.organizadorRepository = organizadorRepository;
        this.participanteRepository = participanteRepository;
        this.staffRepository = staffRepository;
        this.eventoRepository = eventoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        eventoRepository.deleteAll();
        organizadorRepository.deleteAll();
        participanteRepository.deleteAll();
        staffRepository.deleteAll();

        // 1. Crear entidades de personas con sus roles iniciales
        // Paquita es Organizadora y Staff, pero la creamos solo una vez con un email
        Organizador paquitaOrg = new Organizador();
        paquitaOrg.setNombreCompleto("Paquita La del Barrio");
        paquitaOrg.setEmail("paquita@azaila.com"); // Email único
        paquitaOrg.setPassword("pass123");
        paquitaOrg.setTelefono("600111222");
        paquitaOrg.setRanking(10);
        organizadorRepository.save(paquitaOrg);

        // Antonio es Organizador y Participante
        Organizador antonioOrg = new Organizador();
        antonioOrg.setNombreCompleto("Antonio Banderas");
        antonioOrg.setEmail("antonio@azaila.com"); // Email único
        antonioOrg.setPassword("pass456");
        antonioOrg.setTelefono("600333444");
        antonioOrg.setRanking(8);
        organizadorRepository.save(antonioOrg);

        // Pepe es solo Staff
        Staff pepe = new Staff();
        pepe.setNombreCompleto("Pepe Rodriguez");
        pepe.setEmail("pepe@azaila.com"); // Email único
        pepe.setPassword("pass789");
        pepe.setNumeroStaff(1);
        staffRepository.save(pepe);

        // María es solo Staff
        Staff maria = new Staff();
        maria.setNombreCompleto("Maria Lopez");
        maria.setTelefono("600555666");
        maria.setEmail("maria@azaila.com"); // Email único
        maria.setNumeroStaff(2);
        staffRepository.save(maria);

        // Paco es solo Participante
        Participante paco = new Participante();
        paco.setNombreCompleto("Paco Fernandez");
        paco.setEmail("paco@azaila.com"); // Email único
        paco.setPassword("passabc");
        paco.setDieta(Dieta.VEGETARIANA);
        participanteRepository.save(paco);

        // Ana es solo Participante
        Participante ana = new Participante();
        ana.setNombreCompleto("Ana Garcia");
        ana.setEmail("ana@azaila.com"); // Email único
        ana.setPassword("passdef");
        ana.setPrioridadDiscapacidad(PrioridadDiscapacidad.VISUAL);
        participanteRepository.save(ana);

        // 2. Para Paquita, si quieres que también sea Staff, crea su objeto Staff
        Staff paquitaAsStaff = new Staff();
        paquitaAsStaff.setNombreCompleto(paquitaOrg.getNombreCompleto());
        paquitaAsStaff.setEmail("paquita-staff@azaila.com"); // ¡Email diferente para evitar duplicados!
        paquitaAsStaff.setPassword(paquitaOrg.getPassword());
        paquitaAsStaff.setTelefono(paquitaOrg.getTelefono());
        paquitaAsStaff.setNumeroStaff(3);
        staffRepository.save(paquitaAsStaff);

        // 3. Para Antonio, si quieres que también sea Participante, crea su objeto Participante
        Participante antonioAsParticipante = new Participante();
        antonioAsParticipante.setNombreCompleto(antonioOrg.getNombreCompleto());
        antonioAsParticipante.setEmail("antonio-part@azaila.com"); // ¡Email diferente para evitar duplicados!
        antonioAsParticipante.setPassword(antonioOrg.getPassword());
        antonioAsParticipante.setTelefono(antonioOrg.getTelefono());
        participanteRepository.save(antonioAsParticipante);


        // 3. Crear Eventos y asignarles los roles
        // Evento 1: "Huerto Comunitario"
        Evento huertoComunitario = new Evento();
        huertoComunitario.setTitulo("Huerto Comunitario");
        huertoComunitario.setDescripcion("Tarde de trabajo y compañerismo en el huerto municipal.");
        huertoComunitario.setUbicacion("Huerto Municipal");
        huertoComunitario.setFecha(LocalDate.of(2025, 10, 20));
        huertoComunitario.setHora(LocalTime.of(17, 0));
        huertoComunitario.setRequiereInscripcion(true);
        huertoComunitario.setOrganizador(paquitaOrg);
        huertoComunitario.setEstado(EstadoEvento.APROBADO);

        Set<Participante> participantesHuerto = new HashSet<>();
        participantesHuerto.add(paco);
        participantesHuerto.add(ana);
        huertoComunitario.setParticipantes(participantesHuerto);

        Set<Staff> staffHuerto = new HashSet<>();
        staffHuerto.add(pepe);
        huertoComunitario.setStaff(staffHuerto);
        eventoRepository.save(huertoComunitario);


        // Evento 2: "Cine en la Plaza"
        Evento cinePlaza = new Evento();
        cinePlaza.setTitulo("Cine en la Plaza");
        cinePlaza.setDescripcion("Proyección de una película familiar al aire libre.");
        cinePlaza.setUbicacion("Plaza Mayor");
        cinePlaza.setFecha(LocalDate.of(2025, 11, 5));
        cinePlaza.setHora(LocalTime.of(20, 30));
        cinePlaza.setRequiereInscripcion(false);
        cinePlaza.setOrganizador(antonioOrg);
        cinePlaza.setEstado(EstadoEvento.APROBADO);

        Set<Staff> staffCine = new HashSet<>();
        staffCine.add(maria);
        staffCine.add(paquitaAsStaff);
        cinePlaza.setStaff(staffCine);
        eventoRepository.save(cinePlaza);


        // Evento 3: "Cena Temática"
        Evento cenaTematica = new Evento();
        cenaTematica.setTitulo("Cena Temática");
        cenaTematica.setDescripcion("Noche de gastronomía y disfraces. Tema: Los años 80.");
        cenaTematica.setUbicacion("Centro Cívico");
        cenaTematica.setFecha(LocalDate.of(2025, 12, 1));
        cenaTematica.setHora(LocalTime.of(21, 0));
        cenaTematica.setRequiereInscripcion(true);
        cenaTematica.setOrganizador(paquitaOrg);
        cenaTematica.setEstado(EstadoEvento.PENDIENTE);

        Set<Staff> staffCena = new HashSet<>();
        staffCena.add(pepe);
        cenaTematica.setStaff(staffCena);

        Set<Participante> participantesCena = new HashSet<>();
        participantesCena.add(paco);
        participantesCena.add(antonioAsParticipante);
        cenaTematica.setParticipantes(participantesCena);
        eventoRepository.save(cenaTematica);

        System.out.println("************************Carga de datos de prueba completada.");
    }
}