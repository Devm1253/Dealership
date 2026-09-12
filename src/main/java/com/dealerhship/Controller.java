package com.dealerhship;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import java.io.IOException;

public class Controller {

    @FXML
    private RadioButton CRV, Rover, urus, accord, bmw, g90,
            f150, rubi, gwag, supra, c8, turbos;

    @FXML
    private Label lblCar;

    @FXML
    private TextField cusName, cusPay;

    @FXML
    private ComboBox<String> cusTerm;
    @FXML
    private Slider slider;

    @FXML
    private Label rate;

    @FXML
    private CheckBox btnWar, btnWin, btnSun, btnStri,
            btnBlrim, btnSou, btnSeats, btnLseats;

    @FXML
    private Label hbo, total, tax,
            apr, monthly, finalprice;

    public static String carName = "";
    public static double carPri = 0.0;
    public static double downPay = 0.0;
    public static double aPr = 5.0;
    public static String loanTerm = "36";
    public static String custName = "";
    public static boolean carWarr = false;
    public static boolean carTint = false;
    public static boolean carRoof = false;
    public static boolean carShad = false;
    public static boolean carBlrim = false;
    public static boolean carSound = false;
    public static boolean carSeat = false;
    public static boolean carLeat = false;

    private String[] carNAMES = {
        "Honda CR-V",
        "Land Rover Range Rover",
        "Lamborghini URUS",
        "Honda Accord",
        "BMW 540i AWD",
        "Genesis G90",
        "Ford F-150",
        "Jeep Wrangler Rubicon",
        "Mercedes G-CLASS",
        "Toyota Supra",
        "Chevrolet C8 Z06",
        "Porsche Turbo S"
    };

    private double[] carPRICES = {
        33500,
        105000,
        230000,
        29000,
        64500,
        87000,
        42000,
        55000,
        140000,
        55000,
        115000,
        250000
    };

    @FXML
    public void initialize() {

        //optionpage
        if (cusTerm != null) {
            cusTerm.setItems(FXCollections.observableArrayList("24", "36", "48", "60", "72"));

            cusTerm.setValue(loanTerm);

            //forgoing back to page2
            if (!custName.isEmpty()) {
                cusName.setText(custName);
            }
            if (downPay > 0) {
                cusPay.setText(String.valueOf(downPay));
            }
            if (!carName.isEmpty()) {
                lblCar.setText(carName + "  —  $" + String.format("%,.0f", carPri));
            }

            slider.setValue(aPr);
            rate.setText(aPr + "%");
            btnWar.setSelected(carWarr);
            btnWin.setSelected(carTint);
            btnSun.setSelected(carRoof);
            btnStri.setSelected(carShad);
            btnBlrim.setSelected(carBlrim);
            btnSou.setSelected(carSound);
            btnSeats.setSelected(carSeat);
            btnLseats.setSelected(carLeat);

            //slider moving
            slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                rate.setText(Math.round(newValue.doubleValue() * 10.0) / 10.0 + "%");
            });
        }

        //forpage3
        if (hbo != null && !carName.isEmpty()) {
            hbo.setText(carName + " - $" + (int) carPri);
        }
    }

    @FXML
    private void selectCar() {
        RadioButton[] buttons = {
            CRV, Rover, urus, accord, bmw, g90,
            f150, rubi, gwag, supra, c8, turbos};

        for (int i = 0; i < 12; i++) {
            if (buttons[i].isSelected()) {
                carName = carNAMES[i];
                carPri = carPRICES[i];
                break;
            }
        }
    }

    @FXML
    private void goNext() throws IOException {
        if (carName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Car Selected");
            alert.setContentText("Please select a vehicle first!");
            alert.showAndWait();
            return;
        }
        App.setRoot("options");
    }

    @FXML
    private void goBackexplore() throws IOException {
        App.setRoot("cars");
    }
    
    @FXML
    private void goBack() throws IOException {
        App.setRoot("options");
    }

    @FXML
    private void goToPG3() throws IOException {
        custName = cusName.getText();
        if (cusPay != null && !cusPay.getText().isEmpty()) {
            try {
                downPay = Double.parseDouble(cusPay.getText());

            } catch (NumberFormatException e) {
                downPay = 0;
            }
        }

        loanTerm = cusTerm.getValue();
        aPr = slider.getValue();
        carWarr = btnWar.isSelected();
        carTint = btnWin.isSelected();
        carRoof = btnSun.isSelected();
        carShad = btnStri.isSelected();
        carBlrim = btnBlrim.isSelected();
        carSound = btnSou.isSelected();
        carSeat = btnSeats.isSelected();
        carLeat = btnLseats.isSelected();

        App.setRoot("finalprice");
    }

    @FXML
    private void calculate() {
        double extras = 0;
        if (carWarr) {
            extras += 2500;
        }
        if (carTint) {
            extras += 600;
        }
        if (carRoof) {
            extras += 700;
        }
        if (carShad) {
            extras += 300;
        }
        if (carBlrim) {
            extras += 1000;
        }
        if (carSound) {
            extras += 900;
        }
        if (carSeat) {
            extras += 500;
        }
        if (carLeat) {
            extras += 200;
        }

        double subtotal = carPri + extras;
        double taxAmount = subtotal * 0.0875;
        double loanAmount = (subtotal + taxAmount) - downPay;
        if (loanAmount < 0) {
            loanAmount = 0;
        }
        int months = Integer.parseInt(loanTerm);

        double interestAmount = loanAmount * (aPr / 100.0) * (months / 12.0);
        double monthlyPayment = (loanAmount + interestAmount) / months;
        double finalpay = loanAmount + interestAmount + downPay;

        total.setText("$" + subtotal);
        tax.setText("$" + taxAmount);
        apr.setText("$" + interestAmount);
        monthly.setText("$" + monthlyPayment);
        finalprice.setText("$" + finalpay);
    }

    @FXML
    private void reset() throws IOException {
        carName = "";
        carPri = 0.0;
        downPay = 0.0;
        aPr = 5.0;
        loanTerm = "36";
        custName = "";
        carWarr = false;
        carTint = false;
        carRoof = false;
        carShad = false;
        carBlrim = false;
        carSound = false;
        carSeat = false;
        carLeat = false;
        App.setRoot("cars");
    }
}
