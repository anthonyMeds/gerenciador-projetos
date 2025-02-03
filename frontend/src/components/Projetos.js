import React, { useState, useEffect } from "react";
import ProjetoForm from "./ProjetoForm";
import ToastNotification from "./ToastNotification";
import apiService from "../services/apiService";
import { Button, Table, Container, Form, FormControl } from "react-bootstrap";
import "./css/Tarefas.css";

const Projetos = () => {
  const [projetos, setProjetos] = useState([]);
  const [projetosFiltrados, setProjetosFiltrados] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [showToast, setShowToast] = useState(false);
  const [toastMessage, setToastMessage] = useState("");
  const [toastError, setToastError] = useState(false);
  const [projetoAtual, setProjetoAtual] = useState(null);
  const [termoBusca, setTermoBusca] = useState("");

  useEffect(() => {
    buscarProjetos();
  }, []);

  useEffect(() => {
    filtrarProjetos();
  }, [termoBusca, projetos]);

  const buscarProjetos = async () => {
    try {
      const data = await apiService.buscarProjetos();
      setProjetos(data);
      setProjetosFiltrados(data);
    } catch (error) {
      exibirToast(error.message, true);
    }
  };

  const filtrarProjetos = () => {
    const filtro = projetos.filter((projeto) =>
      projeto.nome.toLowerCase().includes(termoBusca.toLowerCase())
    );
    setProjetosFiltrados(filtro);
  };

  const exibirToast = (message, isError) => {
    setToastMessage(message);
    setToastError(isError);
    setShowToast(true);
  };

  const handleCadastrar = async (projeto) => {
    try {
      if (!projeto.nome || !projeto.descricao) {
        exibirToast("Preencha todos os campos.", true);
        return;
      }

      if (projeto.id) {
        await apiService.atualizarProjeto(projeto.id, projeto);
        exibirToast("Projeto atualizado com sucesso");
      } else {
        await apiService.cadastrarProjeto(projeto);
        exibirToast("Projeto cadastrado com sucesso");
      }
      buscarProjetos();
      setShowModal(false);
    } catch (error) {
      exibirToast(error.message, true);
    }
  };

  const handleExcluir = async (id) => {
    try {
      await apiService.excluirProjeto(id);
      exibirToast("Projeto excluído com sucesso");
      buscarProjetos();
    } catch (error) {
      exibirToast(error.message, true);
    }
  };

  const handleEditar = (projeto) => {
    setProjetoAtual(projeto);
    setShowModal(true);
  };

  return (
    <Container>
      <Button
        variant="primary"
        className="my-3"
        onClick={() => {
          setProjetoAtual(null);
          setShowModal(true);
        }}
      >
        Cadastrar Novo Projeto
      </Button>

      <Form className="mb-3">
        <FormControl
          type="text"
          placeholder="Pesquisar por nome"
          value={termoBusca}
          onChange={(e) => setTermoBusca(e.target.value)}
        />
      </Form>

      <div className="table-container">
        <Table striped bordered hover responsive>
          <thead>
            <tr>
              <th>Nome</th>
              <th>Descrição</th>
              <th>Data de Início</th>
              <th>Data de Conclusão</th>
              <th>Status</th>
              <th>Equipe</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            {projetosFiltrados.length > 0 ? (
              projetosFiltrados.map((projeto) => (
                <tr key={projeto.id}>
                  <td>{projeto.nome}</td>
                  <td>{projeto.descricao}</td>
                  <td>{projeto.dataInicio}</td>
                  <td>{projeto.dataFim}</td>
                  <td>{projeto.nomeStatus}</td>
                  <td>{projeto.nomeEquipe}</td>
                  <td>
                    <Button
                      variant="warning"
                      className="me-2"
                      onClick={() => handleEditar(projeto)}
                    >
                      Editar
                    </Button>
                    <Button
                      variant="danger"
                      onClick={() => handleExcluir(projeto.id)}
                    >
                      Excluir
                    </Button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="7">Não existem projetos cadastrados</td>
              </tr>
            )}
          </tbody>
        </Table>
      </div>

      <ProjetoForm
        show={showModal}
        onClose={() => setShowModal(false)}
        onSubmit={handleCadastrar}
        projetoData={projetoAtual}
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

export default Projetos;
