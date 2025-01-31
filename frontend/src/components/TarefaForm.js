import React, { useEffect, useState } from "react";
import { Form, FormGroup, FormLabel, FormControl, FormSelect, Button, Modal } from "react-bootstrap";
import apiService from "../services/apiService";

const TarefaForm = ({ show, onClose, onSubmit, tarefaData }) => {
  const [titulo, setTitulo] = useState("");
  const [descricao, setDescricao] = useState("");
  const [projetoId, setProjetoId] = useState("");
  const [responsavelId, setResponsavelId] = useState("");
  const [prazoDias, setPrazoDias] = useState("");
  const [statusId, setStatusId] = useState("");
  const [projetos, setProjetos] = useState([]);
  const [responsaveis, setResponsaveis] = useState([]);
  const [statuses, setStatuses] = useState([]);

  // Atualizar campos quando tarefaData mudar
  useEffect(() => {
    if (tarefaData) {
      setTitulo(tarefaData.titulo || "");
      setDescricao(tarefaData.descricao || "");
      setProjetoId(tarefaData.projetoId || "");
      setResponsavelId(tarefaData.responsavelId || "");
      setPrazoDias(tarefaData.prazoDias || "");
      setStatusId(tarefaData.statusId || "");
    }
  }, [tarefaData]);

  useEffect(() => {
    if (!show) {
      // Limpa os campos ao fechar o modal
      setTitulo("");
      setDescricao("");
      setProjetoId("");
      setResponsavelId("");
      setPrazoDias("");
      setStatusId("");
    }
  }, [show]);

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
      id: tarefaData?.id,
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
