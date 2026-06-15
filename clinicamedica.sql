create database ClinicaMedica;
use ClinicaMedica;

CREATE TABLE Consulta (
    ID_Consulta INT AUTO_INCREMENT PRIMARY KEY,
    ID_Paciente int NOT NULL,
    CRM VARCHAR(20) NOT NULL,
    ID_Setor INT NOT NULL,
    DataHora DATETIME NOT NULL,
    Valor DECIMAL(10,2) NOT NULL,
    Tipo VARCHAR(30),
    Status VARCHAR(30),
    Observacoes TEXT,
    FOREIGN KEY (ID_Paciente) REFERENCES Paciente(ID_Paciente),
    FOREIGN KEY (CRM) REFERENCES Medico(CRM),
    FOREIGN KEY (ID_Setor) REFERENCES Setor(ID_Setor)
);


CREATE TABLE Paciente (
    ID_Paciente INT AUTO_INCREMENT PRIMARY KEY,
    CPF CHAR(14) UNIQUE,
    Nome VARCHAR(100) NOT NULL,
    Rg char(20) unique,
    Data_Nascimento DATE NOT NULL,
    Email VARCHAR(100) UNIQUE,
    Telefone VARCHAR(20) NOT NULL,
    Contato_Emergencia VARCHAR(20),
    ID_Endereco INT NOT NULL,
    ID_Plano INT NOT NULL,
    FOREIGN KEY (ID_Endereco) REFERENCES Endereco(ID_Endereco),
    FOREIGN KEY (ID_Plano) REFERENCES Plano_Saude(ID_Plano)
);

CREATE TABLE Usuario (
    ID_Usuario INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(100) NOT NULL,
    Telefone VARCHAR(20),
    CPF CHAR(14) NOT NULL UNIQUE,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Cargo varchar(50) NOT NULL ,
    Setor varchar(50) NOT NULL,
    Nivel_Acesso VARCHAR(50) NOT NULL
    );

CREATE TABLE Exame (
ID_Exame INT AUTO_INCREMENT  PRIMARY KEY,
ID_Consulta INT NOT NULL,
Tipo_Exame VARCHAR(100) NOT NULL,
Data_Exame DATE NOT NULL,
Resultado TEXT,
Status VARCHAR(30),
Valor DECIMAL(10,2),
FOREIGN KEY(ID_Consulta) REFERENCES Consulta(ID_Consulta)
);

CREATE TABLE Endereco (
    ID_Endereco INT AUTO_INCREMENT PRIMARY KEY,
    Estado VARCHAR(50) NOT NULL,
    Cidade VARCHAR(50) NOT NULL,
    Bairro VARCHAR(50) NOT NULL,
    CEP VARCHAR(10) NOT NULL,
    Rua VARCHAR(100) NOT NULL,
    Numero VARCHAR(10) NOT NULL
);