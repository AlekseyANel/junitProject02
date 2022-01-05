import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//Класс управляет списком контактов contactList (пользователей)
public class ContactManager {
//Инициализируем список контактов в виде HashMap (Ключ, Контакт)
    Map<String, Contact> contactList = new HashMap<>();

    //Метод добавления нового контакта в список contactList
    public void addContact(String firstName,
                           String lastName,
                           String phoneNumber) {
        //создаем новый экземпляр contact из только что добавленных данных
        Contact contact = new Contact(firstName, lastName, phoneNumber);
        //Валидируем все введенные данные в контакт
        validateContact(contact);
        //Проверяем может уже существует такой контакт
        checkIfContactIsAlreadyExist(contact);
        //ложим в ноду contactList'a два значения ключа и нового экземпляра контакта
        contactList.put(generateKey(contact),contact);

    }

    //Метод выборки всех именно контактов (значений) из списка-мапы contactList
    public Collection<Contact> getAllContacts() {
        return contactList.values();
    }
    //Метод проверяет по ключу, может уже существует такой контакт
    private void checkIfContactIsAlreadyExist(Contact contact) {
        if (contactList.containsKey(generateKey(contact)))
            throw new RuntimeException("Contact Already Exists!");
    }
    //Метод осуществляет валидацию контакта по каждому пункту
    private void validateContact(Contact contact) {
        contact.validateFirstName();
        contact.validateLastName();
        contact.validatePhoneNumber();
    }
    //Метод генерирует ключ для contactList-HashMap в виде строки на основе имени и фамилии
    private String generateKey(Contact contact) {
        return String.format("%s-%s",
                contact.getFirstName(),
                contact.getLastName());
    }


}
