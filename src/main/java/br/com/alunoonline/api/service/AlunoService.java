package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository alunoRepository;

    public void criarAluno(Aluno aluno) {
        alunoRepository.save(aluno);
    }

    public List<Aluno> buscarTodosAlunos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id);
    }

    public void deletarAlunoPorId(Long id) {
        alunoRepository.deleteById(id);
    }

    public void atualizarAlunoPorId(Long id, Aluno alunoAtualizado) {
        // PRIMEIRO PASSO: VER SE O ALUNO EXISTE NO BD
        Optional<Aluno> alunoDoBancoDeDados = buscarAlunoPorId(id);

        // E SE NÃO EXISTIR O ALUNO COM ESSE ID?
        if (alunoDoBancoDeDados.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Aluno não encontrado no banco de dados");
        }

        // SE CHEGOU AQUI, SIGNIFICA QUE EXISTE ALUNO COM ESSE ID!
        // VOU ARMAZENA-LO EM UMA VARIAVEL PARA DEPOIS EDITA-LO
        Aluno alunoParaEditar = alunoDoBancoDeDados.get();

        // COM ESSE ALUNO PARA SER EDITADO ACIMA, FAÇO
        // OS SETS NECESSÁRIOS PARA ATUALIZAR OS ATRIBUTOS DELE
        alunoParaEditar.setNomeCompleto(alunoAtualizado.getNomeCompleto());
        alunoParaEditar.setEmail(alunoAtualizado.getEmail());
        alunoParaEditar.setCpf(alunoAtualizado.getCpf());

        // AGORA, VOU LEVAR ESSE ALUNO PARA EDITAR PARA O BD
        alunoRepository.save(alunoParaEditar);
    }



}
