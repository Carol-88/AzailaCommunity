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
import java.util.Optional;
import java.util.Set;

@Component
public class Data implements CommandLineRunner {

    private final OrganizadorRepository organizadorRepository;
    private final ParticipanteRepository participanteRepository;
    private final StaffRepository staffRepository;
    private final PersonaIdentidadRepository identidadRepository;
    private final EventoRepository eventoRepository;

    public Data(OrganizadorRepository organizadorRepository, ParticipanteRepository participanteRepository, StaffRepository staffRepository, PersonaIdentidadRepository identidadRepository) {
        this.organizadorRepository = organizadorRepository;
        this.participanteRepository = participanteRepository;
        this.staffRepository = staffRepository;
        this.identidadRepository = identidadRepository;
        this.eventoRepository = eventoRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        PersonaIdentidad idPaquita = findOrCreateIdentidad("paquita@azaila.com", "561561651651");
        PersonaIdentidad idAntonio = findOrCreateIdentidad("antonio@azaila.com", null);
        PersonaIdentidad idPepe = findOrCreateIdentidad("pepe@azaila.com", null);
        PersonaIdentidad idJuana = findOrCreateIdentidad("juana@azaila.com", "123456789");
        PersonaIdentidad idPaco = findOrCreateIdentidad(null, "987654321");
        PersonaIdentidad idAna = findOrCreateIdentidad(null, "555666777");

        Organizador paquitaOrg = getOrCreateOrganizador(idPaquita,"Paquita");
        Staff paquitaStf = getOrCreateStaff(idPaquita,"Paquita");
        Participante paquitaPart = getOrCreateParticipante(idPaquita,"Paquita");

        Organizador antonioOrg = getOrCreateOrganizador(idAntonio,"Antonio");
        Staff antonioStf = getOrCreateStaff(idAntonio,"Antonio");
        Participante antonioPart = getOrCreateParticipante(idAntonio,"Antonio");

        Organizador pepeOrg = getOrCreateOrganizador(idPepe,"Pepe");
        Staff pepe = getOrCreateStaff(idPepe,"Pepe");
        Participante pepePart = getOrCreateParticipante(idPepe,"Pepe");

        Organizador juanaOrg = getOrCreateOrganizador(idJuana,"Juana");
        Staff juanaStf = getOrCreateStaff(idJuana,"Juana");
        Participante juanaPart = getOrCreateParticipante(idJuana,"Juana");

        Organizador pacoOrg = getOrCreateOrganizador(idPaco,"Paco");
        Staff pacoStf = getOrCreateStaff(idPaco,"Paco");
        Participante pacoPart = getOrCreateParticipante(idPaco,"Paco");

        Organizador anaOrg = getOrCreateOrganizador(idAna,"Ana");
        Staff anaStf = getOrCreateStaff(idAna,"Ana");
        Participante anaPart = getOrCreateParticipante(idAna,"Ana");


        // Crear Eventos y asignarles los roles

        //Evento huerto comunitario
        Evento huertoComunitario = new Evento();
        huertoComunitario.setTitulo("Huerto Comunitario");
        huertoComunitario.setDescripcion("Tarde de trabajo y compañerismo en el huerto municipal.");
        huertoComunitario.setUbicacion("Huerto Municipal");
        huertoComunitario.setFecha(LocalDate.of(2025, 10, 20));
        huertoComunitario.setHora(LocalTime.of(17, 0));
        huertoComunitario.setRequiereInscripcion(true);
        huertoComunitario.setOrganizador(paquitaOrg);
        huertoComunitario.setEstado(EstadoEvento.APROBADO);

        Set<Staff> staffHuerto = new HashSet<>();
        staffHuerto.add(pepe);

        huertoComunitario.setStaff(staffHuerto);

        Set<Participante> participantesHuerto = new HashSet<>();
        participantesHuerto.add(pacoPart);
        participantesHuerto.add(anaPart);

        huertoComunitario.setParticipantes(participantesHuerto);

        eventoRepository.save(huertoComunitario);

        //Evento cine
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
        staffCine.add(anaStf);

        cinePlaza.setStaff(staffCine);

        Set<Participante> partCine = new HashSet<>();
        partCine.add(juanaPart);
        partCine.add(pepePart);
        partCine.add(paquitaPart);
        partCine.add(anaPart);

        cinePlaza.setParticipantes(partCine);

        eventoRepository.save(cinePlaza);

        //Evento cena tematica
        Evento cenaTematica = new Evento();
        cenaTematica.setTitulo("Cena Temática");
        cenaTematica.setDescripcion("Noche de gastronomía y disfraces. Tema: Los años 80.");
        cenaTematica.setUbicacion("Centro Cívico");
        cenaTematica.setFecha(LocalDate.of(2025, 12, 1));
        cenaTematica.setHora(LocalTime.of(21, 0));
        cenaTematica.setRequiereInscripcion(true);
        cenaTematica.setOrganizador(anaOrg);
        cenaTematica.setEstado(EstadoEvento.PENDIENTE);

        Set<Staff> staffCena = new HashSet<>();
        staffCena.add(pepe);

        cenaTematica.setStaff(staffCena);

        Set<Participante> participantesCena = new HashSet<>();
        participantesCena.add(pacoPart);
        participantesCena.add(antonioPart);
        participantesCena.add(juanaPart);
        participantesCena.add(paquitaPart);

        cenaTematica.setParticipantes(participantesCena);

        eventoRepository.save(cenaTematica);


        System.out.println("************************Carga de datos de prueba completada.");

    }

    private PersonaIdentidad findOrCreateIdentidad(String email, String telefono) {
        if (email != null) {
            Optional<PersonaIdentidad> byEmail = identidadRepository.findByEmail(email);
            if (byEmail.isPresent()) {
                return byEmail.get();
            }
        }
        if (telefono != null) {
            Optional<PersonaIdentidad> byTelefono = identidadRepository.findByTelefono(telefono);
            if (byTelefono.isPresent()) {
                return byTelefono.get();
            }
        }
        PersonaIdentidad nuevaIdentidad = new PersonaIdentidad();
        nuevaIdentidad.setEmail(email);
        nuevaIdentidad.setTelefono(telefono);
        return identidadRepository.save(nuevaIdentidad);
    }

        private Organizador getOrCreateOrganizador(PersonaIdentidad identidad, String nombreCompleto) {
            Organizador org = organizadorRepository.findById(identidad.getId()).orElseGet(()->{
                Organizador o = new Organizador();
                o.setIdentidad(identidad);
                return o;
            });
            org.setNombreCompleto(nombreCompleto);
            return organizadorRepository.save(org);
        }

        private Staff getOrCreateStaff(PersonaIdentidad identidad, String nombreCompleto){
            Staff stf = staffRepository.findById(identidad.getId()).orElseGet(() -> {
                Staff s = new Staff();
                s.setIdentidad(identidad);
                return s;
            });
            stf.setNombreCompleto(nombreCompleto);
            return staffRepository.save(stf);
        }

        private Participante getOrCreateParticipante(PersonaIdentidad identidad, String nombreCompleto){
            Participante part = participanteRepository.findById(identidad.getId()).orElseGet(() -> {
                Participante p = new Participante();
                p.setIdentidad(identidad);
                return p;
            });
            part.setNombreCompleto(nombreCompleto);
            return participanteRepository.save(part);
        }
}


