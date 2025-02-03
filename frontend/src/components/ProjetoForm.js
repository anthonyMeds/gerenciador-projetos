import React, { useEffect, useState } from "react";
import { Form, FormGroup, FormLabel, FormControl, Button, Modal } from "react-bootstrap";

const ProjetoForm = ({ show, onClose, onSubmit, projetoData }) => {
  const [nome, setNome] = useState("");
  const [descricao, setDescricao] = useState("");
  const [dataInicio, setDataInicio] = useState("");
  const [dataConclusao, setDataConclusao] = useState("");
  const [status, setStatus] = useState("");

  useEffect(() => {
    if (projetoData) {
      setNome(projetoData.nome || "");
      setDescricao(projetoData.descricao || "");
      setDataInicio(projetoData.dataInicio || "");
      setDataConclusao(projetoData.dataConclusao || "");
      setStatus(projetoData.status || "");
    }
  }, [projetoData]);

  useEffect(() => {
    if (!show) {
      setNome("");
      setDescricao("");
      setDataInicio("");
      setDataConclusao("");
      setStatus("");
    }
  }, [show]);

  const handleSubmit = (event) => {
    event.preventDefault();
    onSubmit({
      id: projetoData?.id,
      nome,
      descricao,
      dataInicio,
      dataConclusao,
      status,
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
            <FormControl type="date" value={dataConclusao} onChange={(e) => setDataConclusao(e.target.value)} />
          </FormGroup>

          <FormGroup className="mb-3">
            <FormLabel>Status</FormLabel>
            <FormControl value={status} onChange={(e) => setStatus(e.target.value)} placeholder="Status do Projeto" />
          </FormGroup>

          <Button type="submit">Salvar</Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default ProjetoForm;
