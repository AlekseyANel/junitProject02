public class Contact {
    private String firstName;
    private String lastName;
    private String phoneNumber;
//Класс формирует экземпляр контакта пользователя: Имя, Фамилия, Телефон
    public Contact(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void validateFirstName() {
        if (this.firstName.isBlank()) {
            throw new RuntimeException(("First Name can't be null or empty"));
        }
    }
    public void validateLastName() {
        if (this.lastName.isBlank()) {
            throw new RuntimeException(("Last Name can't be null or empty"));
        }
}

    public void validatePhoneNumber() {
        if (this.phoneNumber.isBlank()) {
            throw new RuntimeException(("Last Name can't be null or empty"));
        }
        if (this.phoneNumber.length() != 10) {
            throw new RuntimeException("Phone Number should be 10 digits long!");
        }
        if (!this.phoneNumber.matches("\\d+")) {
            throw new RuntimeException("Phone Number consists only of digits!");
        }
        if (!this.phoneNumber.startsWith("0")) {
            throw new RuntimeException("Phone Number should start with '0'!");
        }
    }



}
