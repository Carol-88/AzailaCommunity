package azaila.community.config;

import azaila.community.enums.EstadoEvento;
import azaila.community.enums.Dieta;
import azaila.community.enums.PrioridadDiscapacidad;
import azaila.community.model.*;
import azaila.community.repository.*;
import jakarta.transaction.Transactional;
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
    private final PersonaRepository personaRepository;

    public Data(OrganizadorRepository organizadorRepository, ParticipanteRepository participanteRepository,
                StaffRepository staffRepository, EventoRepository eventoRepository, PersonaRepository personaRepository) {
        this.organizadorRepository = organizadorRepository;
        this.participanteRepository = participanteRepository;
        this.staffRepository = staffRepository;
        this.eventoRepository = eventoRepository;
        this.personaRepository = personaRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // 1. Crear entidades de personas con sus roles iniciales
        Organizador paquitaOrg = new Organizador();
        paquitaOrg.setNombreCompleto("Paquita La del Barrio");
        paquitaOrg.setEmail("paquita@azaila.com");
        paquitaOrg.setPassword("pass123");
        paquitaOrg.setTelefono("600111222");
        paquitaOrg.setRanking(10);
        paquitaOrg = organizadorRepository.save(paquitaOrg);

        Organizador antonioOrg = new Organizador();
        antonioOrg.setNombreCompleto("Antonio Banderas");
        antonioOrg.setEmail("antonio@azaila.com");
        antonioOrg.setPassword("pass456");
        antonioOrg.setTelefono("600333444");
        antonioOrg.setRanking(8);
        antonioOrg = organizadorRepository.save(antonioOrg);

        Staff pepe = new Staff();
        pepe.setNombreCompleto("Pepe Rodriguez");
        pepe.setEmail("pepe@azaila.com");
        pepe.setPassword("pass789");
        pepe.setNumeroStaff(1);
        pepe = staffRepository.save(pepe);

        Staff maria = new Staff();
        maria.setNombreCompleto("Maria Lopez");
        maria.setTelefono("600555666");
        maria.setEmail("maria@azaila.com");
        maria.setNumeroStaff(2);
        maria = staffRepository.save(maria);

        Participante paco = new Participante();
        paco.setNombreCompleto("Paco Fernandez");
        paco.setEmail("paco@azaila.com");
        paco.setPassword("passabc");
        paco.setDieta(Dieta.VEGETARIANA);
        paco = participanteRepository.save(paco);

        Participante ana = new Participante();
        ana.setNombreCompleto("Ana Garcia");
        ana.setEmail("ana@azaila.com");
        ana.setPassword("passdef");
        ana.setPrioridadDiscapacidad(PrioridadDiscapacidad.VISUAL);
        ana = participanteRepository.save(ana);

        // 2. Si quieres que Paquita y Antonio tengan roles adicionales, crea nuevas entidades independientes
        Staff paquitaAsStaff = new Staff();
        paquitaAsStaff.setNombreCompleto("Paquita La del Barrio");
        paquitaAsStaff.setEmail("paquita@azaila.com");
        paquitaAsStaff.setPassword("pass123");
        paquitaAsStaff.setTelefono("600111222");
        paquitaAsStaff.setNumeroStaff(3);
        paquitaAsStaff = staffRepository.save(paquitaAsStaff);

        Participante antonioAsParticipante = new Participante();
        antonioAsParticipante.setDieta(Dieta.VEGANA);
        antonioAsParticipante.setNombreCompleto("Antonio Banderas");
        antonioAsParticipante.setEmail("antonio@azaila.com");
        antonioAsParticipante.setPassword("pass456");
        antonioAsParticipante.setTelefono("600333444");
        antonioAsParticipante = participanteRepository.save(antonioAsParticipante);

        // 3. Crear Eventos y asignarles los roles
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