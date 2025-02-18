import React, { useState, useEffect } from "react";
import TarefaForm from "./TarefaForm";
import ToastNotification from "./ToastNotification";
import apiService from "../services/apiService";
import { Button, Table, Container, Form, FormControl, FormSelect } from "react-bootstrap";
import "./css/Tarefas.css";

const Tarefas = () => {
  const [tarefas, setTarefas] = useState([]);
  const [tarefasFiltradas, setTarefasFiltradas] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [showToast, setShowToast] = useState(false);
  const [toastMessage, setToastMessage] = useState("");
  const [toastError, setToastError] = useState(false);
  const [tarefaAtual, setTarefaAtual] = useState(null);
  const [termoBusca, setTermoBusca] = useState("");
  const [criterioBusca, setCriterioBusca] = useState("titulo");

  useEffect(() => {
    buscarTarefas();
  }, []);

  useEffect(() => {
    filtrarTarefas();
  }, [termoBusca, criterioBusca, tarefas]);

  const buscarTarefas = async () => {
    try {
      const data = await apiService.buscarTarefas();
      setTarefas(data);
      setTarefasFiltradas(data);
    } catch (error) {
      exibirToast(error.message, true);
    }
  };

  const filtrarTarefas = () => {
    const filtro = tarefas.filter((tarefa) => {
      const valorComparacao = String(tarefa[criterioBusca]?.toLowerCase() || "");
      return valorComparacao.includes(termoBusca.toLowerCase());
    });
    setTarefasFiltradas(filtro);
  };

  const exibirToast = (message, isError) => {
    setToastMessage(message);
    setToastError(isError);
    setShowToast(true);
  };

  const handleCadastrar = async (tarefa) => {
    try {
      const payload = {
        titulo: tarefa.titulo,
        descricao: tarefa.descricao,
        projetoId: parseInt(tarefa.projetoId) || null,
        responsavelId: parseInt(tarefa.responsavelId) || null,
        prazoDias: parseInt(tarefa.prazoDias) || null,
        statusId: parseInt(tarefa.statusId) || null,
      };

      if (!payload.titulo || !payload.descricao || !payload.projetoId || !payload.responsavelId || !payload.statusId) {
        exibirToast("Preencha todos os campos.", true);
        return;
      }

      if (tarefa.id) {
        await apiService.atualizarTarefa(tarefa.id, payload);
        exibirToast("Tarefa atualizada com sucesso");
      } else {
        await apiService.cadastrarTarefa(payload);
        exibirToast("Tarefa cadastrada com sucesso");
      }
      buscarTarefas();
      setShowModal(false);
    } catch (error) {
      exibirToast(error.message, true);
    }
  };

  const handleExcluir = async (id) => {
    try {
      await apiService.excluirTarefa(id);
      exibirToast("Tarefa excluída com sucesso");
      buscarTarefas();
    } catch (error) {
      exibirToast(error.message, true);
    }
  };

  const handleEditar = (tarefa) => {
    setTarefaAtual(tarefa);
    setShowModal(true);
  };

  return (
    <Container>
      <Button
        variant="primary"
        className="my-3"
        onClick={() => {
          setTarefaAtual(null);
          setShowModal(true);
        }}
      >
        Cadastrar Nova Tarefa
      </Button>

      <Form className="mb-3">
        <FormSelect
          className="mb-2"
          value={criterioBusca}
          onChange={(e) => setCriterioBusca(e.target.value)}
        >
          <option value="titulo">Título</option>
          <option value="statusNome">Status</option>
          <option value="nomeResponsavel">Responsável</option>
          <option value="nomeProjeto">Projeto</option>
        </FormSelect>

        <FormControl
          type="text"
          placeholder={`Pesquisar por ${criterioBusca}`}
          value={termoBusca}
          onChange={(e) => setTermoBusca(e.target.value)}
        />
      </Form>

      <div className="table-container">
        <Table striped bordered hover responsive>
          <thead>
            <tr>
              <th>Título</th>
              <th>Descrição</th>
              <th>Projeto</th>
              <th>Responsável</th>
              <th>Status</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            {tarefasFiltradas.length > 0 ? (
              tarefasFiltradas.map((tarefa) => (
                <tr key={tarefa.id}>
                  <td>{tarefa.titulo}</td>
                  <td>{tarefa.descricao}</td>
                  <td>{tarefa.nomeProjeto}</td>
                  <td>{tarefa.nomeResponsavel}</td>
                  <td>{tarefa.statusNome}</td>
                  <td>
                    <Button
                      variant="warning"
                      className="me-2"
                      onClick={() => handleEditar(tarefa)}
                    >
                      Editar
                    </Button>
                    <Button
                      variant="danger"
                      onClick={() => handleExcluir(tarefa.id)}
                    >
                      Excluir
                    </Button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="6">Não existem tarefas cadastradas</td>
              </tr>
            )}
          </tbody>
        </Table>
      </div>

      <TarefaForm
        show={showModal}
        onClose={() => setShowModal(false)}
        onSubmit={handleCadastrar}
        tarefaData={tarefaAtual}
      />
      <ToastNotification
        show={showToast}
        message={toastMessage}
        isError={toastError}
        onClose={() => setShowToast(false)}
      />
    </Container>
  );
};

export default Tarefas;
