# ActiveSound

## Description

ActiveSound is sharing platform that allows musicians to share their creations. This platform uses java 11.

## Functionalities

* __Authentication__ and __register__ of users;
* __Publish__ (upload) a music by providing data about it (__title__, __interpreter__, __year__, and __tags__) and assign it an __id__;
* Perform a music search by sending a tag to search for and getting back a playlist with:
  * __Id__;
  * __Title__;
  * __Interpreter__;
  * __Year__;
  * __Tags__;
  * __Nº Downloads__.
* Download a music by giving its __id__;
* A client cannot have more than one __socket__

## Extras

### Downloads (done)

* Have maximum number of downloads (_MAXDOWN_);
* When the maximum is reached the following requests must wait;
* Have a good download scheduling strategy.

### Notifications

* Receive notifications of new music uploads while the customer is on;
* The notification must contain the __title__ and __author__;
* Receive notifications even during other operations.

### File Size

* It cannot be assumed that the files all fit in memory;
* Have a __limit__ (_MAXSIZE_) size in bytes on both client and server.

---

## Contributors

* [__Luís Macedo__](https://github.com/FallenFoil)
* [__Ângelo Sousa__](https://github.com/AngeloACSousa)
* [__André Martins__](https://github.com/AROM98)
* [__Diogo Monteiro__](https://github.com/DxMonteiro)
