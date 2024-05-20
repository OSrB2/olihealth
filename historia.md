### Histórias de Usuário:

#### História 1: Criar Cliente
**Descrição:**
Como usuário, desejo criar um novo cliente fornecendo seus detalhes, como nome, data de nascimento, sexo e problemas de saúde, para que possa ser registrado no sistema.

**Critérios de Aceitação:**
- O usuário pode enviar uma requisição para criar um novo cliente.
- A requisição deve incluir o nome, data de nascimento, sexo e problemas de saúde do cliente.
- Após a criação bem-sucedida, o cliente deve ser registrado no sistema com data de criação e atualização.

#### História 2: Editar Cliente
**Descrição:**
Como usuário, desejo editar os detalhes de um cliente existente para corrigir informações incorretas ou desatualizadas.

**Critérios de Aceitação:**
- O usuário pode enviar uma requisição para editar os detalhes de um cliente existente, especificando seu ID.
- A requisição pode incluir qualquer campo do cliente para ser atualizado.
- Após a edição bem-sucedida, os detalhes do cliente devem ser atualizados com a nova informação e a data de atualização deve ser modificada.

#### História 3: Obter Cliente Específico
**Descrição:**
Como usuário, desejo obter informações detalhadas sobre um cliente específico, fornecendo seu ID, para visualizar seus detalhes.

**Critérios de Aceitação:**
- O usuário pode enviar uma requisição para obter os detalhes de um cliente específico, especificando seu ID.
- A resposta deve conter todas as informações do cliente, incluindo seu nome, data de nascimento, sexo, problemas de saúde e datas de criação e atualização.

#### História 4: Listar Clientes
**Descrição:**
Como usuário, desejo ver uma lista de todos os clientes cadastrados no sistema, para poder revisar suas informações.

**Critérios de Aceitação:**
- O usuário pode enviar uma requisição para listar todos os clientes cadastrados.
- A resposta deve conter uma lista de todos os clientes, cada um com suas informações básicas, como nome, data de nascimento e sexo.

#### História 5: Calcular Risco de Saúde
**Descrição:**
Como usuário, desejo ver uma lista dos 10 clientes com maior risco de saúde, com base na soma dos graus dos problemas de saúde associados a cada cliente.

**Critérios de Aceitação:**
- O sistema deve calcular o risco de saúde para cada cliente usando a fórmula:
  ```
  sd = soma do grau dos problemas
  score = (1 / (1 + eˆ-(-2.8 + sd ))) * 100
  ```
- Deve ser implementado um endpoint para listar os 10 clientes com maior risco de saúde.
- A lista deve ser ordenada pelo score de risco, em ordem decrescente.

