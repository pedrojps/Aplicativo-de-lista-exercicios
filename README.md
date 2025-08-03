
# 📱 Aplicativo de Lista de Exercícios

Este é um aplicativo Android nativo desenvolvido em **Kotlin**, com foco em registrar treinos e exercícios, utilizando armazenamento local com **Room** e sincronização em nuvem com **Firebase Firestore** e **Firebase Storage**.

---

## 🚀 Funcionalidades

- Cadastro e listagem de **treinos**
- Cadastro de **exercícios** com suporte a imagem
- Autenticação de usuário via e-mail e senha
- Sincronização automática entre banco local (Room) e Firebase
- Armazenamento de imagens no Firebase Storage
- Layout simples e responsivo seguindo padrões Material Design

---

## 🛠️ Tecnologias utilizadas

- **Kotlin**
- **Room** (banco de dados local)
- **Firebase Authentication**
- **Firebase Firestore**
- **Firebase Storage**
- **Hilt** (injeção de dependência)
- **MVVM** (arquitetura)
- **LiveData & ViewModel**
- **Coroutines**
- **Glide** (carregamento de imagens)

---

## 🗂️ Estrutura do Projeto (resumo)

```
com.example.treinoapp/
├── data/
│   ├── local/ → entidades Room, DAOs e banco
│   ├── firebase/ → Firestore, Storage, Auth
│   ├── model/ → modelos de domínio
│   └── repository/ → lógica de dados
├── ui/
│   ├── auth/ → Login e Registro
│   ├── treino/ → Fragments de treino
│   └── exercicio/ → Fragments de exercício
├── viewmodel/ → ViewModels centralizadas
├── di/ → Configuração do Hilt
├── utils/ → Extensões e constantes
├── MainActivity.kt
└── TreinoApp.kt
```

---

## ▶️ Como rodar

1. Clone o projeto:
   ```bash
   git clone https://github.com/pedrojps/Aplicativo-de-lista-exercicios.git
   ```

2. Abra no **Android Studio**

3. Configure o Firebase:
   - Crie um projeto no [Firebase Console](https://console.firebase.google.com)
   - Ative Authentication (Email/Senha)
   - Ative Firestore Database e Storage
   - Baixe o arquivo `google-services.json` e adicione em:
     ```
     app/google-services.json
     ```

4. Compile e rode em um dispositivo físico ou emulador.

---

## 📌 Objetivo

Este projeto foi desenvolvido como teste técnico com foco em boas práticas de desenvolvimento Android e sincronização de dados entre local e nuvem.
