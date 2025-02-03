const BASE_URL = "http://localhost:8080";

const apiService = {
  buscarTarefas: async () => {
    const response = await fetch(`${BASE_URL}/tarefas`);
    if (!response.ok) throw new Error("Erro ao buscar tarefas");
    return response.json();
  },

  buscarProjetos: async () => {
    const response = await fetch(`${BASE_URL}/projeto/buscarProjetos`);
    if (!response.ok) throw new Error("Erro ao buscar projetos");
    return response.json();
  },

  buscarResponsaveis: async () => {
    const response = await fetch(`${BASE_URL}/responsaveis`);
    if (!response.ok) throw new Error("Erro ao buscar responsáveis");
    return response.json();
  },

  buscarStatuses: async () => {
    const response = await fetch(`${BASE_URL}/status`);
    if (!response.ok) throw new Error("Erro ao buscar statuses");
    return response.json();
  },

  buscarEquipes: async () => {
    const response = await fetch(`${BASE_URL}/equipes`);
    if (!response.ok) throw new Error("Erro ao buscar equipes");
    return response.json();
  },

  cadastrarTarefa: async (tarefa) => {
    const response = await fetch(`${BASE_URL}/tarefas`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(tarefa),
    });
  
    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || "Erro ao cadastrar tarefa");
    }
    return response.json();
  },
  

  atualizarTarefa: async (id, tarefa) => {
    const response = await fetch(`${BASE_URL}/tarefas/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(tarefa),
    });
    if (!response.ok) throw new Error("Erro ao atualizar tarefa");
    return response.json();
  },

  excluirTarefa: async (id) => {
    const response = await fetch(`${BASE_URL}/tarefas/${id}`, { method: "DELETE" });
    if (!response.ok) throw new Error("Erro ao excluir tarefa");
  },

  cadastrarProjeto: async (projeto) => {
    const response = await fetch(`${BASE_URL}/projeto`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(projeto),
    });
  
    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || "Erro ao cadastrar projeto");
    }
    return response.json();
  },
  

  atualizarProjeto: async (id, projeto) => {
    const response = await fetch(`${BASE_URL}/projeto/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(projeto),
    });
    if (!response.ok) throw new Error("Erro ao atualizar projeto");
    return response.json();
  },

  excluirProjeto: async (id) => {
    const response = await fetch(`${BASE_URL}/projeto/${id}`, { method: "DELETE" });
    if (!response.ok) throw new Error("Erro ao excluir projeto");
  },
};

export default apiService;
