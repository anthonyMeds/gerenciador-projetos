import React from "react";
import { Table, Button } from "react-bootstrap";

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
  }

  render() {
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
              <td> Atualizar 
                <Button variant="danger" onClick={() => this.deletarTarefas(tarefas.id)}>Excluir</Button> </td>
            </tr>
          ))}
        </tbody>
      </Table>
    );
  }
}

export default Tarefas;
