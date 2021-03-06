package testes;

import application.AllocationSchedule;
import controller.Resolvedor;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import domain.Allocation;
import domain.Schedule;
import domain.Subject;
import domain.Teacher;
import model.dominio.Horario;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Danilo de Oliveira on 15/11/16.
 */
public class HorariosProfessorConflitantes {

    private Allocation allocationUm;
    private Allocation allocationDois;
    private Allocation allocationTres;

    private Teacher teacherUm = new Teacher("Foo Bar", "99999988888");
    private Teacher teacherDois = new Teacher("Teacher Snape", "11122233344");

    private Subject subjectUm = new Subject("Calculo 1", 1, 60);
    private Subject subjectDois = new Subject("Logica", 1, 60);
    private Subject subjectTres = new Subject("Calculo 2", 2, 90);

    private Schedule scheduleUm = new Schedule(1);
    private Schedule scheduleDois = new Schedule(2);
    private Schedule scheduleTres = new Schedule(3);

    private List<Allocation> alocacoes = new ArrayList<>();
    private List<Schedule> schedules;

    @Dado("^Existe um conjunto de professores alocados a um conjunto de disciplinas$")
    public void existe_um_conjunto_de_professores_alocados_a_um_conjunto_de_disciplinas() throws Throwable {
        allocationUm = new Allocation(subjectUm, teacherUm);
        allocationDois = new Allocation(subjectDois, teacherDois);
        allocationTres = new Allocation(subjectTres, teacherDois);

        alocacoes.add(allocationUm);
        alocacoes.add(allocationDois);
        alocacoes.add(allocationTres);
    }

    @Quando("^Eu alocar os horarios que geram conflito de horarios dos professores$")
    public void eu_alocar_os_horarios_que_geram_conflito_de_horarios_dos_professores() throws Throwable {
        scheduleUm.setDiaSemana(Schedule.DiaSemana.TERCA);
        scheduleUm.setHorarioInicio(7, 30);
        scheduleUm.setHorarioFim(9, 20);

        scheduleDois.setDiaSemana(Schedule.DiaSemana.TERCA);
        scheduleDois.setHorarioInicio(7, 30);
        scheduleDois.setHorarioFim(9, 20);

        scheduleTres.setDiaSemana(Schedule.DiaSemana.TERCA);
        scheduleTres.setHorarioInicio(7, 30);
        scheduleTres.setHorarioFim(9, 20);

        schedules = new ArrayList<>();

        schedules.add(scheduleUm);
        schedules.add(scheduleDois);
        schedules.add(scheduleTres);

        AllocationSchedule problema = new AllocationSchedule(alocacoes, schedules);

        Resolvedor.setAllocationSchedule(problema);

        AllocationSchedule solucao = Resolvedor.resolver("solver/bruteForce_solverConfig.xml");

        assertEquals(solucao.getScore().isFeasible(), false);
    }

    @Entao("^Enviar mensagem de conflito de horarios de professor$")
    public void enviar_mensagem_de_conflito_de_horarios_de_professor() throws Throwable {
        System.out.println("### Deu conflito! ###");
    }

    @Quando("^Alocar os horarios que nao geram conflito de horarios dos professores$")
    public void alocar_os_horarios_que_nao_geram_conflito_de_horarios_dos_professores() throws Throwable {
        scheduleUm.setDiaSemana(Schedule.DiaSemana.SEGUNDA);
        scheduleUm.setHorarioInicio(7, 30);
        scheduleUm.setHorarioFim(9, 20);

        scheduleDois.setDiaSemana(Schedule.DiaSemana.TERCA);
        scheduleDois.setHorarioInicio(7, 30);
        scheduleDois.setHorarioFim(9, 20);

        scheduleTres.setDiaSemana(Schedule.DiaSemana.TERCA);
        scheduleTres.setHorarioInicio(7, 30);
        scheduleTres.setHorarioFim(9, 20);

        schedules = new ArrayList<>();

        schedules.add(scheduleUm);
        schedules.add(scheduleDois);
        schedules.add(scheduleTres);

        AllocationSchedule problema = new AllocationSchedule(alocacoes, schedules);

        Resolvedor.setAllocationSchedule(problema);

        AllocationSchedule solucao = Resolvedor.resolver("solver/bruteForce_solverConfig.xml");

        assertEquals(solucao.getScore().isFeasible(), true);
    }
}
