package fr.will33.souppvp.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class StringUtil {

    public static String formatCurrency(double balance) {
        StringBuilder string = new StringBuilder();
        // We removes some cents if it's something like 20.20381 it would set it
        // to 20.20

        String[] theAmount = BigDecimal.valueOf(balance).toPlainString().split("\\.");
        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
        unusualSymbols.setGroupingSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("###,###", unusualSymbols);
        String amount;
        try {
            amount = decimalFormat.format(Double.parseDouble(theAmount[0]));
        } catch (NumberFormatException e) {
            amount = theAmount[0];
        }
        string.append(amount).append(" ");
        return string.toString();
    }

}
