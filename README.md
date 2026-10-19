<div align="center">

🍔 EntregaFood
Seu próximo pedido começa aqui.
Aplicativo Android de delivery — experiência do cliente
<p>
  <img src="https://img.shields.io/badge/PLATAFORMA-Android-111111?style=for-the-badge&logo=android&logoColor=white" alt="Plataforma Android">
  <img src="https://img.shields.io/badge/LINGUAGEM-Kotlin-F97316?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin">
  <img src="https://img.shields.io/badge/INTERFACE-Jetpack%20Compose-111111?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose">
</p>


Um projeto acadêmico que une desenvolvimento mobile, organização de código e uma experiência de navegação simples e intuitiva.
</div>

✨ Sobre o projeto
O EntregaFood é um projeto de aplicativo de delivery desenvolvido para Android. Esta versão concentra-se na experiência do cliente, com fluxo de autenticação, navegação entre telas e visualização de restaurantes.

O projeto faz parte da jornada de aprendizado em desenvolvimento de sistemas e serve como base para evoluir a aplicação com integrações e funcionalidades adicionais.
📱 O que você encontra nesta versão
- Login: tela de acesso do cliente com credenciais de demonstração.
- Tela inicial: ponto de partida para explorar a experiência do aplicativo.
- Restaurantes: visualização da lista e acesso à tela de um restaurante.
- Navegação: fluxo entre login, início e detalhes do restaurante.
- Sessão local: estrutura para armazenar os dados de sessão no dispositivo.
Importante: esta versão utiliza repositórios e dados simulados (mock data) para autenticação e restaurantes. A integração real com o backend não está confirmada nesta versão.

🧰 Tecnologias
Tecnologia	Utilização
Kotlin	Linguagem principal do aplicativo
Jetpack Compose	Construção da interface declarativa
Material 3	Componentes e padrões visuais
Navigation Compose	Navegação entre telas
ViewModel	Organização do estado e da lógica de interface
StateFlow	Exposição reativa de estados
Gradle / Kotlin DSL	Configuração e build do projeto
EncryptedSharedPreferences	Armazenamento local protegido da sessão


🗂️ Estrutura do projeto:
EntregaFood/

├── app/

│   └── src/main/

│       ├── AndroidManifest.xml

│       └── java/com/entregafood/app/

│           ├── MainActivity.kt

│           ├── data/

│           │   ├── AuthRepository.kt

│           │   ├── Models.kt

│           │   └── SessionStorage.kt

│           └── ui/

│               ├── AuthViewModel.kt

│               ├── home/

│               │   └── HomeScreen.kt

│               ├── login/

│               │   └── LoginScreen.kt

│               ├── navigation/

│               │   └── AppNavigation.kt

│               ├── restaurant/

│               │   └── RestaurantScreen.kt

│               └── theme/

│                   └── Theme.kt

├── gradle/

├── build.gradle.kts

├── gradle.properties

├── settings.gradle.kts

└── README.md


🚀 Como executar
Pré-requisitos
- Android Studio instalado.
- JDK compatível com a configuração do projeto.
- Emulador Android configurado ou dispositivo físico com depuração USB habilitada.
Passo a passo
1. Clone este repositório:
   git clone https://github.com/annahigaf/EntregaFood-Mobile.git
2. Abra o Android Studio e selecione File → Open.
3. Escolha a pasta EntregaFood-Mobile (a pasta que contém settings.gradle.kts).
4. Aguarde a sincronização do Gradle.
5. Selecione um emulador ou conecte um dispositivo Android.
6. Clique em Run ▶ para executar o aplicativo.
Na primeira abertura, o Android Studio pode precisar baixar dependências do Gradle. Mantenha a conexão com a internet durante a sincronização.


🔐 Acesso para demonstração
Use as credenciais abaixo na versão atual, com autenticação simulada:
Campo	Credencial
E-mail	cliente@entregafood.com
Senha	123456


Essas credenciais são exclusivas para demonstração e não devem ser utilizadas em um sistema de produção.
🧭 Próximas evoluções
- [ ] Integrar autenticação com a API backend.
- [ ] Carregar restaurantes a partir de dados reais.
- [ ] Implementar cardápio e detalhes dos produtos.
- [ ] Desenvolver carrinho e fluxo de pedidos.
- [ ] Adicionar acompanhamento de pedidos.
- [ ] Criar testes automatizados para as principais funcionalidades.
- [ ] Aprimorar acessibilidade, feedbacks e estados de carregamento.
Esta lista representa ideias de evolução, não funcionalidades já concluídas.

🎓 Contexto acadêmico
Projeto desenvolvido no contexto de Sistemas de Informação, para a matéria de desenvolvimento de sistemas do professor Gilberto, com foco em desenvolvimento de aplicações mobile, arquitetura de interface e organização de código.

👩‍💻 Autoria 
Anna Julia Higa Farincho
Leticia Macedo
Richard Ferreira
Geovane Soares
