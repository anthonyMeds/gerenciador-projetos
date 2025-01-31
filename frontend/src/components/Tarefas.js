import React from "react";
import { Table, Button, Form, FormGroup, FormLabel, FormControl } from "react-bootstrap";

class Tarefas extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      tarefas: [],
    };
  }

  componentDidMount() {
    this.buscarTarefas();
  }

  buscarTarefas() {
    fetch("http://localhost:8080/tarefas")
      .then((resposta) => resposta.json())
      .then((dados) => {
        this.setState({ tarefas: dados });
      });
  }

  deletarTarefas = (id) => {
    fetch("http://localhost:8080/tarefas/" + id, { method: "DELETE" }).then(
      (resposta) => {
        if (resposta.ok) {
          this.buscarTarefas();
        }
      }
    );
  };

  renderTabela() {
    return (
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Título</th>
            <th>Descrição</th>
            <th>Nome do Projeto</th>
            <th>Responsável da Tarefa</th>
            <th>Prazo</th>
            <th>Status</th>
            <th>Opções</th>
          </tr>
        </thead>
        <tbody>
          {this.state.tarefas.map((tarefas) => (
            <tr>
              <td> {tarefas.titulo} </td>
              <td> {tarefas.descricao} </td>
              <td> {tarefas.nomeProjeto} </td>
              <td> {tarefas.nomeResponsavel} </td>
              <td> {tarefas.prazoDias} </td>
              <td> {tarefas.statusNome} </td>
              <td>
                Atualizar
                <Button
                  variant="danger"
                  onClick={() => this.deletarTarefas(tarefas.id)}
                >
                  Excluir
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    );
  }

  render() {
    return (
      <div>
        <Form>
          <FormGroup className="mb-3">
            <FormLabel>Título</FormLabel>
            <FormControl type="text" placeholder="Insira o título da tarefa" />
          </FormGroup>
          <FormGroup className="mb-3">
            <FormLabel>Descrição</FormLabel>
            <FormControl
              as="textarea"
              rows={3}
              placeholder="Descreva a tarefa"
            />
          </FormGroup>
          <FormGroup className="mb-3">
            <FormLabel>Nome do Projeto</FormLabel>
            <FormControl type="text" placeholder="Informe o nome do projeto" />
          </FormGroup>
          <FormGroup className="mb-3">
            <FormLabel>Responsável</FormLabel>
            <FormControl type="text" placeholder="Quem será responsável?" />
          </FormGroup>
          <FormGroup className="mb-3">
            <FormLabel>Prazo em dias</FormLabel>
            <FormControl type="number" />
          </FormGroup>
          <FormGroup className="mb-3">
            <FormLabel>Status</FormLabel>
            <FormControl as="select">
              <option>Pendente</option>
              <option>Em Andamento</option>
              <option>Concluída</option>
            </FormControl>
          </FormGroup>
          <Button variant="primary" type="submit">
            Salvar Tarefa
          </Button>
        </Form>

        {this.renderTabela()}
      </div>
    );
  }
}

export default Tarefas;
