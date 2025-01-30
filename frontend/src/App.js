import "./App.css";
import Home from "./components/Home";
import Tarefas from "./components/Tarefas";
import Projetos from "./components/Projetos";
import { BrowserRouter, Routes, Link, Route } from "react-router-dom";

function App() {
  return (
    <div className="App">
      <p>teste</p>

      <BrowserRouter>
        <ul>
          <li>
            <Link to="/"> Home </Link>
          </li>
          <li>
            <Link to="/frontend/src/components/Tarefas"> Tarefas </Link>
          </li>
          <li>
            <Link to="/frontend/src/components/Projetos"> Projetos </Link>
          </li>
        </ul>

        <Routes>
          <Route path="/" element={<Home/>}></Route>
          <Route path="/frontend/src/components/Tarefas" element={<Tarefas/>}></Route>
          <Route path="/frontend/src/components/Projetos" element={<Projetos/>}></Route>
        </Routes>

      </BrowserRouter>
    </div>
  );
}

export default App;
