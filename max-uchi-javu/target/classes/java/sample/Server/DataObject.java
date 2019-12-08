package sample.Server;

import java.io.Serializable;

public class DataObject implements Serializable {
    private String command;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String location;
    private String gender;
    private int id;
    private String totalrisk;
    private String multiply;
    private String level;
    private String companyName;
    private String companyTag;
    private String availableSum;
    private String gratInvest;
    private String credit;
    private String percent;
    private String withoutPercent;
    private boolean result;
    private int counter;
    private int companyId;
    public DataObject(){

    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTotalrisk() {
        return totalrisk;
    }

    public void setTotalrisk(String totalrisk) {
        this.totalrisk = totalrisk;
    }

    public String getMultiply() {
        return multiply;
    }

    public void setMultiply(String multiply) {
        this.multiply = multiply;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyTag() {
        return companyTag;
    }

    public void setCompanyTag(String companyTag) {
        this.companyTag = companyTag;
    }

    public String getAvailableSum() {
        return availableSum;
    }

    public void setAvailableSum(String availableSum) {
        this.availableSum = availableSum;
    }

    public String getGratInvest() {
        return gratInvest;
    }

    public void setGratInvest(String gratInvest) {
        this.gratInvest = gratInvest;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getWithoutPercent() {
        return withoutPercent;
    }

    public void setWithoutPercent(String withoutPercent) {
        this.withoutPercent = withoutPercent;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
