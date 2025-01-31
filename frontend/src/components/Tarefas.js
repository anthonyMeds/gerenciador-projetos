import React from "react";
import { Table } from 'react-bootstrap';

class Tarefas extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      tarefas: [],
    };
  }

  render() {
    return (
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Título</th>
            <th>Descrição</th>
            <th>Nome do Projeto</th>
            <th>Responsável</th>
            <th>Prazo</th>
            <th>Status</th>
            <th>Opções</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>1</td>
            <td>Mark</td>
            <td>Otto</td>
            <td>@mdo</td>
            <td>@mdo</td>
            <td>@mdo</td>
            <td>Atualizar Excluir</td>
          </tr>
        </tbody>
      </Table>
    );
  }
}

export default Tarefas;
