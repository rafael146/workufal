package datebase;

import java.sql.SQLException;
import java.util.ArrayList;

public class MySQL extends Conexao {

    private final String insert = new String(
        "INSERT INTO  bd2.pessoa (nome,dataCad,cidade,cep) VALUES   (\"Sloane\",\"20/12/2016\",\"Bamberg\",\"65038-427\"),"
         + "  (\"Frances\",\"10/08/2019\",\"Neerrepen\",\"22610-777\"),"
         + "  (\"Zelenia\",\"25/11/2020\",\"Alguma\",\"56962-589\"),"
         + "  (\"Fay\",\"24/07/2015\",\"Brugge Bruges\",\"52978-299\"),"
         + "  (\"Shana\",\"11/05/2018\",\"Lexington\",\"13569-029\"),"
         + "  (\"Aiko\",\"14/04/2017\",\"Doetinchem\",\"79397-825\"),"
         + "  (\"Orli\",\"21/05/2023\",\"Lebbeke\",\"28883-565\"),"
         + "  (\"Aimee\",\"22/11/2016\",\"Sankt Wendel\",\"64029-657\"),"
         + "  (\"Shaine\",\"27/12/2018\",\"Cochrane\",\"50450-744\"),"
         + "  (\"Regina\",\"29/01/2018\",\"Noduwez\",\"00727-080\"),"
         + "  (\"Beatrice\",\"12/12/2023\",\"Villers-Poterie\",\"13681-837\"),"
         + "  (\"Paloma\",\"24/09/2020\",\"Mondolfo\",\"35602-253\"),"
         + "  (\"Doris\",\"06/11/2014\",\"Chalon-sur-Saone\",\"14427-499\"),"
         + "  (\"Kiara\",\"14/06/2021\",\"Cherbourg-Octeville\",\"08782-899\"),"
         + "  (\"Serena\",\"22/05/2013\",\"Fort Collins\",\"78580-141\"),"
         + "  (\"Claudia\",\"10/11/2020\",\"Mayerthorpe\",\"41961-399\"),"
         + "  (\"Candace\",\"13/04/2020\",\"Busso\",\"03238-184\"),"
         + "  (\"Hedwig\",\"14/01/2017\",\"Howrah\",\"92835-564\"),"
         + "  (\"Vera\",\"08/11/2021\",\"Opdorp\",\"17550-302\"),"
         + "  (\"Eve\",\"10/11/2021\",\"Soverzene\",\"30868-168\"),"
         + "  (\"Vivian\",\"16/06/2021\",\"Saint-Dizier\",\"23343-422\"),"
         + "  (\"Nayda\",\"26/07/2013\",\"Abolens\",\"42510-272\"),"
         + "  (\"Fatima\",\"04/12/2020\",\"Carleton\",\"84131-122\"),"
         + "  (\"Fredericka\",\"09/09/2021\",\"Atlanta\",\"78211-465\"),"
         + "  (\"Penelope\",\"04/11/2017\",\"Baunatal\",\"61315-478\"),"
         + "  (\"Rowan\",\"09/06/2016\",\"Schinebeck\",\"61966-567\"),"
         + "  (\"Nevada\",\"24/11/2020\",\"Raipur\",\"73078-830\"),"
         + "  (\"Maite\",\"02/01/2016\",\"Hompri\",\"80760-080\"),"
         + "  (\"Kelsey\",\"08/10/2013\",\"Forio\",\"63138-783\"),"
         + "  (\"Doris\",\"12/02/2019\",\"Tiegem\",\"32800-206\"),"
         + "  (\"Holly\",\"22/08/2014\",\"Nanton\",\"56934-583\"),"
         + "  (\"Kirsten\",\"24/08/2022\",\"Reze\",\"22727-200\"),"
         + "  (\"Kellie\",\"19/10/2021\",\"Deline\",\"97636-446\"),"
         + "  (\"Mallory\",\"10/07/2013\",\"Kansas City\",\"94871-240\"),"
         + "  (\"Penelope\",\"06/10/2020\",\"Workum\",\"06659-689\"),"
         + "  (\"Odessa\",\"31/12/2014\",\"Ucluelet\",\"75663-479\"),"
         + "  (\"Briar\",\"06/04/2014\",\"Sainte-Ode\",\"33318-051\"),"
         + "  (\"Janna\",\"24/08/2015\",\"Osasco\",\"64850-882\"),"
         + "  (\"Victoria\",\"10/10/2018\",\"Lingen\",\"43495-911\"),"
         + "  (\"Ima\",\"09/03/2020\",\"Delitzsch\",\"80104-647\"),"
         + "  (\"Noel\",\"31/08/2021\",\"Souvret\",\"22647-976\"),"
         + "  (\"Kathleen\",\"12/03/2024\",\"Porirua\",\"03259-427\"),"
         + "  (\"Kirestin\",\"15/08/2018\",\"Mysore\",\"11702-576\"),"
         + "  (\"Audra\",\"10/11/2020\",\"Avelgem\",\"10867-427\"),"
         + "  (\"Morgan\",\"11/07/2015\",\"Kurnool\",\"61889-328\"),"
         + "  (\"Shana\",\"28/03/2014\",\"Westerlo\",\"36262-853\"),"
         + "  (\"Alyssa\",\"30/08/2018\",\"Saint-Honor\",\"29380-729\"),"
         + "  (\"Adria\",\"05/09/2017\",\"Halifax\",\"44207-379\"),"
         + "  (\"Lesley\",\"15/08/2015\",\"Hachst\",\"22472-030\"),"
         + "  (\"Aiko\",\"20/10/2023\",\"Humbeek\",\"16950-586\"),"
         + "  (\"Eden\",\"28/08/2016\",\"Port Hope\",\"85218-717\"),"
         + "  (\"May\",\"04/05/2017\",\"Wardha\",\"93161-312\"),"
         + "  (\"Kirestin\",\"21/12/2022\",\"Neunkirchen\",\"96812-903\"),"
         + "  (\"Tamara\",\"16/10/2021\",\"Laramie\",\"76704-513\"),"
         + "  (\"Montana\",\"03/12/2017\",\"Promo-Control\",\"58019-445\"),"
         + "  (\"Jenette\",\"01/08/2016\",\"Wabamun\",\"71367-837\"),"
         + "  (\"Petra\",\"04/01/2023\",\"Recogne\",\"69135-370\"),"
         + "  (\"Lenore\",\"01/03/2021\",\"Libramont-Chevigny\",\"37623-934\"),"
         + "  (\"Francesca\",\"16/06/2015\",\"Cessnock\",\"14627-662\"),"
         + "  (\"Germaine\",\"17/07/2017\",\"Yellowknife\",\"59573-263\"),"
         + "  (\"Kelsie\",\"02/03/2014\",\"Neufeld an der Leitha\",\"25425-615\"),"
         + "  (\"Aiko\",\"22/04/2016\",\"Damme\",\"33797-445\"),"
         + "  (\"Jescie\",\"27/08/2022\",\"Vico nel Lazio\",\"48177-738\"),"
         + "  (\"Melissa\",\"18/05/2013\",\"St. Clears\",\"24763-268\"),"
         + "  (\"Charlotte\",\"17/06/2014\",\"Hartford\",\"26559-467\"),"
         + "  (\"Imogene\",\"28/12/2021\",\"Recife\",\"69563-723\"),"
         + "  (\"Pamela\",\"06/03/2016\",\"Mazy\",\"73292-211\"),"
         + "  (\"Sade\",\"22/03/2021\",\"Rhemes-Notre-Dame\",\"76422-767\"),"
         + "  (\"Hedda\",\"07/01/2021\",\"Prenzlau\",\"39780-975\"),"
         + "  (\"Ignacia\",\"12/11/2017\",\"Sankt Johann im Pongau\",\"83897-413\"),"
         + "  (\"Selma\",\"14/02/2015\",\"Collines-de-l'Outaouais\",\"36944-425\"),"
         + "  (\"Ivy\",\"28/02/2018\",\"Rhisnes\",\"47124-892\"),"
         + "  (\"Denise\",\"30/09/2017\",\"Airdrie\",\"12539-644\"),"
         + "  (\"Brittany\",\"08/12/2023\",\"Gespeg\",\"67392-237\"),"
         + "  (\"Leigh\",\"12/05/2023\",\"Ville de Maniwaki\",\"54447-785\"),"
         + "  (\"Gemma\",\"12/11/2021\",\"Goiania\",\"30276-432\"),"
         + "  (\"Daria\",\"02/03/2013\",\"Saint-Marc\",\"42926-615\"),"
         + "  (\"Eleanor\",\"22/11/2021\",\"Fayetteville\",\"55338-968\"),"
         + "  (\"Donna\",\"02/10/2020\",\"Rollegem\",\"23127-501\"),"
         + "  (\"Maryam\",\"30/11/2019\",\"Castelnuovo Magra\",\"10679-255\"),"
         + "  (\"Jolene\",\"06/01/2018\",\"Nevers\",\"96304-920\"),"
         + "  (\"Eleanor\",\"04/11/2018\",\"Sao Luis\",\"79794-013\"),"
         + "  (\"Constance\",\"01/01/2024\",\"Springdale\",\"36744-050\"),"
         + "  (\"Idona\",\"29/08/2015\",\"Pottlingen\",\"98493-431\"),"
         + "  (\"Vielka\",\"04/01/2023\",\"Bareilly\",\"58927-914\"),"
         + "  (\"Kylan\",\"18/01/2018\",\"Pak Pattan\",\"03786-829\"),"
         + "  (\"Sigourney\",\"04/03/2024\",\"Keith\",\"38732-171\"),"
         + "  (\"Shana\",\"09/06/2020\",\"l'Escaillore\",\"38186-362\"),"
         + "  (\"Anne\",\"19/08/2014\",\"Sherbrooke\",\"61780-502\"),"
         + "  (\"Alexandra\",\"30/07/2014\",\"Pradamano\",\"99746-780\"),"
         + "  (\"Grace\",\"29/12/2017\",\"Flin Flon\",\"32204-158\"),"
         + "  (\"Gillian\",\"27/03/2014\",\"Richmond\",\"71428-783\"),"
         + "  (\"Maya\",\"14/12/2023\",\"Saint-Die-des-Vosges\",\"81953-862\"),"
         + "  (\"Guinevere\",\"02/05/2016\",\"Minervino di Lecce\",\"95759-253\"),"
         + "  (\"Quon\",\"25/12/2019\",\"Leominster\",\"85180-531\"),"
         + "  (\"Winifred\",\"25/07/2018\",\"Malle\",\"13820-608\"),"
         + "  (\"Ruth\",\"13/10/2021\",\"Henin-Beaumont\",\"02001-919\"),"
         + "  (\"Hedy\",\"01/09/2019\",\"Montenero Val Cocchiara\",\"98548-181\"),"
         + "  (\"Abra\",\"03/08/2013\",\"Cartagena\",\"22458-384\"),"
         + "  (\"Kaye\",\"28/12/2018\",\"Patalillo\",\"83168-575\");");


    public MySQL() {
    	info=new InfoDB("MySQL");
    	conectar();
        inicializarDataBase();
    }

    private void inicializarDataBase() {
        try {
        	conectar();
            sql = "CREATE SCHEMA if not exists " + info.NAME_DATABASE + " DEFAULT CHARACTER SET utf8 ;";
           PREPARED_STATEMENT = CONNECTION.prepareStatement(sql);
           PREPARED_STATEMENT.executeUpdate(sql); // Utilizado para fazer algum tipo de altera√ßao no BD
            createTable();
            finalizarDataBase();
        } catch (SQLException ex) {
            System.out.println("Erro banco n„o inicializado");
        }
    }


    public void inserir100() {
        
            executarSQL(insert);
        
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

    public int qtdLinhas() {
        sql = "SELECT COUNT(*) FROM " + info.NAME_DATABASE + ".pessoa;";
        try {
            
            PREPARED_STATEMENT = CONNECTION.prepareStatement(sql);
            RESULT_SET = PREPARED_STATEMENT.executeQuery();
            while (RESULT_SET.next()) {
                return RESULT_SET.getInt(1);
            }
            finalizarDataBase();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }


    @Override
    public long importarXML(String path) {
        sql = "LOAD XML LOCAL INFILE '" + path + "\\pessoa.xml' "
                + "INTO TABLE bd2.pessoa(id, nome, dataCad, cidade, cep);";
        long tempoInicio = System.currentTimeMillis();
        executarSQL(sql);
        return System.currentTimeMillis() - tempoInicio;
    }


	@Override
	public void createTable() {
	
		        sql = "CREATE  TABLE IF NOT EXISTS " + info.NAME_DATABASE + ".pessoa ("
		                + "id mediumint(8) unsigned NOT NULL auto_increment, "
		                + "nome varchar(255) default NULL, "
		                + "dataCad varchar(255), "
		                + "cidade varchar(255), "
		                + "cep varchar(10) default NULL, "
		                + "PRIMARY KEY (id)) AUTO_INCREMENT=1;";
		        executarSQL(sql);
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
                + "FROM " + info.NAME_DATABASE + ".pessoa ORDER BY id;";
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
