import React, { useEffect, useState } from "react";
import { Form, FormGroup, FormLabel, FormControl, FormSelect, Button, Modal } from "react-bootstrap";
import apiService from "../services/apiService";

const TarefaForm = ({ show, onClose, onSubmit, tarefaData }) => {
  const [titulo, setTitulo] = useState(tarefaData?.titulo || "");
  const [descricao, setDescricao] = useState(tarefaData?.descricao || "");
  const [projetoId, setProjetoId] = useState(tarefaData?.projetoId || "");
  const [responsavelId, setResponsavelId] = useState(tarefaData?.responsavelId || "");
  const [prazoDias, setPrazoDias] = useState(tarefaData?.prazoDias || "");
  const [statusId, setStatusId] = useState(tarefaData?.statusId || "");
  const [projetos, setProjetos] = useState([]);
  const [responsaveis, setResponsaveis] = useState([]);
  const [statuses, setStatuses] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [projetosData, responsaveisData, statusesData] = await Promise.all([
          apiService.buscarProjetos(),
          apiService.buscarResponsaveis(),
          apiService.buscarStatuses(),
        ]);

        setProjetos(projetosData);
        setResponsaveis(responsaveisData);
        setStatuses(statusesData);
      } catch (error) {
        console.error("Erro ao buscar dados:", error);
      }
    };
    fetchData();
  }, []);

  const handleSubmit = (event) => {
    event.preventDefault();
    onSubmit({
      titulo,
      descricao,
      projetoId: projetoId ? parseInt(projetoId) : null,
      responsavelId: responsavelId ? parseInt(responsavelId) : null,
      prazoDias: prazoDias ? parseInt(prazoDias) : null,
      statusId: statusId ? parseInt(statusId) : null,
    });
  };

  return (
    <Modal show={show} onHide={onClose}>
      <Modal.Header closeButton>
        <Modal.Title>{tarefaData ? "Atualizar Tarefa" : "Cadastrar Nova Tarefa"}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleSubmit}>
          <FormGroup className="mb-3">
            <FormLabel>Título</FormLabel>
            <FormControl value={titulo} onChange={(e) => setTitulo(e.target.value)} placeholder="Título" />
          </FormGroup>

          <FormGroup className="mb-3">
            <FormLabel>Descrição</FormLabel>
            <FormControl value={descricao} onChange={(e) => setDescricao(e.target.value)} placeholder="Descrição" />
          </FormGroup>

          <FormGroup className="mb-3">
            <FormLabel>Projeto</FormLabel>
            <FormSelect value={projetoId} onChange={(e) => setProjetoId(e.target.value)}>
              <option value="">Selecione um Projeto</option>
              {projetos.map((projeto) => (
                <option key={projeto.id} value={projeto.id}>
                  {projeto.nome}
                </option>
              ))}
            </FormSelect>
          </FormGroup>

          <FormGroup className="mb-3">
            <FormLabel>Responsável</FormLabel>
            <FormSelect value={responsavelId} onChange={(e) => setResponsavelId(e.target.value)}>
              <option value="">Selecione um Responsável</option>
              {responsaveis.map((responsavel) => (
                <option key={responsavel.id} value={responsavel.id}>
                  {responsavel.nome}
                </option>
              ))}
            </FormSelect>
          </FormGroup>

          <FormGroup className="mb-3">
            <FormLabel>Prazo em dias</FormLabel>
            <FormControl value={prazoDias} onChange={(e) => setPrazoDias(e.target.value)} />
          </FormGroup>

          <FormGroup className="mb-3">
            <FormLabel>Status</FormLabel>
            <FormSelect value={statusId} onChange={(e) => setStatusId(e.target.value)}>
              <option value="">Selecione um Status</option>
              {statuses.map((status) => (
                <option key={status.id} value={status.id}>
                  {status.nome}
                </option>
              ))}
            </FormSelect>
          </FormGroup>

          <Button type="submit">Salvar</Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default TarefaForm;
