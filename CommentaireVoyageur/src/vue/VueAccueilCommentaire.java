package vue;

import java.util.List;

import org.controlsfx.control.Rating;

import com.sun.media.jfxmedia.logging.Logger;

import controleur.Controleur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import modele.Commentaire;
import modele.ListCellCommentaire;

public class VueAccueilCommentaire extends Vue {
	
	public static VueAccueilCommentaire instance;
	public static VueAccueilCommentaire getInstance() {if(null == instance)instance = new VueAccueilCommentaire(); return instance;}
		
	protected Controleur controleur;
	
	private final static int LIMITE_COMMENTAIRES_LISTE = 10;
	
	private VueAccueilCommentaire () {
		super("vue_accueil_commentaire.fxml");
		super.controleur = this.controleur = new Controleur();
		Logger.logMsg(Logger.INFO, "new VueAccueilCommentaire()");
	}
	
	/**
	 * Permet de gerer les controles
	 */
	public void activerControles(){
		super.activerControles();
		Button actionLaisserCommentaire = (Button)lookup("#vue-accueil-action-laisser-commentaire");
		
		actionLaisserCommentaire.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Logger.logMsg(Logger.INFO, "Clic sur actionLaisserCommentaire VueAcceuilCommentaire()");
				controleur.notifierNavigationVueAjoutCommentaire();
			}});
		
		
		System.out.println(System.getProperty("user.dir"));
		

		MenuButton menuOptions = (MenuButton)lookup("#vue-accueil-menu-options");
		Image imageMenuBurger = new Image("file:src/vue/decoration/menu-burger.png", 20, 20, true, true);
		menuOptions.setGraphic(new ImageView(imageMenuBurger));
		
		ObservableList<MenuItem> options = menuOptions.getItems();
		
		for (MenuItem menuItem : options)
		{
			menuItem.setOnAction(new EventHandler<ActionEvent>() 
			{
				@Override
				public void handle(ActionEvent e) 
				{
					MenuItem source = (MenuItem)e.getSource();
					
					if(source.getId().equals("vue-accueil-action-synchroniser"))
						controleur.notifierSynchronisation();
				}
			});
		}
		

	}
	
	public void afficherCommentaires(List<Commentaire> listeCommentaire) {
		
		ListView<Commentaire> listViewCommentaires = (ListView<Commentaire>)lookup("#vue-accueil-listView");
		ObservableList<Commentaire> listeCommentaireAfficher = FXCollections.observableArrayList();

		//D�finit une limite selon la taille de la liste en BDD et la taille donn�e par l'application
		int limite = (listeCommentaire.size() >= LIMITE_COMMENTAIRES_LISTE) ? LIMITE_COMMENTAIRES_LISTE : listeCommentaire.size();
		for(int i = 0; i < limite; i++)
		{
			Commentaire commentaire = listeCommentaire.get(i);
			listeCommentaireAfficher.add(commentaire);
		}
		
				
		listViewCommentaires.setItems(listeCommentaireAfficher);
		listViewCommentaires.setCellFactory(new Callback<ListView<Commentaire>, ListCell<Commentaire>>() {
			@Override
			public ListCell<Commentaire> call(ListView<Commentaire> listView)
			{
				return new ListCellCommentaire();
			}
		});

		
		listViewCommentaires.setOnMouseClicked(new EventHandler<Event>()
		{
			@Override
			public void handle(Event arg0)
			{
				controleur.notifierNavigationVueCommentaires(listViewCommentaires.getSelectionModel().getSelectedItem().getId());
			}
		});
	}
}
