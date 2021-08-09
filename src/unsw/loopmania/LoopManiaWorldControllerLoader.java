package unsw.loopmania;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;

/**
 * A LoopManiaLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * 
 * this should NOT be used to load any entities which spawn, or might be removed (use controller for that)
 * since this doesnt add listeners or teardown functions (so it will be very hacky to remove event handlers)
 */
public class LoopManiaWorldControllerLoader extends LoopManiaWorldLoader {

    private List<ImageView> entities;

    private Image characterImage;
    private Image pathImage;

    public LoopManiaWorldControllerLoader(String filename) throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        characterImage = new Image((new File("src/images/human.png")).toURI().toString(), 64, 64, false, false);
        pathImage = new Image((new File("src/images/path.png")).toURI().toString(), 64, 64, false, false);
    }

    /**
     * load character and its image
     */
    @Override
    public void onLoad(Character character) {
        ImageView view = new ImageView(characterImage);
        addEntity(character, view);
    }

    /**
     * load path and its image
     */
    @Override
    public void onLoad(PathTile pathTile) {
        ImageView view = new ImageView(pathImage);
        addEntity(pathTile, view);
    }

    /**
     * pair the  backendentity and view, so the view tracks the coordinates of the entity
     * @param entity backend entity
     * @param view frontend image to be paired with the backend entity
     */
    private void addEntity(Entity entity, ImageView view) {
        trackPositionOfNonSpawningEntities(entity, view);
        entities.add(view);
    }


    /**
     * Track the position of entities which don't spawn or require removal.
     * We only setup the node to follow the coordinates of the backend entity.<br>
     * Items which potentially need to be removed should be spawned by controller, and have listener handles and teardown functions added.
     * @param entity backend entity
     * @param node frontend image to track the coordinates of the backend entity
     */
    private static void trackPositionOfNonSpawningEntities(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
    }

    /**
     * Create a controller that can be attached to the LoopManiaView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public LoopManiaWorldController loadController() throws FileNotFoundException {
        return new LoopManiaWorldController(load(), entities);        
    }

}
