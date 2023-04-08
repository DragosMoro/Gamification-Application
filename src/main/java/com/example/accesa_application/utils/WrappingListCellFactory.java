package com.example.accesa_application.utils;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class WrappingListCellFactory implements Callback<ListView<String>, ListCell<String>> {

    @Override
    public ListCell<String> call(ListView<String> listView) {
        listView.setMinWidth(270);
        return new ListCell<>() {
            private final Label label;

            {
                label = new Label();
                label.setWrapText(true);
                label.setPrefWidth(270); // Set the preferred width to 100px
                label.setMaxWidth(Double.MAX_VALUE);
                label.setTextFill(javafx.scene.paint.Color.WHITE);
                label.setFont(new javafx.scene.text.Font("Arial Rounded MT Bold", 12.0));
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    label.setText(item);
                    setGraphic(label);
                }
            }
        };
    }
}
