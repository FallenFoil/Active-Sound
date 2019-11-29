## Music

* int id
* String title
* String author
* int year
* List\<String> tags
* int nDownloads
* String path
* int size

- [x] *int getId()*  

- [x] *String getTitle()*  
- [ ] *void setTitle(String newTitle)*

- [x] *String getAuthor()*  
- [ ] *void setAuthor(String newAuthor)*

- [x] *int getYear()*  
- [ ] *void setYear(int newYear)*

- [x] *List\<String> getTags()*  
- [ ] *void setTags(List\<String> newTags)*

- [x] *int getNDownloads()*  
__*void SetNDownloads(int newNDownloads)* não pode ser implementado__

- [x] *String getPath()*  
- [ ] *void setPath(String newPath)*

- [x] *int size()*  
***void SetSize(int newSize)* não pode ser implementado**

- [ ] *boolean addTag(String newTag)*

- [x] *void downloadIncrement()*

---

## Users
* String username
* String password
* private List\<Integer> downloads;
* private List\<Integer> uploads;

- [x] *String getUsername()*  
- [ ] *void setUsername(String newUsername)*

- [ ] ***String getPassword()* não pode ser implementado**  
- [ ] *void setPassword(String newPassword)*

- [x] *List\<Integer> getDownloads()*
- [ ] *void setDownloads(List\<Integer> newDownloads)*

- [x] *List\<Integer> getUploads()*
- [ ] *void setUploads(List\<Integer> newUploads)*

- [ ] *void addDownload(int newDownload)*

- [ ] *void addUpload(int newUpload)*

- [x] *boolean authentication(String password)*

---

## Server

*void main(String[] args)*

---

## ActiveSound

* Map\<String username, User user> users
* Map\<Integer id, Music> musics

- [ ] *boolena login(String username, String password)*

- [ ] *boolean register(String username, String password)*

- [ ] *List<Music> search(String query)*

- [ ] *void upload(String titulo, String interprete, String ano, List\<etiquetas>)*

- [ ] *byte[] download(int id)*

*void main(String[] args)*

---

## ServerClient

* Server server
* Socket client

>Implementar no fututo se for preciso:  
>* *Thread read()*
>* *Thread write()*

*void run()*

---

## Client

>Menus:
>* *void authentication(PrintWriter out)*
>* *Menu mainMenu(PrintWriter out)*

*Thread read()*

*Thread write()*

*void main(String[] args)*