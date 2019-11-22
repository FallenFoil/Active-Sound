# Active-Sound

## Description

Active-Sound is sharing platform that allows musicians to share their creations. This platform uses java 11.

## Deadline

* __3 de janeiro 2020__

## Functionalities

* __Autenticação__ e __registo__ de clientes;
* __Publicar__ uma música, fornecendo dados sobre ele (__título__, __intérprete__, __ano__ e __etiquetas__) e atribuir-lhe um __id__;
* Efetuar uma procura de música, enviando uma etiqueta a procurar e recebendo de volta uma lista com:
  * __Id__;
  * __Titulo__;
  * __Interprete__;
  * __Ano__;
  * __Etiquetas__;
  * __Nº downloads__.
* Fazer download de uma música dando o __id__;
* Um cliente não pode ter mais do que um __socket__.

## Extras

### Downloads

* Ter numero máximo de downloads (_MAXDOWN_);
* Quando o máximo é atingido os pedidos seguintes devem esperar;
* Ter uma boa estratégia de escalonamento de downloads.

### Notifications

* Receber notificações de novos uploads de música, enquanto o cliente estiver ligado;
* A notificação deve conter o __título__ e o __autor__;
* Receber notificações mesmo durante outras operações.

### File Size

* Não se pode assumir que os ficheiros cabem todos na memória;
* Ter um tamanho __limite__ (_MAXSIZE_) em bytes, no cliente e no servidor.

---

## Contributors

* [__Luís Macedo__](https://github.com/FallenFoil)
* [__Ângelo Sousa__](https://github.com/AngeloACSousa)
* [__Rafael Martins__](https://github.com/AROM98)
* [__Diogo Monteiro__](https://github.com/DxMonteiro)
