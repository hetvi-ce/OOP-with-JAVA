package util;

import model.Account;

/**
 * StatementFormatter generates clean, multi-line account statements using StringBuilder.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 04 & 06
 */
public class StatementFormatter {

    public static String buildStatement(Account account) {
        if (account == null) {
            return "No account specified for statement.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("====================================================\n");
        sb.append("            MINIBANK ACCOUNT STATEMENT              \n");
        sb.append("====================================================\n");
        sb.append(String.format(" Account Number : %s%n", account.getAccountNumber()));
        sb.append(String.format(" Account Holder : %s%n", account.getOwnerName()));
        sb.append(String.format(" Account Type   : %s%n", account.getClass().getSimpleName()));
        sb.append(String.format(" Interest Rate  : %.1f%%%n", account.interestRate()));
        sb.append(String.format(" Current Balance: ₹%d%n", account.getBalance()));
        sb.append(String.format(" Status         : %s%n", account.isActive() ? "ACTIVE" : "INACTIVE"));
        sb.append("----------------------------------------------------\n");
        sb.append(" Statement generated successfully via StringBuilder.\n");
        sb.append("====================================================\n");

        return sb.toString();
    }
}
