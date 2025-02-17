import React, { useEffect, useState } from "react";
import { Form, FormGroup, FormLabel, FormControl, Button, Modal } from "react-bootstrap";
import apiService from "../services/apiService";

// Converte data do formato dd/mm/yyyy (do back) para yyyy-mm-dd (para o input date)
const formatarDataParaFrontEnd = (data) => {
  if (!data) return "";
  const [dia, mes, ano] = data.split("/");
  return `${ano}-${mes}-${dia}`;
};

// Converte data do formato yyyy-mm-dd (input date) para dd/mm/yyyy (para o backend)
const formatarDataParaBackend = (data) => {
  if (!data) return "";
  const [ano, mes, dia] = data.split("-");
  return `${dia}/${mes}/${ano}`;
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
      // Se as datas já vierem no formato dd/mm/yyyy, converte para yyyy-mm-dd para o input date
      setDataInicio(projetoData.dataInicio ? formatarDataParaFrontEnd(projetoData.dataInicio) : "");
      setDataFim(projetoData.dataFim ? formatarDataParaFrontEnd(projetoData.dataFim) : "");
      setStatusId(projetoData.statusId || "");
      setEquipeId(projetoData.equipeId || "");
    } else {
      // Limpa os campos se não houver projeto a editar
      setNome("");
      setDescricao("");
      setDataInicio("");
      setDataFim("");
      setStatusId("");
      setEquipeId("");
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
      // Converte as datas para o formato esperado pelo backend (dd/mm/yyyy)
      dataInicio: formatarDataParaBackend(dataInicio),
      dataFim: formatarDataParaBackend(dataFim),
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
