import "./App.css";
import Home from "./components/Home";
import Tarefas from "./components/Tarefas";
import Projetos from "./components/Projetos";
import { BrowserRouter, Routes, Link, Route } from "react-router-dom";
import { Nav } from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
    <div className="App">
      <p>teste</p>

      <BrowserRouter>
        <Nav variant="tabs">
          <Nav.Link as={Link} to="/">
            Pagina Inicial
          </Nav.Link>
          <Nav.Link as={Link} to="/frontend/src/components/Tarefas">
            Gerenciamento de Tarefas
          </Nav.Link>
          <Nav.Link as={Link} to="/frontend/src/components/Projetos">
            Gerenciamento de Projetos
          </Nav.Link>
        </Nav>

        <Routes>
          <Route path="/" element={<Home />}></Route>
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
