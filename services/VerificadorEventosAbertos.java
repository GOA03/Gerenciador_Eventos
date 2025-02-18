package services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import dao.BancoDados;
import dao.EventoDAO;
import entities.Evento;
import userinterfaces.TelaGerenciamentoEventosParticipante;

public class VerificadorEventosAbertos extends Thread {
    private boolean running = true;
    private long intervalo = 2000; // Intervalo de 2 segundos
    private TelaGerenciamentoEventosParticipante tela;
    private Set<Integer> idsEventosAbertos; // Armazena apenas os IDs dos eventos abertos

    public VerificadorEventosAbertos(TelaGerenciamentoEventosParticipante tela) {
        this.tela = tela;
        this.idsEventosAbertos = new HashSet<>();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(intervalo);
                verificarNovosEventos();
            } catch (InterruptedException e) {
                running = false; // Encerra a thread se ocorrer uma interrupção
            }
        }
    }

    private void verificarNovosEventos() {
        try (Connection conn = BancoDados.conectar()) {
            EventoDAO eventoDAO = new EventoDAO(conn);
            List<Evento> eventosAbertosNovo = eventoDAO.listarEventos("ABERTO"); // Retorna eventos com status "ABERTO"

            // Converte a lista de eventos em um conjunto de IDs
            Set<Integer> novosIdsEventosAbertos = new HashSet<>();
            for (Evento evento : eventosAbertosNovo) {
                novosIdsEventosAbertos.add(evento.getId());
            }

            // Exibe aviso SOMENTE se houver novos eventos adicionados
            if (!novosIdsEventosAbertos.isEmpty() && !idsEventosAbertos.containsAll(novosIdsEventosAbertos)) {
                idsEventosAbertos = novosIdsEventosAbertos; // Atualiza a lista de eventos abertos
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(
                        tela,
                        "Novos eventos abertos disponíveis!",
                        "Novo Evento",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                });
            } else {
                idsEventosAbertos = novosIdsEventosAbertos; // Apenas atualiza a lista sem exibir aviso
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                BancoDados.desconectar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void parar() {
        running = false; // Método para parar a thread
    }
}
