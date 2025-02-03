import "bootstrap/dist/css/bootstrap.min.css";
import { Nav } from "react-bootstrap";
import { BrowserRouter, Link, Route, Routes } from "react-router-dom";
import "./App.css";
import Projetos from "./components/Projetos";
import Tarefas from "./components/Tarefas";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Nav variant="tabs">
          <Nav.Link as={Link} to="/frontend/src/components/Tarefas">
            Gerenciamento de Tarefas
          </Nav.Link>
          <Nav.Link as={Link} to="/frontend/src/components/Projetos">
            Gerenciamento de Projetos
          </Nav.Link>
        </Nav>

        <Routes>
        <Route
            path="/"
            element={<Tarefas />}
          ></Route>
          <Route
            path="/frontend/src/components/Tarefas"
            element={<Tarefas />}
          ></Route>
          <Route
            path="/frontend/src/components/Projetos"
            element={<Projetos />}
          ></Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
