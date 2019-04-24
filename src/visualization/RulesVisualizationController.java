package visualization;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.converter.NumberStringConverter;



import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class RulesVisualizationController implements Initializable {

    @FXML
    private GridPane visualizationGridPane;

    @FXML
    private Spinner<Integer> sizeN;

    @FXML
    private Spinner<Integer> sizeM;

    @FXML
    private Button updateSizeButton;

    @FXML
    private ChoiceBox<Rules> rulesChoiceBox;

    private RulesCalculator rulesCalculator;
    private ObservableList<ObservableList<Integer>> arrayRules;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rulesCalculator = new RulesCalculator();
        sizeM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100));
        sizeN.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100));
        rulesChoiceBox.setItems(FXCollections.observableArrayList(Rules.values()));
        rulesChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener( (ObservableValue<? extends Rules> observable, Rules oldValue, Rules newValue) -> {
                    if(newValue != null) arrayRules = rulesCalculator.calculate(newValue, arrayRules);
                    setColorsOnRectangles();
                   });
        updateSizeButton.setOnAction((e)->{initializeArray();updateVisualization();});
        updateSizeButton.fire();
    }

    private void updateVisualization() {
        System.out.println("updateVisualization");
        resizeVisualization();
        setColorsOnRectangles();
        rulesChoiceBox.setValue(null);
    }
    private void setColorsOnRectangles()
    {
        FilteredList<Node> rectangles = visualizationGridPane.getChildren().filtered(n -> n instanceof Rectangle);
        for(int i = 0; i < arrayRules.size(); i++)
        {
            for(int j = 0; j < arrayRules.get(0).size();j++)
            {
                Rectangle rectangle = (Rectangle) rectangles.get(i*arrayRules.get(0).size() + j);
                if(arrayRules.get(i).get(j) == 1)
                    rectangle.setFill(Color.BLACK);
                else
                    rectangle.setFill(Color.WHITE);
            }
        }
    }
    public void resizeVisualization()
    {
        visualizationGridPane.getChildren().clear();
        while(visualizationGridPane.getRowConstraints().size() > 0){
            visualizationGridPane.getRowConstraints().remove(0);
        }
        while(visualizationGridPane.getColumnConstraints().size() > 0){
            visualizationGridPane.getColumnConstraints().remove(0);
        }
        visualizationGridPane.resize(300,300);
        double width = 100./ arrayRules.get(0).size();
        double height = 100./ arrayRules.size();
        for (int i = 0; i < arrayRules.get(0).size(); i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setHgrow(Priority.ALWAYS);
            colConst.setPercentWidth(width);
            visualizationGridPane.getColumnConstraints().add(colConst);
        }
        for(int i = 0; i < arrayRules.size(); i++){
            RowConstraints rowConst = new RowConstraints();
            rowConst.setVgrow(Priority.ALWAYS);
            rowConst.setPercentHeight(height);
            visualizationGridPane.getRowConstraints().add(rowConst);
        }

        for(int i = 0; i < arrayRules.size(); i++)
            for(int j = 0; j <arrayRules.get(0).size(); j++){
                Rectangle square = new Rectangle();
                square.setStrokeType(StrokeType.INSIDE);
                square.setStroke(Color.BLACK);
                square.widthProperty().bind(visualizationGridPane.widthProperty().divide(arrayRules.get(0).size()));
                square.heightProperty().bind(visualizationGridPane.heightProperty().divide(arrayRules.size()));
                visualizationGridPane.add(square,j,i);
            }
    }


    private void initializeArray()
    {
        arrayRules = FXCollections.observableArrayList();
        for (int i = 0; i < sizeN.getValue(); i++) {
            ObservableList<Integer> row = FXCollections.observableArrayList();
            arrayRules.add(i, row);
            for (int j = 0; j < sizeM.getValue(); j++) {
                row.add(0);
            }

        }
        arrayRules.get(0).set(sizeM.getValue()/2,1);
    }
}
