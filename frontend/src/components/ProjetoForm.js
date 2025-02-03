import React, { useEffect, useState } from "react";
import { Form, FormGroup, FormLabel, FormControl, Button, Modal } from "react-bootstrap";
import apiService from "../services/apiService";

const ProjetoForm = ({ show, onClose, onSubmit, projetoData }) => {
  const [nome, setNome] = useState("");
  const [descricao, setDescricao] = useState("");
  const [dataInicio, setDataInicio] = useState("");
  const [dataFim, setdataFim] = useState("");
  const [status, setStatus] = useState("");
  const [equipe, setEquipe] = useState("");
  const [statusList, setStatusList] = useState([]);
  const [equipeList, setEquipeList] = useState([]);

  useEffect(() => {
    carregarDadosProjeto();
  }, [projetoData]);

  useEffect(() => {
    if (show) {
      carregarCombos();
    }
  }, [show]);

  const carregarDadosProjeto = () => {
    if (projetoData) {
      setNome(projetoData.nome || "");
      setDescricao(projetoData.descricao || "");
      setDataInicio(projetoData.dataInicio || "");
      setdataFim(projetoData.dataFim || "");
      setStatus(projetoData.nomeStatus || "");
      setEquipe(projetoData.nomeEquipe || "");
    }
  };

  const carregarCombos = async () => {
    try {
      const [statuses, equipes] = await Promise.all([
        apiService.buscarStatuses(),
        apiService.buscarEquipes(),
      ]);
      setStatusList(statuses);
      setEquipeList(equipes);
    } catch (error) {
      console.error("Erro ao carregar dados:", error.message);
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    onSubmit({
      id: projetoData?.id,
      nome,
      descricao,
      dataInicio,
      dataFim,
      status,
      equipe,
    });
  };

  return (
    <Modal show={show} onHide={onClose}>
      <Modal.Header closeButton>
        <Modal.Title>{projetoData ? "Atualizar Projeto" : "Cadastrar Novo Projeto"}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleSubmit}>
          <FormGroup className="mb-3">
            <FormLabel>Nome</FormLabel>
            <FormControl value={nome} onChange={(e) => setNome(e.target.value)} placeholder="Nome do Projeto" />
          </FormGroup>

          <FormGroup className="mb-3">
            <FormLabel>Descrição</FormLabel>
            <FormControl value={descricao} onChange={(e) => setDescricao(e.target.value)} placeholder="Descrição" />
          </FormGroup>

          <FormGroup className="mb-3">
            <FormLabel>Data de Início</FormLabel>
            <FormControl type="date" value={dataInicio} onChange={(e) => setDataInicio(e.target.value)} />
          </FormGroup>

          <FormGroup className="mb-3">
            <FormLabel>Data de Conclusão</FormLabel>
            <FormControl type="date" value={dataFim} onChange={(e) => setdataFim(e.target.value)} />
          </FormGroup>

          <FormGroup className="mb-3">
            <FormLabel>Status</FormLabel>
            <FormControl as="select" value={status} onChange={(e) => setStatus(e.target.value)}>
              <option value="">Selecione um Status</option>
              {statusList.map((status) => (
                <option key={status.id} value={status.nome}>
                  {status.nome}
                </option>
              ))}
            </FormControl>
          </FormGroup>

          <FormGroup className="mb-3">
            <FormLabel>Equipe</FormLabel>
            <FormControl as="select" value={equipe} onChange={(e) => setEquipe(e.target.value)}>
              <option value="">Selecione uma Equipe</option>
              {equipeList.map((equipe) => (
                <option key={equipe.id} value={equipe.nome}>
                  {equipe.nome}
                </option>
              ))}
            </FormControl>
          </FormGroup>

          <Button type="submit">Salvar</Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default ProjetoForm;
