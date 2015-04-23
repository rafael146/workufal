package datebase;

import java.sql.SQLException;
import java.util.ArrayList;

public class PostgreSQL extends Conexao {

 
    private final String[] insert = new String[]{"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Sloane','20/12/2016','Bamberg','65038-427');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Frances','10/08/2019','Neerrepen','22610-777');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Zelenia','25/11/2020','G�strow','56962-589');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Fay','24/07/2015','Brugge Bruges','52978-299');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Shana','11/05/2018','Lexington','13569-029');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Aiko','14/04/2017','Doetinchem','79397-825');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Orli','21/05/2023','Lebbeke','28883-565');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Aimee','22/11/2016','Sankt Wendel','64029-657');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Shaine','27/12/2018','Cochrane','50450-744');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Regina','29/01/2018','Noduwez','00727-080');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Beatrice','12/12/2023','Villers-poterie','13681-837');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('paloma','24/09/2020','Mondolfo','35602-253');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Doris','06/11/2014','Chalon-sur-Saône','14427-499');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Kiara','14/06/2021','Cherbourg-Octeville','08782-899');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Serena','22/05/2013','Fort Collins','78580-141');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Claudia','10/11/2020','Mayerthorpe','41961-399');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Candace','13/04/2020','Busso','03238-184');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Hedwig','14/01/2017','Howrah','92835-564');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Vera','08/11/2021','Opdorp','17550-302');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Eve','10/11/2021','Soverzene','30868-168');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Vivian','16/06/2021','Saint-Dizier','23343-422');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Nayda','26/07/2013','Abolens','42510-272');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Fatima','04/12/2020','Carleton','84131-122');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Fredericka','09/09/2021','Atlanta','78211-465');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('penelope','04/11/2017','Baunatal','61315-478');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Rowan','09/06/2016','Sch�nebeck','61966-567');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Nevada','24/11/2020','Raipur','73078-830');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Maite','02/01/2016','Hompr�','80760-080');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Kelsey','08/10/2013','Forio','63138-783');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Doris','12/02/2019','Tiegem','32800-206');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Holly','22/08/2014','Nanton','56934-583');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Kirsten','24/08/2022','Rezé','22727-200');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Kellie','19/10/2021','Deline','97636-446');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Mallory','10/07/2013','Kansas City','94871-240');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('penelope','06/10/2020','Workum','06659-689');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Odessa','31/12/2014','Ucluelet','75663-479');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Briar','06/04/2014','Sainte-Ode','33318-051');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Janna','24/08/2015','Osasco','64850-882');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Victoria','10/10/2018','Lingen','43495-911');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Ima','09/03/2020','Delitzsch','80104-647');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Noel','31/08/2021','Souvret','22647-976');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Kathleen','12/03/2024','porirua','03259-427');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Kirestin','15/08/2018','Mysore','11702-576');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Audra','10/11/2020','Avelgem','10867-427');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Morgan','11/07/2015','Kurnool','61889-328');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Shana','28/03/2014','Westerlo','36262-853');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Alyssa','30/08/2018','Saint-Honor?','29380-729');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Adria','05/09/2017','Halifax','44207-379');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Lesley','15/08/2015','Höchst','22472-030');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Aiko','20/10/2023','Humbeek','16950-586');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Eden','28/08/2016','port Hope','85218-717');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('May','04/05/2017','Wardha','93161-312');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Kirestin','21/12/2022','Neunkirchen','96812-903');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Tamara','16/10/2021','Laramie','76704-513');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Montana','03/12/2017','promo-Control','58019-445');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Jenette','01/08/2016','Wabamun','71367-837');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('petra','04/01/2023','Recogne','69135-370');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Lenore','01/03/2021','Libramont-Chevigny','37623-934');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Francesca','16/06/2015','Cessnock','14627-662');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Germaine','17/07/2017','Yellowknife','59573-263');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Kelsie','02/03/2014','Neufeld an der Leitha','25425-615');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Aiko','22/04/2016','Damme','33797-445');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Jescie','27/08/2022','Vico nel Lazio','48177-738');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Melissa','18/05/2013','St. Clears','24763-268');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Charlotte','17/06/2014','Hartford','26559-467');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Imogene','28/12/2021','Recife','69563-723');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('pamela','06/03/2016','Mazy','73292-211');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Sade','22/03/2021','Rhemes-Notre-Dame','76422-767');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Hedda','07/01/2021','prenzlau','39780-975');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Ignacia','12/11/2017','Sankt Johann im pongau','83897-413');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Selma','14/02/2015','Collines-de-l Outaouais','36944-425');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Ivy','28/02/2018','Rhisnes','47124-892');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Denise','30/09/2017','Airdrie','12539-644');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Brittany','08/12/2023','Gespeg','67392-237');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Leigh','12/05/2023','Ville de Maniwaki','54447-785');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Gemma','12/11/2021','Goiânia','30276-432');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Daria','02/03/2013','Saint-Marc','42926-615');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Eleanor','22/11/2021','Fayetteville','55338-968');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Donna','02/10/2020','Rollegem','23127-501');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Maryam','30/11/2019','Castelnuovo Magra','10679-255');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Jolene','06/01/2018','Nevers','96304-920');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Eleanor','04/11/2018','São Luís','79794-013');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Constance','01/01/2024','Springdale','36744-050');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Idona','29/08/2015','pttlingen','98493-431');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Vielka','04/01/2023','Bareilly','58927-914');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Kylan','18/01/2018','pak pattan','03786-829');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Sigourney','04/03/2024','Keith','38732-171');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Shana','09/06/2020','Escaillre','38186-362');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Anne','19/08/2014','Sherbrooke','61780-502');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Alexandra','30/07/2014','pradamano','99746-780');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Grace','29/12/2017','Flin Flon','32204-158');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Gillian','27/03/2014','Richmond','71428-783');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Maya','14/12/2023','Saint-Dié-des-Vosges','81953-862');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Guinevere','02/05/2016','Minervino di Lecce','95759-253');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Quon','25/12/2019','Leominster','85180-531');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Winifred','25/07/2018','Malle','13820-608');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Ruth','13/10/2021','Hénin-Beaumont','02001-919');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Hedy','01/09/2019','Montenero Val Cocchiara','98548-181');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Abra','03/08/2013','Cartagena','22458-384');",
    		"INSERT INTO pessoa (nome,dataCad,cidade,cep) VALUES ('Kaye','28/12/2018','patalillo','83168-575');"}; 
 public PostgreSQL() {
		
		info=new InfoDB("PostgreSQL");
		conectar();
		createTable();
	}
	
	
	 public void inserir100() {
		 for (String inserir : this.insert) {
	            executarSQL(inserir);
	        }
	    }

	    public void inserir10mil() {

	        for (int i = 0; i < 100; i++) {
	            inserir100();
	        }
	    }

	    public void inserirCemMil() {
	        for (int i = 0; i < 10; i++) {
	            inserir10mil();
	        }
	    }
	
	 
	 @Override
	public void createTable() {
	        sql = "CREATE  TABLE if not exists pessoa ("
	                + "id serial primary key , "
	                + "nome varchar(255) NOT NULL, "
	                + "dataCad varchar(255), "
	                + "cidade varchar(255), "
	                + "cep varchar(10) NOT NULL "
	                
	                + ") ;";
	        executarSQL(sql);
	    }
	 @Override
	 public long importarXML(String path) {
	        sql = "LOAD XML LOCAL INFILE '" + path + "/pessoa.xml' "
	                + "INTO TABLE pessoa(id, nome, dataCad, cidade, cep);";
	        long tempoInicio = System.currentTimeMillis();
	        executarSQL(sql);
	        return System.currentTimeMillis() - tempoInicio;
	    }
	 @Override
	 public ArrayList<String> getXML() {
	        sql = "SELECT CONCAT('\n\t<row>\n',"
	                + "'\t\t<field name=\"id\">', id,'</field>\n',"
	                + "'\t\t<field name=\"nome\">', nome,'</field>\n',"
	                + "'\t\t<field name=\"dataCad\">', dataCad,'</field>\n',"
	                + "'\t\t<field name=\"cidade\">', cidade,'</field>\n',"
	                + "'\t\t<field name=\"cep\">', cep,'</field>\n',"
	                + "'\t</row>') "
	                + "FROM  pessoa ORDER BY id;";
	        ArrayList<String> dados = null;
	        try {
	            conectar();
	            PREPARED_STATEMENT = CONNECTION.prepareStatement(sql);
	            RESULT_SET = PREPARED_STATEMENT.executeQuery();
	            dados = new ArrayList<>();
	            dados.add("<?xml version=\"1.0\"?>\n\n");
	            dados.add("<resultset statement=\"SELECT * FROM Pessoa\" "
	                    + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
	            while (RESULT_SET.next()) {
	                dados.add(RESULT_SET.getString(1));
	            }
	            dados.add("/n</resultset>");
	            finalizarDataBase();
	        } catch (SQLException ex) {
	            System.out.println("Comando SQL nao executado:" + ex.getMessage());
	        }
	        return dados;
	    }


}

