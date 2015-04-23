
import datebase.MySQL;
import datebase.PostgreSQL;

public class Bd2 {

    public static void main(String[] args) {
        MySQL bd = new MySQL();
       // bd.inserir100();
        bd.importarXML("E:\\");
      //  PostgreSQL pt=new PostgreSQL();
     //   pt.inserirCemMil();
       // System.out.println(pt.getXML());
        String path = "E:\\";//configuração pessoal
      // pt.exportarXML(path);
     //   pt.importarXML(path);

        System.out.println("Inicio");
       
        System.out.println("Fim");
//        System.out.println("Quantidade = " + bd.qtdLinhas());
      // System.out.println("Exportar = " + bd.exportarXML(path) + " Millisegundos");
     //   System.out.println("Importar = " + bd.importarXML(path) + " Millisegundos");
    }//
}
