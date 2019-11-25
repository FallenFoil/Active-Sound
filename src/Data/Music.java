package Data;

import java.util.ArrayList;
import java.util.List;

public class Music {
    /*int id
* String titulo
* String autor
* int ano
* List\<String> etiquetas
* int nDownloads
* String path
* int size;*/

    int id;
    String titulo;
    String autor;
    int ano;
    List<String> etiquetas;
    int nDownloads;
    String path;
    int size;

    public Music(int id, String titulo, String autor, int ano, List<String> etiquetas,
                 int nDownloads, String path, int size){

        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.etiquetas = new ArrayList<>(etiquetas);
        this.nDownloads = nDownloads;
        this.path = path;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public int getAno() {
        return ano;
    }

    public int getSize() {
        return size;
    }

    public int getnDownloads() {
        return nDownloads;
    }

    public String getAutor() {
        return autor;
    }

    public String getPath() {
        return path;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void downloadIncrement(){
        synchronized (this){
            nDownloads++;
        }
    }

}
