## Music

* int id
* String titulo
* String autor
* int ano
* List\<String> etiquetas
* int nDownloads
* String path
* int size;

*get/set*

---

## Users
* String name
* String password
* List\<Integer id> history

*get/set*

*boolean authentication(String password);*

---

## Server

* Map\<String username, User user> users
* Map\<Integer id, Music> musics

*boolena login(String username, String password);*

*boolean register(String username, String password);*

*List<Music> search(String query);*

*void upload(String titulo, String interprete, String ano, List\<etiquetas>);*

*byte[] download(int id);*

*void main(String[] args);*

---

## ServerClient

* Server server
* Socket client

*void run();*