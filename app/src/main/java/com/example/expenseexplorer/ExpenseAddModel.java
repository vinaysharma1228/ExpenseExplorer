package com.example.expenseexplorer;

public class ExpenseAddModel {

    String ExpenseName,ExpensePlace,ExpenseAmountPayMode,ExpenseCategory,ExpenseAmount,ExpenseTime;

    public ExpenseAddModel(String expenseName, String expensePlace, String expenseAmountPayMode, String expenseCategory, String expenseAmount,String expenseTime) {
        ExpenseName = expenseName;
        ExpensePlace = expensePlace;
        ExpenseAmountPayMode = expenseAmountPayMode;
        ExpenseCategory = expenseCategory;
        ExpenseAmount = expenseAmount;
        ExpenseTime=expenseTime;

    }

    public String getExpenseTime() {
        return ExpenseTime;
    }

    public void setExpenseTime(String expenseTime) {
        ExpenseTime = expenseTime;
    }

    public ExpenseAddModel() {
    }

    public String getExpenseName() {
        return ExpenseName;
    }

    public void setExpenseName(String expenseName) {
        ExpenseName = expenseName;
    }

    public String getExpensePlace() {
        return ExpensePlace;
    }

    public void setExpensePlace(String expensePlace) {
        ExpensePlace = expensePlace;
    }

    public String getExpenseAmountPayMode() {
        return ExpenseAmountPayMode;
    }

    public void setExpenseAmountPayMode(String expenseAmountPayMode) {
        ExpenseAmountPayMode = expenseAmountPayMode;
    }

    public String getExpenseCategory() {
        return ExpenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        ExpenseCategory = expenseCategory;
    }

    public String getExpenseAmount() {
        return ExpenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        ExpenseAmount = expenseAmount;
    }
}
