import React, { useState, useEffect } from "react";
import TarefaForm from "./TarefaForm";
import ToastNotification from "./ToastNotification";
import apiService from "../services/apiService";
import { Button, Table } from "react-bootstrap";

const Tarefas = () => {
  const [tarefas, setTarefas] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [showToast, setShowToast] = useState(false);
  const [toastMessage, setToastMessage] = useState("");
  const [toastError, setToastError] = useState(false);

  useEffect(() => {
    buscarTarefas();
  }, []);

  const buscarTarefas = async () => {
    try {
      const data = await apiService.buscarTarefas();
      setTarefas(data);
    } catch (error) {
      exibirToast(error.message, true);
    }
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

      console.log("Payload enviado:", payload);

      await apiService.cadastrarTarefa(payload);
      exibirToast("Tarefa cadastrada com sucesso");
      buscarTarefas();
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

  return (
    <div>
      <Button variant="primary" onClick={() => setShowModal(true)}>
        Cadastrar Nova Tarefa
      </Button>

      <Table striped bordered hover>
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
          {tarefas.length > 0 ? (
            tarefas.map((tarefa) => (
              <tr key={tarefa.id}>
                <td>{tarefa.titulo}</td>
                <td>{tarefa.descricao}</td>
                <td>{tarefa.nomeProjeto}</td>
                <td>{tarefa.nomeResponsavel}</td>
                <td>{tarefa.statusId}</td>
                <td>
                  <Button variant="danger" onClick={() => handleExcluir(tarefa.id)}>
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

      <TarefaForm show={showModal} onClose={() => setShowModal(false)} onSubmit={handleCadastrar} />
      <ToastNotification show={showToast} message={toastMessage} isError={toastError} onClose={() => setShowToast(false)} />
    </div>
  );
};

export default Tarefas;
