import React, { useEffect, useState } from "react";
import { Form, FormGroup, FormLabel, FormControl, Button, Modal } from "react-bootstrap";
import apiService from "../services/apiService";


  

const formatarDataParaFrontEnd = (data) => {
  if (!data) return '';
  const [dia, mes, ano] = data.split('/');
  return `${ano}-${mes}-${dia}`;  // formato yyyy-mm-dd
};

const ProjetoForm = ({ show, onClose, onSubmit, projetoData }) => {
  const [nome, setNome] = useState("");
  const [descricao, setDescricao] = useState("");
  const [dataInicio, setDataInicio] = useState("");
  const [dataFim, setDataFim] = useState("");
  const [statusId, setStatusId] = useState("");
  const [equipeId, setEquipeId] = useState("");
  const [statuses, setStatuses] = useState([]);
  const [equipes, setEquipes] = useState([]);

  useEffect(() => {
    if (projetoData) {
      setNome(projetoData.nome || "");
      setDescricao(projetoData.descricao || "");
      setDataInicio(formatarDataParaFrontEnd(projetoData.dataInicio) || "");
      setDataFim(formatarDataParaFrontEnd(projetoData.dataFim) || "");
      setStatusId(projetoData.statusId || "");
      setEquipeId(projetoData.equipeId || "");
    }
  }, [projetoData]);

  useEffect(() => {
    if (show) {
      buscarStatuses();
      buscarEquipes();
    }
  }, [show]);

  const buscarStatuses = async () => {
    try {
      const data = await apiService.buscarStatuses();
      setStatuses(data);
    } catch (error) {
      console.error("Erro ao carregar statuses:", error);
    }
  };

  const buscarEquipes = async () => {
    try {
      const data = await apiService.buscarEquipes();
      setEquipes(data);
    } catch (error) {
      console.error("Erro ao carregar equipes:", error);
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    onSubmit({
      id: projetoData?.id,
      nome,
      descricao,
      dataInicio: dataInicio,
      dataFim: dataFim,
      statusId,
      equipeId,
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
            <FormControl
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              placeholder="Nome do Projeto"
            />
          </FormGroup>

          <FormGroup className="mb-3">
            <FormLabel>Descrição</FormLabel>
            <FormControl
              value={descricao}
              onChange={(e) => setDescricao(e.target.value)}
              placeholder="Descrição do Projeto"
            />
          </FormGroup>

          <FormGroup className="mb-3">
            <FormLabel>Data de Início</FormLabel>
            <FormControl
              type="date"
              value={dataInicio}
              onChange={(e) => setDataInicio(e.target.value)}
            />
          </FormGroup>

          <FormGroup className="mb-3">
            <FormLabel>Data de Fim</FormLabel>
            <FormControl
              type="date"
              value={dataFim}
              onChange={(e) => setDataFim(e.target.value)}
            />
          </FormGroup>

          <FormGroup className="mb-3">
            <FormLabel>Status</FormLabel>
            <FormControl
              as="select"
              value={statusId}
              onChange={(e) => setStatusId(e.target.value)}
            >
              <option value="">Selecione um status</option>
              {statuses.map((status) => (
                <option key={status.id} value={status.id}>
                  {status.nome}
                </option>
              ))}
            </FormControl>
          </FormGroup>

          <FormGroup className="mb-3">
            <FormLabel>Equipe</FormLabel>
            <FormControl
              as="select"
              value={equipeId}
              onChange={(e) => setEquipeId(e.target.value)}
            >
              <option value="">Selecione uma equipe</option>
              {equipes.map((equipe) => (
                <option key={equipe.id} value={equipe.id}>
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
