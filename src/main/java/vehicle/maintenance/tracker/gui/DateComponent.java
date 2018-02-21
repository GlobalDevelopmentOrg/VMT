package vehicle.maintenance.tracker.gui;

import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import vehicle.maintenance.tracker.api.exceptions.InvalidDateException;

@SuppressWarnings("unused")
public class DateComponent extends FlowPane {

    private TextField dayInput;
    private TextField monthInput;
    private TextField yearInput;

    private static final String DAY_VALIDATION = "(0?[0][1-9])|([1-2]?[1-2][0-9])|(3?[3][0-2])";
    private static final String MONTH_VALIDATION = "(0?[0][1-9])|(1?[1][0-2])";
    private static final String YEAR_VALIDATION = "(0?[0][0-9])|([1-9]?[1-9][0-9])";

    private static final Color BORDER_COLOUR = new Color(.2, .2, .2, .6);
    private static final Color INPUT_ROW_COLOR = new Color(.8, .8, .8, 1);
    private static final Color VALID_COLOUR = new Color(.3, 1, .3, 1);
    private static final Color INVALID_COLOUR = new Color(1, .3, .3, 1);

    private static final CornerRadii radii = new CornerRadii(12);

    private static final Background VALID =
            new Background(new BackgroundFill(VALID_COLOUR, radii, Insets.EMPTY));

    private static final Background INVALID =
            new Background(new BackgroundFill(INVALID_COLOUR, radii, Insets.EMPTY));

    private static final Background BACKGROUND =
            new Background(new BackgroundFill(INPUT_ROW_COLOR, CornerRadii.EMPTY, Insets.EMPTY));


    private static final Border INPUT_BORDER = new Border(new BorderStroke(BORDER_COLOUR, BorderStrokeStyle.SOLID, radii, BorderStroke.THIN));
    private static final Font INPUT_FONT = new Font(18);


    public DateComponent(String date) throws InvalidDateException {
        super(Orientation.VERTICAL);
        this.init();
        String[] parts = date.split("/");
        if(parts.length == 3){
            if(parts[0].matches(DateComponent.DAY_VALIDATION)){
                this.dayInput.setText(parts[0]);
            }else{
                throw new InvalidDateException(parts[0] + " is not valid input for day, expected dd");
            }
            if(parts[1].matches(DateComponent.MONTH_VALIDATION)){
                this.monthInput.setText(parts[1]);
            }else{
                throw new InvalidDateException(parts[1] + " is not valid input for month, expected mm");
            }
            if(parts[2].matches(DateComponent.YEAR_VALIDATION)){
                this.yearInput.setText(parts[2]);
            }else{
                throw new InvalidDateException(parts[2] + " is not valid input for year, expected yy");
            }
        }else{
            throw new InvalidDateException("expected dd/mm/yy but got " + date);
        }
        // If we have no errors the given time is valid
        // so we will just set all backgrounds
        // to show valid background colours
        this.dayInput.setBackground(DateComponent.VALID);
        this.monthInput.setBackground(DateComponent.VALID);
        this.yearInput.setBackground(DateComponent.VALID);
    }

    public DateComponent(){
        super(Orientation.VERTICAL);
        this.init();
    }

    private void init(){
        this.dayInput = new TextField();
        this.dayInput.setFont(DateComponent.INPUT_FONT);
        this.dayInput.setPrefColumnCount(2);
        this.dayInput.setAlignment(Pos.CENTER);
        this.dayInput.setBorder(DateComponent.INPUT_BORDER);

        this.monthInput = new TextField();
        this.monthInput.setFont(DateComponent.INPUT_FONT);
        this.monthInput.setPrefColumnCount(2);
        this.monthInput.setAlignment(Pos.CENTER);
        this.monthInput.setBorder(DateComponent.INPUT_BORDER);

        this.yearInput = new TextField();
        this.yearInput.setFont(DateComponent.INPUT_FONT);
        this.yearInput.setPrefColumnCount(2);
        this.yearInput.setAlignment(Pos.CENTER);
        this.yearInput.setBorder(DateComponent.INPUT_BORDER);

        /*
         * ------------------------- EVENT LISTENERS ----------------------------
         *  Added event listeners to validate input in all 3 input fields using
         *  each of the validation regex strings.
         * ----------------------------------------------------------------------
         */
        this.dayInput.onKeyReleasedProperty().set(this.validate(this.dayInput, DateComponent.DAY_VALIDATION));
        this.monthInput.onKeyReleasedProperty().set(this.validate(this.monthInput, DateComponent.MONTH_VALIDATION));
        this.yearInput.onKeyReleasedProperty().set(this.validate(this.yearInput, DateComponent.YEAR_VALIDATION));

        HBox inputRow = new HBox(10);

        inputRow.getChildren().addAll(
                this.dayInput,
                this.monthInput,
                this.yearInput
        );

        inputRow.setAlignment(Pos.CENTER);
        inputRow.setPadding(new Insets(15));
        inputRow.setBackground(DateComponent.BACKGROUND);

        this.getChildren().add(inputRow);
        this.autosize();
    }

    private EventHandler<KeyEvent> validate(TextField field, String validation){
        return (keyEvent) -> {
            if(field.getText().matches(validation)){
                field.setBackground(DateComponent.VALID);
            }else{
                field.setBackground(DateComponent.INVALID);
            }
        };
    }

    public boolean hasValidDate(){
        return this.dayInput.getText().matches(DateComponent.DAY_VALIDATION)
                && this.monthInput.getText().matches(DateComponent.MONTH_VALIDATION)
                && this.yearInput.getText().matches(DateComponent.YEAR_VALIDATION);
    }

    public String getDay(){
        return this.dayInput.getText();
    }

    public String getMonth(){
        return this.monthInput.getText();
    }

    public String getYear(){
        return this.yearInput.getText();
    }

}
